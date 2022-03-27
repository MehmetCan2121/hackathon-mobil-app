package com.mehmetcanmut.ogrencinot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mehmetcanmut.ogrencinot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()

        }

    }

    fun girisyap(view : View){
        val email = binding.emailText2.text.toString()
        val sifre = binding.sifreText3.text.toString()
        if (email.equals("")||sifre.equals("")){
            Toast.makeText(this,"Sifre ve emaili dogru giriniz",Toast.LENGTH_LONG).show()
        }
        else{
            auth.signInWithEmailAndPassword(email,sifre).addOnSuccessListener {
                val intent = Intent(applicationContext, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Giriş başarısız",Toast.LENGTH_LONG).show()
            }
        }



    }
    fun kayitol(view : View){
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)

    }
}