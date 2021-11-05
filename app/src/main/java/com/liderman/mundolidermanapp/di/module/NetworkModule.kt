package com.liderman.mundolidermanapp.di.module

import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Suppress("PrivatePropertyName")
    private val TIMEOUT: Long = 90

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRetrofit(rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                         okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Suppress("ConstantConditionIf")
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.IS_SHOW_LOG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

       /* val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }
        val trustManager = trustManagers[0] as X509TrustManager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory*/

        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(logging)
            //sslSocketFactory(sslSocketFactory, trustManager)
        }.build()
    }


    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

}