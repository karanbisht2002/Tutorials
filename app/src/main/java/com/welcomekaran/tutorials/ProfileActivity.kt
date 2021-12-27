package com.welcomekaran.tutorials

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.welcomekaran.tutorials.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    //View Binding
    private lateinit var binding:ActivityProfileBinding

    //Progress Bar
    private lateinit var progressbar:ProgressDialog

    //Action Bar
    private lateinit var actionbar:ActionBar

    //FirebaseAuth
    private lateinit var firebaseauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        //ViewBinding
        binding= ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Progress bar
        progressbar= ProgressDialog(this)
        progressbar.setTitle("Please Wiat")
        progressbar.setMessage("Loging Out...")
        progressbar.setCanceledOnTouchOutside(false)

        //Action Bar
        actionbar=supportActionBar!!
        actionbar.setTitle("Profile")

        //Firebase Auth

        firebaseauth= FirebaseAuth.getInstance()
        checkUser()

        //handel on click on logout button
        binding.logoutBtn.setOnClickListener{
            firebaseauth.signOut()
            checkUser()

        }
    }

    private fun checkUser() {
        val firebaseuser=firebaseauth.currentUser
        if(firebaseuser!=null)
        {
            //user not nulll
                var email=firebaseuser.email
            binding.emailTv.text=email
        }
        else
        {
            //user null
           startActivity(Intent(this,LoginActivity::class.java))
            finish()

        }
    }
}