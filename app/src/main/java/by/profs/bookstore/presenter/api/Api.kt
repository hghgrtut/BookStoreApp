package by.profs.bookstore.presenter.api

import by.profs.bookstore.data.ParseObject
import kotlinx.coroutines.flow.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiImplementation {
    private const val baseUrl = "https://our-book-store.herokuapp.com/api/"
    private val service = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
        .create(ApiInterface::class.java)

    suspend fun getPrices(bookName: String): Flow<List<ParseObject>> =
        try { with(service.getPrices(bookName)) {
            if (isSuccessful) listOf(body()!!).asFlow() else emptyFlow() }
        } catch (e: Exception) {
            emptyFlow()
        }
}

private interface ApiInterface {
    @GET(ENDPOINT_BOOK)
    suspend fun getPrices(@Query(value = QUERY_BOOKNAME) bookName: String): Response<List<ParseObject>>
}
