package com.a24i.jobinterview.rest;

import android.util.Log;

import com.a24i.jobinterview.BuildConfig;
import com.a24i.jobinterview.JobInterviewConfig;
import com.a24i.jobinterview.repository.TheMovieDbRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JIClient
{
    private static volatile Retrofit sRetrofit;


    public JIClient() {
    }


    public static <T> T createService(Class<T> service) {
        return getRetrofit().create(service);
    }


    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (TheMovieDbRepository.class) {
                if (sRetrofit == null) {
                    sRetrofit = buildRetrofit();
                }
            }
        }
        return sRetrofit;
    }


    private static Retrofit buildRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(JobInterviewConfig.REST_BASE_URL);
        builder.client(buildClient());
        builder.addConverterFactory(createConverterFactory());
        builder.addCallAdapterFactory(createCallAdapterFactory());
        return builder.build();
    }


    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(JobInterviewConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(JobInterviewConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(JobInterviewConfig.SERVER_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addNetworkInterceptor(createLoggingInterceptor());
        return builder.build();
    }

    private static Interceptor createLoggingInterceptor() {
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("intercepter", message);
            }
        };
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logger);
        interceptor.setLevel(BuildConfig.LOGS ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    /**
     * Enable Retrofit to convert data from JSON format to Java object
     *
     * @return
     */
    private static Converter.Factory createConverterFactory() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(JobInterviewConfig.BASIC_DATE_FORMAT_STRING);
        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    private static CallAdapter.Factory createCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}
