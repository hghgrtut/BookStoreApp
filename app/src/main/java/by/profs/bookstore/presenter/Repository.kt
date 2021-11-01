package by.profs.bookstore.presenter

import by.profs.bookstore.data.ParseObject
import by.profs.bookstore.presenter.api.ApiImplementation as Api
import kotlinx.coroutines.flow.Flow

object Repository {
    suspend fun getBookPrices(bookName: String) : Flow<List<ParseObject>> = Api.getPrices(bookName)
}