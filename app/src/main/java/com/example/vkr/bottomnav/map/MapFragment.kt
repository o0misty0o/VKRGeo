package com.example.vkr.bottomnav.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vkr.R
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vkr.AddPostActivity
import com.example.vkr.PostDetailActivity
import com.example.vkr.databinding.FragmentMapsBinding
import com.example.vkr.posts.PostItem
import com.google.firebase.database.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider

class MapFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val startLocation = Point(59.224209, 39.883676)
    private val zoomValue = 15.0f
    private lateinit var targetLocation: Point
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKeyOnce()
        MapKitFactory.initialize(requireContext())
        database = FirebaseDatabase.getInstance().reference.child("posts")

        arguments?.let {
            val latitude = it.getDouble("latitude")
            val longitude = it.getDouble("longitude")
            targetLocation = Point(latitude, longitude)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        requestLocationPermission()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
        moveToStartLocation()

        // Установка слушателя нажатий на карту
        binding.mapview.map.addInputListener(object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                onMapTap(point)
            }

            override fun onMapLongTap(map: Map, point: Point) {
                // Можно реализовать длительное нажатие, если нужно
            }
        })

        // Загрузка данных из Firebase и добавление маркеров
        loadPostsAndAddMarkers()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setApiKeyOnce() {
        if (!MapKitFactoryHelper.isApiKeySet) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY)
            MapKitFactoryHelper.isApiKeySet = true
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Разрешение получено, можете использовать функционал, зависящий от разрешения
            } else {
                // Разрешение отклонено
            }
        }
    }

    private fun moveToStartLocation() {
        binding.mapview.map.move(
            CameraPosition(startLocation, zoomValue, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f),
            null
        )
    }

    private fun onMapTap(point: Point) {
        val latitude = point.latitude
        val longitude = point.longitude
        Toast.makeText(requireContext(), "Координаты: $latitude, $longitude", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), AddPostActivity::class.java)
        intent.putExtra("latitude", latitude)
        intent.putExtra("longitude", longitude)
        intent.putExtra("window", "map")
        startActivity(intent)
    }



    private fun loadPostsAndAddMarkers() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.mapview.map.mapObjects.clear()  // Очистка всех объектов карты перед добавлением новых

                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(PostItem::class.java)

                    if (post != null) {
                        val point = Point(post.postCoord1.toDouble(),
                            post.postCoord2.toDouble())

                        val marker = binding.mapview.map.mapObjects.
                        addPlacemark(point, ImageProvider.fromResource(requireContext(),
                            R.drawable.icon_marker))

                        marker.userData = post

                        marker.addTapListener { _, _ ->
                            val intent = Intent(requireContext(),
                                PostDetailActivity::class.java)
                            intent.putExtra("post", post)
                            startActivity(intent)
                            true
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load posts", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val MAPKIT_API_KEY = "8fbca111-de1d-4895-b50a-5006afb697e3"
        private const val REQUEST_CODE_LOCATION = 1

        fun newInstance() = MapFragment()
    }
}

object MapKitFactoryHelper {
    var isApiKeySet: Boolean = false
}
