package com.example.medicoDoctor.data.api

import com.example.medicoDoctor.data.model.*
import retrofit2.Call
import retrofit2.http.*


interface ServiceProvider {

    //authentification patient
    @POST("api/auth/medecin")
    fun userLogin(
        @Body info: SignInBody
    ): Call<LoginUser>

}
