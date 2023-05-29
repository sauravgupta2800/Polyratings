package com.example.polyratings

import com.example.polyratings.data.EvaluateProfessorFormState
import com.example.polyratings.data.Professor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://api-beta.polyratings.org/"

val contentType = "application/json"

interface APIService {
    @GET("professors.all")
    suspend fun getProfessors(): Response<ApiResponse>

    @GET("professors.get")
    suspend fun getProfessorDetails(@Query("input", encoded = true) professorId: String): Response<ApiResponseDetails>

    @POST("ratings.add")
    suspend fun addRating(@Body payload: EvaluateProfessorFormState): Response<ApiResponseDetails>

    companion object {
        var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }



}

//List
data class ApiResponse(
    val result: Results
)
data class Results(
    val data: List<Professor>
)

// Details
data class ApiResponseDetails(
    val result: ResultsDetails
)
data class ResultsDetails(
    val data: Professor
)