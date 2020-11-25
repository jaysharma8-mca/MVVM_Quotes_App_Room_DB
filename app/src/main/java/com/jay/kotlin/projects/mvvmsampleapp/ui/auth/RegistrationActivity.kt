package com.jay.kotlin.projects.mvvmsampleapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jay.kotlin.projects.mvvmsampleapp.R
import com.jay.kotlin.projects.mvvmsampleapp.databinding.ActivityRegistrationBinding
import com.jay.kotlin.projects.mvvmsampleapp.ui.home.HomeActivity
import com.jay.kotlin.projects.mvvmsampleapp.util.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.progress_bar
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegistrationActivity : AppCompatActivity(),/*AuthListener,*/ KodeinAware {

    //Importing DI
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_registration)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, { user->
            if(user != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignUp.setOnClickListener {
            userRegister()
        }
        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun userRegister() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()
        val name = binding.editTextName.text.toString().trim()
        val school = binding.editTextSchool.text.toString().trim()

        when {
            name.isEmpty() -> {
                edit_text_name.error = "Invalid name!!!"
                edit_text_name.requestFocus()
                return
            }
            email.isEmpty() -> {
                edit_text_email.error = "Invalid email!!!"
                edit_text_email.requestFocus()
                return
            }
            school.isEmpty() -> {
                edit_text_school.error = "Invalid school name!!!"
                edit_text_school.requestFocus()
                return
            }
            password.isEmpty() -> {
                edit_text_password.error = "Invalid password!!!"
                edit_text_password.requestFocus()
                return
            }
            password != confirmPassword -> {
                edit_text_password_confirm.error = "Password did not match!!!"
                edit_text_password_confirm.requestFocus()
                return
            }
            else -> {
                progress_bar.show()
                lifecycleScope.launch {
                    try{
                        val authResponse =  viewModel.userRegister(email, password, name, school)
                        if(authResponse.user != null){

                            viewModel.saveLoggedInUser(authResponse.user)
                            progress_bar.hide()
                        }
                        else{
                            progress_bar.hide()
                            binding.rootLayout.snackbar(authResponse.message!!)
                        }
                    }
                    catch (e: ApiException){
                        e.message?.let { binding.rootLayout.snackbar(it) }
                        progress_bar.hide()
                        e.printStackTrace()
                    }
                    catch (e: NoInternetException){
                        e.message?.let { binding.rootLayout.snackbar(it) }
                        progress_bar.hide()
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /* override fun onStarted() {
         //toast("Login Started")
         progress_bar.show()
     }

     override fun onSuccess(user: User) {
         progress_bar.hide()
         //root_layout.snackbar("${user.name} is logged in")
         //toast("${user.name} is logged in")
     }

     override fun onFailure(message: String) {
         progress_bar.hide()
         root_layout.snackbar(message)
         //toast(message)
     }*/

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}