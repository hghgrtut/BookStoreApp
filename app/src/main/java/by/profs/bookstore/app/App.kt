package by.profs.bookstore.app

import android.app.Application
import by.profs.bookstore.app.ServiceLocator.register

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        register(applicationContext)
    }
}