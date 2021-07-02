package com.example.medicoDoctor.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.medicoDoctor.data.api.ServiceBuilder
import com.example.medicoDoctor.data.api.ServiceProvider
import com.example.medicoDoctor.data.model.LoginUser
import com.example.medicoDoctor.data.model.SignInBody
import com.example.medicoDoctor.ui.home.HomeActivity
import com.example.medicoDoctor.utils.sharedPrefFile
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepo {
    companion object {
        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun login(
            context: Context,
            tel: String,
            password: String
        ) {

            var signinbody = SignInBody(tel, password)
            val authPatientRequest = api.userLogin(signinbody) // consommation de l'api

            val sharedPref = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

            authPatientRequest.enqueue(object : Callback<LoginUser> {

                @SuppressLint("RestrictedApi")
                override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: LoginUser = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            LoginUser::class.java
                        )
                        Toast.makeText(context, "Erreur dans le login", Toast.LENGTH_LONG).show()

                    } else {
                        val resp = response.body()

                        if (resp != null) {
                            with(sharedPref?.edit()) {
                                this?.putString("userID", resp?.id.toString())
                                this?.apply()
                            }
                        }

                        Toast.makeText(context, "Connexion établie", Toast.LENGTH_SHORT).show()
                        val myIntent = Intent(context, HomeActivity::class.java)
                        context.startActivity(myIntent)
                    }
                }

                override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}