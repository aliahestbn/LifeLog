package com.example.lifelog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lifelog.databinding.ActivityEditprofileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditprofileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var selectedImageUri: Uri? = null
    private lateinit var userDatabaseReference: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    companion object {
        var profilePictureUrl: String? = null
    }

    private fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val userUid = getCurrentUserUid()
        userDatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(userUid.toString())

        binding.imageButton17.setOnClickListener {
            chooseProfilePicture()
        }

        binding.imageButton2.setOnClickListener {
            saveProfile()
        }
        fetchAndDisplayProfilePicture()
    }


    private fun chooseProfilePicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data

                selectedImageUri?.let {
                    Glide.with(this)
                        .load(it)
                        .transform(CenterCrop(), RoundedCorners(8))
                        .into(binding.imageView11)

                    // Upload the selected image to Firebase Storage and update the profile picture URL
                    uploadImageToStorage(getCurrentUserUid())
                }
            }
        }

    private fun uploadImageToStorage(userUid: String?) {
        userUid?.let {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("profilePictures/$userUid.jpg")

            selectedImageUri?.let {
                storageReference.putFile(it)
                    .addOnSuccessListener { taskSnapshot ->
                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            saveProfilePictureUrl(uri.toString())
                            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to upload profile picture", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
    }

    private fun saveProfile() {
        val userUid = getCurrentUserUid()

        if (userUid != null) {
            val profilePictureReference =
                FirebaseDatabase.getInstance().getReference("DiaryEntries").child(userUid)
                    .child("ProfilePicture")

            if (selectedImageUri != null) {
                // Save the image URL to the "url" node under "ProfilePicture"
                profilePictureReference.child("url").setValue(userUid + ".jpg")
                    .addOnSuccessListener {
                        uploadImageToStorage(userUid)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to save profile picture URL", Toast.LENGTH_SHORT)
                            .show()
                    }
            } else {
                Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle the case where the user UID is null
            Toast.makeText(this, "User UID is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveProfilePictureUrl(profilePictureUrl: String) {
        val userUid = getCurrentUserUid()

        if (userUid != null) {
            val profilePictureReference =
                FirebaseDatabase.getInstance().getReference("Users").child(userUid)
                    .child("profilePictureUrl")

            profilePictureReference.setValue(profilePictureUrl)
                .addOnSuccessListener {
                    // Update the profile picture URL in the companion object
                    EditProfile.profilePictureUrl = profilePictureUrl

                    // Fetch and display the profile picture in the EditProfile activity
                    fetchAndDisplayProfilePicture()

                    // Pass the updated profilePictureUrl to the History activity
                    //val intent = Intent(this@EditProfile, History::class.java)
                   // intent.putExtra("profilePictureUrl", profilePictureUrl)
                   // startActivity(intent)

                    // If needed, you can do something after successfully saving the URL
                }
                .addOnFailureListener {
                    // Handle the case where saving the URL failed
                }
        }
    }


    fun fetchAndDisplayProfilePicture() {
        val userUid = getCurrentUserUid()
        val userDatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(userUid.toString())

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val profilePictureUrl =
                    dataSnapshot.child("profilePictureUrl").getValue(String::class.java)
                if (!profilePictureUrl.isNullOrEmpty()) {
                    Glide.with(this@EditProfile).load(profilePictureUrl).into(binding.imageView11)

                    // Update the profile picture URL in History activity
                    EditProfile.profilePictureUrl = profilePictureUrl
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        }

        // Add the ValueEventListener to listen for changes in the profile picture URL
        userDatabaseReference.addValueEventListener(valueEventListener)
    }


    override fun onResume() {
        super.onResume()
        // Fetch and display the profile picture when the activity is resumed
        fetchAndDisplayProfilePicture()
    }

    fun redirectToHomePage2(view: View) {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }

}
