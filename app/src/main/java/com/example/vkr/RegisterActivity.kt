package com.example.vkr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vkr.databinding.ActivityLoginBinding
import com.example.vkr.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


        binding.registerBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (checkFields()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //auth.signOut()
                        val userInfo: HashMap<String, String> = HashMap()
                        userInfo.put("email", binding.emailEt.text.toString())
                        userInfo.put("login", binding.loginEt.text.toString())
                        userInfo.put("profileImage", "")

                        val currentUser = FirebaseAuth.getInstance().currentUser

                        // Получаем UID текущего пользователя
                        val uid = currentUser?.uid

                        // Сохраняем данные пользователя в Firebase Realtime Database
                        if (uid != null) {
                            FirebaseDatabase.getInstance().reference.child("Users").child(uid)
                                .setValue(userInfo)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Данные успешно сохранены
                                        Log.d("Firebase", "User information saved successfully.")
                                        startActivity(Intent(this, HomeActivity::class.java))
                                    } else {
                                        // Произошла ошибка при сохранении данных
                                        Log.e(
                                            "Firebase",
                                            "Failed to save user information.",
                                            task.exception
                                        )
                                    }
                                }
                        } else {
                            Log.d("Firebase", "uid = null")
                        }


                        Toast.makeText(this, "Account created successfuly!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }

//        if (binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString().isEmpty() || binding.loginEt.text.toString().isEmpty()){
//            Toast.makeText(applicationContext,"fields cannot be empty", Toast.LENGTH_SHORT).show()
//        }else{
//            val email = binding.emailEt.text.toString()
//            val password = binding.passwordEt.text.toString()
//            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
//                val userInfo: HashMap<String, String> = HashMap()
//                userInfo.put("email",binding.emailEt.text.toString())
//                userInfo.put("login",binding.loginEt.text.toString())
//                FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseAuth.getInstance().currentUser.uid.)
//            }
//        }

    }

    private fun checkFields(): Boolean{
        val email = binding.emailEt.text.toString()
        if(binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString().isEmpty() || binding.loginEt.text.toString().isEmpty()){
            Toast.makeText(applicationContext,"fields cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(applicationContext,"check email format", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}