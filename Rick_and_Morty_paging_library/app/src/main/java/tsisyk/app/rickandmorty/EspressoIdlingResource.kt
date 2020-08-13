package tsisyk.app.rickandmorty

import androidx.test.espresso.idling.CountingIdlingResource


/*
* An idling resource represents an asynchronous operation whose results affect subsequent
* operations in a UI test. By registering idling resources with Espresso, you can validate
* these asynchronous operations more reliably when testing your app.
* https://developer.android.com/training/testing/espresso/idling-resource
* */
object EspressoIdlingResource {

    private const val RESOURSE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURSE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}