package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var myDB: DataManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var carAdapter: CarAdapter
    lateinit var carList: ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDB = DataManager(this)
        carList = ArrayList()

        storeDataInArrays()

        recyclerView = findViewById(R.id.recyclerView)
        carAdapter = CarAdapter(this, carList)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.addItemDecoration(DividerItemDecoration(this, linearLayoutManager.orientation))
        recyclerView.adapter = carAdapter

    }

    // Reading cars data from database and adding to car list
    private fun storeDataInArrays() {
        var cursor: Cursor
        cursor = myDB.readAllData()
        if (cursor.count == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                var carID = cursor.getLong(0)
                var carBrand = cursor.getString(1)
                var carModel = cursor.getString(2)
                var carYear = cursor.getInt(3)
                var carKm = cursor.getInt(4)
                var carPrice = cursor.getInt(5)
                var isSold = cursor.getString(6)
                val car = Car(carID, carBrand, carModel, carYear, carKm, carPrice)
                carList.add(car)
                Log.i(TAG, "storeDataInArrays: $isSold")
                if (isSold == "false") {
                    car.isSold = false
                } else if (isSold == "true") {
                    car.isSold = true
                }
            }
            cursor.close()
        }
    }

    fun updateTable(carObj: Car) {
        val updateDialog = EditDialogFragment(carObj)
        updateDialog.show(supportFragmentManager, null)
        Log.i(TAG, "updateTable: updateID= ${carObj.ID}.")
    }

    fun refresh(requestCode: Int) {
        if (requestCode == 1) {
            recreate()
        }
    }

    fun deleteRow(car: Car) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete Car!")
            .setMessage("Are you sure you really want to delete ${car.brand} ?")
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                myDB.deleteData(car)
                refresh(1)
            }.setNegativeButton("No", null).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val b = when (id) {
            R.id.action_add -> {
                val intentToAdd = Intent(this, AddActivity::class.java)
                startActivity(intentToAdd)

                Log.i(TAG, "onOptionsItemSelected: AddActivity has been opened.")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return b
    }
}