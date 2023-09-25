package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    private lateinit var emailText : EditText
    private lateinit var pwText : EditText
    private lateinit var loginBtn : Button
    private lateinit var regBtn : Button
//    private lateinit var logoutBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailText = findViewById(R.id.email)
        pwText = findViewById(R.id.password)
        loginBtn = findViewById(R.id.signIn)
        regBtn = findViewById(R.id.register)
//        logoutBtn = findViewById(R.id.logOut)

        loginBtn.setOnClickListener { v -> loginClick(v) }
        regBtn.setOnClickListener { v -> regClick(v) }
//        logoutBtn.setOnClickListener { v -> logoutClick(v)}

    }
    override fun onStart() {
        super.onStart()
        closeKeyBoard()
        update()
    }

    override fun onStop() {
        super.onStop()
        currentUser = mAuth.currentUser
        mAuth.signOut()
    }

    private fun loginClick(view : View) {
        try {

            mAuth.signInWithEmailAndPassword(
                emailText.text.toString(),
                pwText.text.toString()).addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful){
                        displayMessage(view, getString(R.string.login_success, emailText.text))
                        closeKeyBoard()
                        update()
                        try {
                            openAccount()
                            clearInput()
                        }
                        catch(e: Exception){
                            Log.i("Activities", "Null input")
                        }
                    }
                    else{
                        closeKeyBoard()
                        displayMessage(loginBtn, getString(R.string.login_failure))
                    }
                }
        }
        catch (e: Exception) {

        }
    }
    private fun clearInput(){
        emailText.text.clear()
        pwText.text.clear()
    }
    private fun openAccount(){
        val newIntent = Intent(this, SecondActivity::class.java)
        newIntent.putExtra("user", mAuth.currentUser?.email)
        startActivity(newIntent)
    }

   /* private fun logoutClick(view : View) {
        currentUser = mAuth.currentUser
        //currentUser?.delete() // Deletes the user
        if(currentUser != null) {
            mAuth.signOut()
            closeKeyBoard()
            update()
        }else {
            displayMessage(view, getString(R.string.logout_while_logged_out))
        }
    }*/

    private fun regClick(view: View) {
        try {
            if (mAuth.currentUser != null) {
                displayMessage(view, getString(R.string.register_while_logged_in))
            }
            else{
                mAuth.createUserWithEmailAndPassword(
                    emailText.text.toString(),
                    pwText.text.toString()
                ).addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        closeKeyBoard()
                        update()
                        openAccount()
                    }
                    else{
                        closeKeyBoard()
                        displayMessage(loginBtn, getString(R.string.register_failure))
                    }
                }
            }
        }
        catch(e : Exception){

        }
    }


    private fun update(){
        currentUser = mAuth.currentUser

        val currentEmail = currentUser?.email

        val greetingSpace = findViewById<TextView>(R.id.greetView)

        if(currentEmail == null) {
            greetingSpace.text = getString(R.string.not_logged_in)
        }
        else {
            greetingSpace.text = getString(R.string.greeting_message, getString(R.string.logged_in), currentEmail)
        }
    }

    private fun displayMessage(view: View, msgTxt : String) {
        val sb = Snackbar.make(view, msgTxt, Snackbar.LENGTH_SHORT)
        sb.show()
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}