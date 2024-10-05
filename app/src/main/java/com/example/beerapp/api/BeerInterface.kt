package com.example.beerapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.beerapp.model.BeerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BeerInterface {
    @GET("/beers/ale")
    suspend fun getBeer(): Response<List<BeerResponse>>

    companion object {
        private var apiInterface: BeerInterface? = null
        fun getInstance(): BeerInterface {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.sampleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            apiInterface = retrofit.create(BeerInterface::class.java)
            return apiInterface!!

        }
    }
}

fun getBeerData(): MutableLiveData<List<BeerResponse>> {
    val beerInterface = BeerInterface.getInstance()
    return callApi { beerInterface.getBeer() }
}

fun <T : Any> callApi(apiRequest: suspend () -> Response<T>): MutableLiveData<T> {
    val liveData = MutableLiveData<T>()
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = withContext(Dispatchers.IO) { apiRequest.invoke() }
            liveData.postValue(response.body())
        } catch (e: Exception) {
            Log.e("Api Error", Log.getStackTraceString(e));
        }
    }
    return liveData
}