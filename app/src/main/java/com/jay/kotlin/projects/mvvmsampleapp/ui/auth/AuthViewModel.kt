package com.jay.kotlin.projects.mvvmsampleapp.ui.auth

import androidx.lifecycle.ViewModel
import com.jay.kotlin.projects.mvvmsampleapp.data.db.entities.User
import com.jay.kotlin.projects.mvvmsampleapp.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNUSED_PARAMETER")
class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    /*var email : String?  = null
    var password : String? = null
    var confirmPassword : String? = null
    var name : String? = null
    var school: String? = null*/

    //var authListener : AuthListener ? = null

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO){
        repository.userLogin(email, password)
    }



    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        school: String
    ) = withContext(Dispatchers.IO){
        repository.userRegister(email, password, name, school)
    }


    /*fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty()){
            authListener?.onFailure("Invalid email!!!")
            return
        }
        else if(password.isNullOrEmpty()){
            authListener?.onFailure("Invalid password!!!")
            return
        }

        Coroutines.main {
            try{
                val autResponse =  repository.userLogin(email!!, password!!)
                autResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(autResponse.message!!)
            }
            catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
            catch (e:NoInternetException){
                authListener?.onFailure(e.message!!)
            }

            *//*val response = UserRepository().userLogin(email!!, password!!)
            if(response.isSuccessful){
                authListener?.onSuccess(response.body()?.user!!)
            }
            else{
                authListener?.onFailure("Error Code: ${response.code()}")
            }*//*
        }
    }

    fun onLoginPage(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onRegisterPage(view: View){
        Intent(view.context, RegistrationActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onRegisterButtonClick(view: View){
        authListener?.onStarted()
        if(name.isNullOrEmpty()){
            authListener?.onFailure("Name is required!!!")
            return
        }

        else if(email.isNullOrEmpty()){
            authListener?.onFailure("Email is required!!!")
            return
        }

        else if(school.isNullOrEmpty()){
            authListener?.onFailure("School name is required!!!")
            return
        }

        else if(password.isNullOrEmpty()){
            authListener?.onFailure("Password cannot be empty!!!")
            return
        }

        else if(confirmPassword.isNullOrEmpty()){
            authListener?.onFailure("Confirm Password cannot be empty!!!")
            return
        }

        else if(!confirmPassword.equals(password)){
            authListener?.onFailure("Password did not match!!!")
            return
        }

        else{
            Coroutines.main {
                try{
                    val autResponse =  repository.userRegister(email!!, password!!, name!!, school!!)
                    autResponse.user?.let {
                        authListener?.onSuccess(it)
                        repository.saveUser(it)
                        return@main
                    }
                    authListener?.onFailure(autResponse.message!!)
                }
                catch (e: ApiException){
                    authListener?.onFailure(e.message!!)
                }
                catch (e:NoInternetException){
                    authListener?.onFailure(e.message!!)
                }
            }
        }
    }*/
}