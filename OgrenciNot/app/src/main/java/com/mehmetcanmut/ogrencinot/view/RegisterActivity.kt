package com.mehmetcanmut.ogrencinot.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mehmetcanmut.ogrencinot.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()



    }

    fun kayitol(view: View) {
        val email = binding.emailText.text.toString()
        val sifre = binding.sifreText.text.toString()
        val ad = binding.adText.text.toString()
        val soyad = binding.soyadText.text.toString()
        val ogrencino = binding.ogrencinoText.text.toString().toInt()
        val universite = binding.universiteText.text.toString()
        val fakulte = binding.fakulteText.text.toString()
        val bolum = binding.bolumText.text.toString()
        val sinif = binding.sinifText.text.toString()
        val sifretekrar = binding.sifreText2.text.toString()

        if (email.equals("") || sifre.equals("") ||
            ad.equals("") || soyad.equals("") ||
            ogrencino.equals("") || universite.equals("") ||
            fakulte.equals("") || bolum.equals("") ||
            sinif.equals("") || sifretekrar.equals("")
        ) {
            Toast.makeText(this@RegisterActivity, "Boş alanları doldurunuz", Toast.LENGTH_LONG).show()


        } else {
            //success
            auth.createUserWithEmailAndPassword(email, sifre).addOnSuccessListener {
                Toast.makeText(this, "Kaydınız Başarılı", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Bir hata oluştu", Toast.LENGTH_LONG).show()
            }
        }


    }
}