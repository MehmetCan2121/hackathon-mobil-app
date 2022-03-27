package com.mehmetcanmut.ogrencinot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mehmetcanmut.ogrencinot.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth


    }
    fun notlar(view : View){
        val intent = Intent(applicationContext, EklemeActivity::class.java)
        startActivity(intent)
    }
    fun notEkle(view : View){
        val intent = Intent(applicationContext, NotEkleActivity::class.java)
        startActivity(intent)
    }
    fun exit(view: View){
        auth.signOut()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()

    }


}