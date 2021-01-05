package com.jay.kotlin.projects.mvvmsampleapp.ui.auth


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jay.kotlin.projects.mvvmsampleapp.R
import com.jay.kotlin.projects.mvvmsampleapp.databinding.ActivityLoginBinding
import com.jay.kotlin.projects.mvvmsampleapp.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein //this import is important
import org.kodein.di.generic.instance


class LoginActivity() : AppCompatActivity()/*,AuthListener*/, KodeinAware {

    //Importing DI
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        /*val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api,db)
        val factory = AuthViewModelFactory(repository)*/


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }


    }

    private fun loginUser(){
        val email = binding.editTextUserName.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        when {
            email.isEmpty() -> {
                editTextUserName.error = "Invalid email!!!"
                editTextUserName.requestFocus()
                return
            }
            password.isEmpty() -> {
                editTextPassword.error = "Invalid password!!!"
                editTextPassword.requestFocus()
                return
            }
            else -> {
                progressBar.show()
                lifecycleScope.launch {
                    try{
                        val authResponse =  viewModel.userLogin(email, password)
                        if(authResponse.user != null){

                            //viewModel.saveLoggedInUser(authResponse.user)
                                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                            progressBar.hide()
                        }
                        else{
                            progressBar.hide()
                            binding.rootLayout.snackbar(authResponse.message!!)
                        }
                    }
                    catch (e: ApiException){
                        e.message?.let { binding.rootLayout.snackbar(it) }
                        progressBar.hide()
                        e.printStackTrace()
                    }
                    catch (e: NoInternetException){
                        e.message?.let { binding.rootLayout.snackbar(it) }
                        progressBar.hide()
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
}