package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom

import android.util.Log
import java.sql.Timestamp

class Car {

    companion object {
        const val TAG = "Car"
    }

    var ID: Long? = null
    var brand: String? = null
    var model: String? = null
    var year: Int = 0
    var km: Int = 0
    var price: Int = 0
    var isSold: Boolean = false

    // This constructor for read data from database
    constructor(ID: Long?, brand: String?, model: String?, year: Int, km: Int, price: Int) {
        this.ID = ID
        this.brand = brand
        this.model = model
        this.year = year
        this.km = km
        this.price = price
    }

    // This constructor for insert new cars
    constructor(brand: String?, model: String?, year: Int, km: Int, price: Int) {

        this.brand = brand
        this.model = model
        this.year = year
        this.km = km
        this.price = price
    }


}