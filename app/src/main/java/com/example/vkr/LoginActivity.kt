package com.example.vkr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vkr.databinding.ActivityLoginBinding
import com.example.vkr.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        binding.goRegisterTv.setOnClickListener({
            startActivity(Intent(this,RegisterActivity::class.java))
        })



        auth = Firebase.auth

        binding.loginBtn.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (checkFields()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this,"Sign in is successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }else{
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }

//        binding.loginBtn.setOnClickListener(View.OnClickListener {
//            if (binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString().isEmpty()){
//                Toast.makeText(applicationContext,"fields cannot be empty", Toast.LENGTH_SHORT).show()
//            }else{
////                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailEt.text.toString(),
////                    binding.passwordEt.text.toString()
//
//                val email = binding.emailEt.text.toString()
//                val password = binding.passwordEt.text.toString()
//                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
//                    if (it.isSuccessful){
//                        startActivity(Intent(this,MainActivity::class.java))
//                    }
//                }
//
//
//
//
//            }
//        })
    }

    private fun checkFields(): Boolean {
        val email = binding.emailEt.text.toString()
        if (binding.emailEt.text.toString().isEmpty() || binding.passwordEt.text.toString()
                .isEmpty()
        ) {
            Toast.makeText(applicationContext, "fields cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}