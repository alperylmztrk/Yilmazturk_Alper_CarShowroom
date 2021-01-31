package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AddActivity"
    }

    private lateinit var addBrandEditText: EditText
    private lateinit var addModelEditText: EditText
    private lateinit var addYearEditText: EditText
    private lateinit var addKmEditText: EditText
    private lateinit var addPriceEditText: EditText
    private lateinit var addButton: Button
    private lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        addBrandEditText = findViewById(R.id.addBrandEditText)
        addModelEditText = findViewById(R.id.addModelEditText)
        addYearEditText = findViewById(R.id.addYearEditText)
        addKmEditText = findViewById(R.id.addKmEditText)
        addPriceEditText = findViewById(R.id.addPriceEditText)
        addButton = findViewById(R.id.addButton)

        // New car is added when add button is clicked
        addButton.setOnClickListener {

            if (checkIsEmpty()) {
                Toast.makeText(this, "Please enter the all informations.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                car = Car(
                    addBrandEditText.text.toString(), addModelEditText.text.toString(),
                    addYearEditText.text.toString().toInt(), addKmEditText.text.toString().toInt(),
                    addPriceEditText.text.toString().toInt()
                )
                val myDB = DataManager(this)
                myDB.insert(car.brand!!, car.model!!, car.year, car.km, car.price, car.isSold)

                Log.i(TAG, "onCreate: Add button clicked...")

                val intentToMain = Intent(this, MainActivity::class.java)
                startActivity(intentToMain)
            }
        }
    }

    // Checks the edit texts if empty
    private fun checkIsEmpty(): Boolean {
        return addBrandEditText.text.isEmpty() || addModelEditText.text.isEmpty() || addYearEditText.text.isEmpty() || addKmEditText.text.isEmpty() || addPriceEditText.text.isEmpty()
    }
}
