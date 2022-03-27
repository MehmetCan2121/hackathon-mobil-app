package com.mehmetcanmut.ogrencinot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mehmetcanmut.ogrencinot.adapter.RecyclerAdapter
import com.mehmetcanmut.ogrencinot.databinding.ActivityFiltrelemeBinding
import com.mehmetcanmut.ogrencinot.model.Notlar

class FiltrelemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiltrelemeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var notlarArrayList : ArrayList<Notlar>
    private lateinit var feedAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltrelemeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        notlarArrayList = ArrayList<Notlar>()

        getData()

        binding.recylerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = RecyclerAdapter(notlarArrayList)
        binding.recylerView.adapter = feedAdapter


    }

    private fun getData() {
        db.collection("Notlar").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        //notlarArrayList.clear()
                        for (document in documents) {
                            //casting
                            val bolum = document.get("bolum") as String
                            val ders = document.get("ders") as String
                            val sinif = document.get("sinif") as String
                            val universite = document.get("universite") as String
                            val dowloadUrl = document.get("dowloadUrl") as String

                            val notlar = Notlar(bolum, ders, sinif, universite, dowloadUrl)

                            notlarArrayList.add(notlar)

                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }

            }

        }
    }

}