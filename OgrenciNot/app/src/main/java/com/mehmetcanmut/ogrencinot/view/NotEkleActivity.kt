package com.mehmetcanmut.ogrencinot.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mehmetcanmut.ogrencinot.R
import com.mehmetcanmut.ogrencinot.databinding.ActivityNotEkleBinding
import java.util.*

class NotEkleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotEkleBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var fireStore : FirebaseFirestore
    private lateinit var storage : FirebaseStorage
    var selectedPicture: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_ekle)
        binding = ActivityNotEkleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        registerLauncher()

        auth = Firebase.auth
        fireStore = Firebase.firestore
        storage = Firebase.storage
    }

    fun notEkle(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(view, "İzin gerekli", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzin Ver") {
                        //request permission
                        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                //request permission
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {

            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data
                        selectedPicture?.let {
                            binding.imageView3.setImageURI(it)
                        }
                    }
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                } else {
                    Toast.makeText(this, "İzin gerekli", Toast.LENGTH_SHORT).show()
                }

            }

    }

    fun kaydet(view: View) {

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)
        if (selectedPicture != null){
            imageReference.putFile(selectedPicture!!).addOnSuccessListener {
                //dowload-url -> firestore
                val uploadPictureReference = storage.reference.child("images").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val dowloadUrl = it.toString()

                    if (auth.currentUser != null){
                        val postMap = hashMapOf<String , Any>()
                        postMap.put("dowloadUrl",dowloadUrl)
                        postMap.put("universite",binding.universitetext.text.toString())
                        postMap.put("ders",binding.dersText.text.toString())
                        postMap.put("bolum",binding.bolumtext.text.toString())
                        postMap.put("sinif",binding.siniftext.text.toString())
                        postMap.put("date",Timestamp.now())

                        fireStore.collection("Notlar").add(postMap).addOnSuccessListener {
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this,"Hata oluştu",Toast.LENGTH_LONG).show()
                        }

                    }

                }

            }.addOnFailureListener {
                Toast.makeText(this,"Hata oluştu",Toast.LENGTH_LONG).show()
            }
        }

    }
}