package by.profs.bookstore.app

import kotlin.reflect.KClass

object ServiceLocator {
    private val instances = mutableMapOf<KClass<*>, Any>()

    fun <T : Any> register(kClass: KClass<T>, instance: T) = instances.set(kClass, instance)

    inline fun <reified T : Any> register(instance: T) = register(T::class, instance)

    fun <T : Any> get(kClass: KClass<T>) = instances[kClass] as T

    inline fun <reified T : Any> locate() = get(T::class)

    inline fun <reified T : Any> locateLazy(): Lazy<T> = lazy { get(T::class) }

    fun <T : Any> checkExistence(kClass: KClass<T>) = instances[kClass] != null
}