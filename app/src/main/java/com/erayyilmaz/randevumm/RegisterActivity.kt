package com.erayyilmaz.randevumm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.erayyilmaz.randevumm.databinding.ActivityMainBinding
import com.erayyilmaz.randevumm.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        binding.showPassword.setOnCheckedChangeListener { _, isChecked ->
            showPassword(isChecked, binding.passwordRegisterText)
        }
    }
    fun showPassword(isChecked: Boolean, editText: EditText) {
        if (isChecked) {
            // Şifreyi göster
            binding.passwordRegisterText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            // Şifreyi gizle
            binding.passwordRegisterText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }
    fun register(view: View){
        val email = binding.emailRegisterText.text.toString()
        val password = binding.passwordRegisterText.text.toString()
        if(email.equals("")||password.equals("")){
            Toast.makeText(this,"Enter Email and Password", Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@RegisterActivity,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }


    }
}