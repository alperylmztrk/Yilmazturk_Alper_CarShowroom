package ise308.yilmazturk.alper.yilmazturk_alper_carshowroom

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Inheriting from the RecyclerView.Adapter class
class CarAdapter(var activity: Activity, var carList: ArrayList<Car>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    companion object {
        const val TAG = "CarAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.card_view_car, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(this.carList[position])
    }

    // Supplying the current number of items in list.
    override fun getItemCount(): Int {
        return this.carList.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var car: Car
        var brandTextView: TextView
        var modelTextView: TextView
        var yearTextView: TextView
        var kmTextView: TextView
        var priceTextView: TextView
        var editImageButton: ImageButton
        var deleteImageButton: ImageButton

        init {
            brandTextView = view.findViewById(R.id.cardCarBrand)
            modelTextView = view.findViewById(R.id.cardCarModel)
            yearTextView = view.findViewById(R.id.cardCarYear)
            kmTextView = view.findViewById(R.id.cardCarKm)
            priceTextView = view.findViewById(R.id.cardCarPrice)
            editImageButton = view.findViewById(R.id.cardEditImage)
            deleteImageButton = view.findViewById(R.id.cardDeleteImage)

            editImageButton.setOnClickListener {
                (activity as MainActivity).updateTable(car)
            }
            deleteImageButton.setOnClickListener {
                (activity as MainActivity).deleteRow(car)
            }
        }

        @SuppressLint("SetTextI18n")
        fun setData(car: Car) {
            this.car = car
            brandTextView.text = car.brand
            modelTextView.text = car.model
            yearTextView.text = car.year.toString()
            kmTextView.text = car.km.toString() + " Km"
            priceTextView.text = car.price.toString() + " ₺"

            if (car.isSold) {
                view.setBackgroundColor(Color.parseColor("#FF5252"))
            } else {
                view.setBackgroundColor(Color.parseColor("#76FF03"))
            }

            Log.i(TAG, "setData: Data has been set.")

        }
    }
}