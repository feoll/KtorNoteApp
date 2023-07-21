package com.example.ktornoteapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.ktornoteapp.data.api.NoteApi
import com.example.ktornoteapp.data.common.BASE_URL
import com.example.ktornoteapp.data.manager.SessionManager
import com.example.ktornoteapp.data.manager.SessionManagerImpl
import com.example.ktornoteapp.data.repositories.NoteRepository
import com.example.ktornoteapp.data.repositories.NoteRepositoryImpl
import com.example.ktornoteapp.data.repositories.UserRepository
import com.example.ktornoteapp.data.repositories.UserRepositoryImpl
import com.google.gson.Gson
import com.samarth.ktornoteapp.data.local.NoteDao
import com.samarth.ktornoteapp.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideNoteApi(): NoteApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context, api: NoteApi, gson: Gson, sessionManager: SessionManager): UserRepository {
        return UserRepositoryImpl(context = context, api = api, gson = gson, sessionManager = sessionManager)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(@ApplicationContext context: Context, api: NoteApi, gson: Gson, sessionManager: SessionManager, dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(context = context, api = api, gson = gson, sessionManager = sessionManager, dao = dao)
    }


    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "notedb"
    ).build()

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.getNoteDao()
    }

}