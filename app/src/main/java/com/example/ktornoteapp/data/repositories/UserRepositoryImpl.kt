package com.example.ktornoteapp.data.repositories

import android.content.Context
import com.example.ktornoteapp.data.api.NoteApi
import com.example.ktornoteapp.data.manager.SessionManager
import com.example.ktornoteapp.data.models.Message
import com.example.ktornoteapp.data.models.Token
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.params.RegistrationParams
import com.example.ktornoteapp.utils.Resource
import com.example.ktornoteapp.utils.isNetworkConnected
import com.google.gson.Gson
import java.lang.Exception

class UserRepositoryImpl(
    private val context: Context,
    private val api: NoteApi,
    private val gson: Gson,
    private val sessionManager: SessionManager
) : UserRepository {
    override suspend fun login(param: LoginParams): Resource<Token> {
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val loginResponse = api.login(loginParams = param)
            if (loginResponse.isSuccessful) {
                val body = loginResponse.body()
                body?.let { token ->
                    sessionManager.updateSession(token.token)
                    return Resource.Success(token)
                }
            }

            val errorBody = loginResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }

    override suspend fun registration(param: RegistrationParams): Resource<Token> {
        try {
            if (!isNetworkConnected(context)) {
                return Resource.Error("No Internet connection")
            }

            val registrationResponse = api.registration(registrationParams = param)
            if (registrationResponse.isSuccessful) {
                val body = registrationResponse.body()
                body?.let { token ->
                    sessionManager.updateSession(token.token)
                    return Resource.Success(token)
                }
            }

            val errorBody = registrationResponse.errorBody()
            if (errorBody != null) {
                val message = gson.fromJson(errorBody.string(), Message::class.java)
                if (message != null) return Resource.Error(message.message)
            }

            return Resource.Error("Something went wrong")
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error("Some Problem")
        }
    }

    override suspend fun getToken(): Resource<Token> {
        val token = sessionManager.getToken()
        token?.let {
            return Resource.Success(Token(it))
        }
        return Resource.Error("User isn't authorized")
    }

    override suspend fun logout() {
        sessionManager.logout()
    }
}