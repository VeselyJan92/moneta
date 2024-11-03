package cz.janvesely.nba.di

import cz.janvesely.nba.BuildConfig
import cz.janvesely.nba.network.api.NbaAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addNetworkInterceptor(AuthInterceptor(BuildConfig.GATEWAY_TOKEN))
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, networkJson: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GATEWAY_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun provideNbaAPI(retrofit: Retrofit): NbaAPI {
        return retrofit.create(NbaAPI::class.java)
    }


    class AuthInterceptor(val token: String) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val authenticatedRequest = chain
                .request()
                .newBuilder()
                .header("Authorization", token)
                .build()

            return chain.proceed(authenticatedRequest)
        }
    }
}