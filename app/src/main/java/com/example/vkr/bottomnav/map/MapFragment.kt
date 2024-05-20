package com.example.vkr.bottomnav.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vkr.databinding.FragmentMapsBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.geometry.Point


class MapFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val startLocation = Point(59.224209, 39.883676) //координаты стартовой точки
    private val zoomValue = 15.0f // Примерное значение зума

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKeyOnce()
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        // Запрос разрешения
        requestLocationPermission()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
        moveToStartLocation()
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
            CameraPosition(startLocation, zoomValue, 0.0f, 0.0f), // Позиция камеры
            Animation(Animation.Type.SMOOTH, 2f), // Красивая анимация при переходе на стартовую точку
            null
        )
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
