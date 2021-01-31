package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.RowId


class DataManager(context: Context) {
    private val db: SQLiteDatabase

    init {
        db = CustomSQLiteOpenHelper(context).writableDatabase
    }

    companion object {
        private const val TAG = "DataManager"

        private const val DB_NAME = "car_database"
        private const val DB_VERSION = 1
        private const val TABLE_CAR = "car_table"
        private const val ID = "car_id"
        private const val BRAND = "car_brand"
        private const val MODEL = "car_model"
        private const val YEAR = "car_year"
        private const val KM = "car_km"
        private const val PRICE = "car_price"
        private const val IS_SOLD = "car_is_sold"
    }

    fun insert(brand: String, model: String, year: Int, km: Int, price: Int, isSold: Boolean) {
        val insertQuery = "INSERT INTO " + TABLE_CAR +
                " ( " + BRAND + ", " + MODEL + ", " + YEAR + ", " + KM + ", " + PRICE + ", " + IS_SOLD + " ) VALUES ( '" +
                brand + "', '" + model + "', '" + year + "', '" + km + "', '" + price + "', '" + isSold + "'); "
        Log.i(TAG, "----> insert: $insertQuery ")
        db.execSQL(insertQuery)
    }

    private inner class CustomSQLiteOpenHelper(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            val newTableQuery = "CREATE TABLE " + TABLE_CAR + " ( " +
                    ID + " integer primary key autoincrement not null, " +
                    BRAND + " text not null, " +
                    MODEL + " text not null, " +
                    YEAR + " integer not null, " +
                    KM + " integer not null, " +
                    PRICE + " integer not null, " +
                    IS_SOLD + " integer not null); "
            Log.i(TAG, "----> onCreate: $newTableQuery ")

            db?.execSQL(newTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }
    }

    fun readAllData(): Cursor {
        val readQuery = "SELECT * FROM " + TABLE_CAR + ";"
        lateinit var cursor: Cursor
        cursor = db.rawQuery(readQuery, null)
        Log.i(TAG, "----> readAllData: $readQuery")
        return cursor
    }

    fun updateData(
        carID: Long,
        brand: String,
        model: String,
        year: Int,
        km: Int,
        price: Int,
        isSold: Boolean
    ) {
        val updateQuery = "UPDATE " + TABLE_CAR + " SET " + BRAND + " = '" + brand + "', " +
                MODEL + " = '" + model + "', " + YEAR + " = '" + year + "', " + KM + " = '" + km + "', " +
                PRICE + " = '" + price + "', " + IS_SOLD + " = '" + isSold + "' WHERE " + ID + " = " + carID + ";"
        Log.i(TAG, "----> updateData: $updateQuery")
        db.execSQL(updateQuery)
        Log.i(TAG, "updateData: Updated car id $carID")
    }

    fun deleteData(car: Car) {
        val deleteQuery = "DELETE FROM " + TABLE_CAR +
                " WHERE " + ID + " = " + car.ID + ";"
        Log.i(TAG, "----> deleteData: $deleteQuery")
        db.execSQL(deleteQuery)
        Log.i(TAG, "deleteData: Deleted car id: ${car.ID}")
    }
}