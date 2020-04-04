package lab.march.diary.data.apis

import lab.march.diary.data.database.Post
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface DiaryService {

    suspend fun loadPosts(): List<Post>

    suspend fun savePost()

    companion object {

        operator fun invoke(): DiaryService {
            val loggingInterceptor = HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://marchlab.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DiaryService::class.java)
        }
    }
}