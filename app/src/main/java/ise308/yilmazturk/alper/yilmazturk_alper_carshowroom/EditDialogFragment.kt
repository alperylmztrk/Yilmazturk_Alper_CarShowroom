package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom

import android.app.AlertDialog
import android.app.Dialog
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class EditDialogFragment(var car: Car) : DialogFragment() {

    companion object {
        const val TAG = "EditDialogFragment"
    }

    private lateinit var brandEditText: EditText
    private lateinit var modelEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var kmEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var soldCheckBox: CheckBox
    private lateinit var editButton: Button

    private lateinit var myDB: DataManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.activity!!)
        builder.setMessage("Edit Car")
        val inflater = activity!!.layoutInflater
        val editDialog = inflater.inflate(R.layout.fragment_edit, null)

        brandEditText = editDialog.findViewById(R.id.editBrandEditText)
        modelEditText = editDialog.findViewById(R.id.editModelEditText)
        yearEditText = editDialog.findViewById(R.id.editYearEditText)
        kmEditText = editDialog.findViewById(R.id.editKmEditText)
        priceEditText = editDialog.findViewById(R.id.editPriceEditText)
        soldCheckBox = editDialog.findViewById(R.id.soldCheckBox)
        editButton = editDialog.findViewById(R.id.editButton)

        setTextOfEditTexts()

        myDB = DataManager(context!!)

        editButton.setOnClickListener {

            Log.i(TAG, "onCreateDialog: Edit button clicked...")

            updateItem(car.ID)

            (activity as MainActivity).refresh(1)

            dismiss()
        }

        builder.setView(editDialog)

        return builder.create()
    }

    // Updating the data according to car ID
    private fun updateItem(carID: Long?) {

        myDB.updateData(
            carID!!, brandEditText.text.toString(), modelEditText.text.toString(),
            yearEditText.text.toString().toInt(), kmEditText.text.toString().toInt(),
            priceEditText.text.toString().toInt(), soldCheckBox.isChecked
        )
        car.isSold = soldCheckBox.isChecked
    }

    // Fill the edit texts on the edit dialog fragment
    private fun setTextOfEditTexts() {

        brandEditText.setText(car.brand)
        modelEditText.setText(car.model)
        yearEditText.setText(car.year.toString())
        kmEditText.setText(car.km.toString())
        priceEditText.setText(car.price.toString())
        soldCheckBox.isChecked = car.isSold
    }
}