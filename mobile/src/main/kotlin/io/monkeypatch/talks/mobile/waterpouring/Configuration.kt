package io.monkeypatch.talks.mobile.waterpouring

/**
 * The application Configuration
 */

object Configuration {

    /** The backend URL (default address with emulator) */
    var url: String = "http://10.0.2.2:8080"

    /** Glass min capacity */
    val minCapacity = 2

    /** Glass max capacity */
    val maxCapacity = 9

    init {
        require(minCapacity < maxCapacity)
    }

}