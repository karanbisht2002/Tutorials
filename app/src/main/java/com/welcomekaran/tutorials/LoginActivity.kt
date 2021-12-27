package com.welcomekaran.tutorials

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.welcomekaran.tutorials.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding
    //ActionBar
    private lateinit var actionbar:ActionBar
    //progresDialog
    private lateinit var progresbar:ProgressDialog

    //firebaseAuth
    private lateinit var firebaseauth:FirebaseAuth

    private var email=""
    private var pasword=""

    override fun onCreate(savedInstanceState: Bundle?) {
        //ViewBinding
        binding= ActivityLoginBinding.inflate(layoutInflater);
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //ActionBar
        actionbar=supportActionBar!!
        actionbar.setTitle("Login")

        //Progress Bar
        progresbar= ProgressDialog(this)
        progresbar.setTitle("Please Wait")
        progresbar.setMessage("Logging in...")
        progresbar.setCanceledOnTouchOutside(false)

        //init FirebaseAuth
        firebaseauth=FirebaseAuth.getInstance()
        checkUser()


        binding.singupTv.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
        }

        binding.loginBtn.setOnClickListener{
            validData()
        }
    }

    private fun validData() {
        //get Data
        email=binding.emailEt.text.toString().trim()
        pasword=binding.passwordEt.text.toString().trim()

        //valid Data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            //invalid Email Formet
            binding.emailEt.error = "InValid Email Formet"
        }
        else if(TextUtils.isEmpty(pasword))
        {
            //empty password
            binding.passwordEt.error="Empty Password"
        }
        else
        {
            fireBaseLogin()
        }
    }

    private fun fireBaseLogin() {
        //showing Progess Bar
        progresbar.show()
        firebaseauth.signInWithEmailAndPassword(email,pasword)
            .addOnSuccessListener {

                progresbar.dismiss()
                val firebaseuser=firebaseauth.currentUser
                val email=firebaseuser!!.email
                Toast.makeText(this,"LoggedIn as $email",Toast.LENGTH_LONG).show()

                //open Profile Page
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                e->
                progresbar.dismiss()
                Toast.makeText(this,"Login Failed due to${e.message}",Toast.LENGTH_LONG).show()

            }
    }


    private fun checkUser() {
        //if user is already loggedin go to profile
        //getcurrent user
        val firebaseuser=firebaseauth.currentUser
        if(firebaseuser!=null)
        {
            //user is already loggedin
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
    }
}