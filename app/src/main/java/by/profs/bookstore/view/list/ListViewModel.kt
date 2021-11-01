package by.profs.bookstore.view.list

import android.util.Log
import androidx.lifecycle.ViewModel
import by.profs.bookstore.data.ParseObject
import by.profs.bookstore.presenter.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val _data = MutableStateFlow(emptyList<ParseObject>())

    val data = _data.asStateFlow()

    fun loadData(bookName: String) = CoroutineScope(Dispatchers.IO).launch {
        Repository.getBookPrices(bookName).collectLatest { _data.value = it }
    }
}