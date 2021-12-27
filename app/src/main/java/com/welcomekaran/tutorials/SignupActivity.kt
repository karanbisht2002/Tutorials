package com.welcomekaran.tutorials

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.welcomekaran.tutorials.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    //View Binding
    private lateinit var binding:ActivitySignupBinding
    //ActionBar
    private lateinit var actionbar:ActionBar

    //Progress Bar
    private lateinit var progrssbar:ProgressDialog

    //firebaseauth
    private lateinit var firebaseauth:FirebaseAuth

    //email,password
    private var email=""
    private var pasword=""
    override fun onCreate(savedInstanceState: Bundle?) {
        //View Binding
        binding= ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Action Bar
        actionbar= supportActionBar!!
        actionbar.setTitle("Sign Up")
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)


        //Progress Bar
        progrssbar= ProgressDialog(this)
        progrssbar.setTitle("Please Wait")
        progrssbar.setMessage("Creating Account....")
        progrssbar.setCanceledOnTouchOutside(false)

        //Firebase Auth
        firebaseauth= FirebaseAuth.getInstance()

        //on click of Signup
        binding.signupBtn.setOnClickListener{
            email=binding.emailEt.text.toString().trim()
            pasword=binding.passwordEt.text.toString().trim()

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                //invalid Format
                binding.emailEt.error = "Invalid Email Formet"
            }
            else if(TextUtils.isEmpty(pasword))
            {
                //Empty Password
                binding.passwordEt.error="Empty Password"
            }
            else if(pasword.length <6)
            {
                     binding.passwordEt.error="Character is Less Than 6"
            }
            else
            {
                firebaseSignup()
            }
        }

    }

    private fun firebaseSignup() {
        //showing progress bar
        progrssbar.show()

        //Create Account
        firebaseauth.createUserWithEmailAndPassword(email,pasword)
            .addOnSuccessListener {
           val firebaseUser=firebaseauth.currentUser
                val email=firebaseUser!!.email
                Toast.makeText(this,"Account Created with $email",Toast.LENGTH_LONG).show()

                //open Profile
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{
                e->
                  progrssbar.dismiss()
                Toast.makeText(this,"Signup Failed due to ${e.message}",Toast.LENGTH_LONG).show()
            }


    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()  //when we press back button it moves to back
        return super.onNavigateUp()
    }
}