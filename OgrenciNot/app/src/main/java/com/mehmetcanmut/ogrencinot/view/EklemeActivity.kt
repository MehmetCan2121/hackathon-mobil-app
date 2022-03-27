package com.mehmetcanmut.ogrencinot.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.mehmetcanmut.ogrencinot.R
import com.mehmetcanmut.ogrencinot.adapter.RecyclerAdapter
import com.mehmetcanmut.ogrencinot.databinding.ActivityEklemeBinding

import com.mehmetcanmut.ogrencinot.model.Notlar

class EklemeActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityEklemeBinding
    private lateinit var notlarArrayList : ArrayList<Notlar>
    private lateinit var feedAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekleme)
        databaseReference = FirebaseDatabase.getInstance().getReference("Notlar")
        notlarArrayList = ArrayList<Notlar>()
    }


    /*private  fun filter(e: String) {

        val filteredItem = ArrayList<Notlar>()

        for (item in notlarArrayList) {
            if (item.universite!!.toLowerCase().contains(e.toLowerCase())
                && item.bolum!!.toLowerCase().contains(e.toLowerCase())

            ) {
                filteredItem.add(item)
            }
        }
        // add the filtered value to adapter
        feedAdapter.notlarList
        (filteredItem)

        binding.universitetext2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                filter(editable.toString())
            }
        })


    }*/




    fun ara(view : View){
        val intent = Intent(applicationContext, FiltrelemeActivity::class.java)
        startActivity(intent)
        db.collection("Notlar")
            .whereEqualTo("universite", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "Başarılı")
                }


            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }



    }
}