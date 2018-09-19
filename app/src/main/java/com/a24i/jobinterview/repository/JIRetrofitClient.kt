package com.a24i.jobinterview.repository

import android.util.Log

import com.a24i.jobinterview.BuildConfig
import com.a24i.jobinterview.JobInterviewConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory

import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class JIRetrofitClient {
    companion object {
        @Volatile
        private var sRetrofit: Retrofit? = null


        fun <T> createService(service: Class<T>): T {
            return retrofit!!.create(service)
        }


        val retrofit: Retrofit?
            get() {
                if (sRetrofit == null) {
                    synchronized(TheMovieDbRepository::class.java) {
                        if (sRetrofit == null) {
                            sRetrofit = buildRetrofit()
                        }
                    }
                }
                return sRetrofit
            }


        private fun buildRetrofit(): Retrofit {
            val builder = Retrofit.Builder()
            builder.baseUrl(JobInterviewConfig.REST_BASE_URL)
            builder.client(buildClient())
            builder.addConverterFactory(createConverterFactory())
            builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
            return builder.build()
        }


        private fun buildClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(JobInterviewConfig.SERVER_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            builder.readTimeout(JobInterviewConfig.SERVER_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            builder.writeTimeout(JobInterviewConfig.SERVER_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            builder.addNetworkInterceptor(createLoggingInterceptor())
            return builder.build()
        }


        private fun createLoggingInterceptor(): Interceptor {
            val logger = HttpLoggingInterceptor.Logger { message -> Log.d("intercepter", message) }
            val interceptor = HttpLoggingInterceptor(logger)
            interceptor.level = if (BuildConfig.LOGS) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            return interceptor
        }


        /**
         * Enable Retrofit to convert data from JSON format to Java object
         *
         * @return
         */
        private fun createConverterFactory(): Converter.Factory {
            val builder = GsonBuilder()
            builder.setDateFormat(JobInterviewConfig.BASIC_DATE_FORMAT_STRING)
            val gson = builder.create()
            return GsonConverterFactory.create(gson)
        }
    }
}