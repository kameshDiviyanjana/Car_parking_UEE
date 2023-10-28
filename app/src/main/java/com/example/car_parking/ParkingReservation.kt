package com.example.car_parking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.database.*

class VehicleBooking : AppCompatActivity() {
    private lateinit var dbconection: DatabaseReference
   private lateinit var slotbook  :String
    @SuppressLint("WrongViewCast", "MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_reservation)


        val book = findViewById<Button>(R.id.BookingBtn)
        val cancel = findViewById<Button>(R.id.CancelBtn)
        val name = findViewById<EditText>(R.id.nma)
        val vehiclenum = findViewById<EditText>(R.id.vehicnum)
        val phonenum = findViewById<EditText>(R.id.phonenum)
        val date = findViewById<EditText>(R.id.date)
        val starttime = findViewById<EditText>(R.id.start_time)
        val endtime = findViewById<EditText>(R.id.end_time)
        //val slot = findViewById<EditText>(R.id.slot)
        val spinner: Spinner = findViewById(R.id.myspinner)
        val fruits = arrayOf("Slot 1 ", "Slot 2", "Slot 3 ", "Slot 4", "Solt 5")


        val adapterss = ArrayAdapter(this, android.R.layout.simple_spinner_item, fruits)
        adapterss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterss

         spinner.onItemSelectedListener
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                slotbook = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        val databaseReference = FirebaseDatabase.getInstance().getReference("Customers")

        val dataList = mutableListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val parkingSpinner = findViewById<Spinner>(R.id.parkingSpinner)
        parkingSpinner.adapter = adapter



        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear() // Clear the existing data
                for (customerSnapshot in dataSnapshot.children) {
                    // Assuming each customer has a "CustomerMessage" field
                    val customerMessage = customerSnapshot.child("customerMessage").getValue(String::class.java)
                    if (customerMessage != null) {
                        dataList.add(customerMessage)
                    }
                }
                adapter.notifyDataSetChanged() // Notify the adapter that data has changed
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (databaseError != null) {
                    Log.e("Firebase Error", "Error: ${databaseError.message}")
                }
            }
        })



        val dateEditText = findViewById<AppCompatEditText>(R.id.date)
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        val calendar = Calendar.getInstance()
        val currentDate = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()

                selectedCalendar.set(year, month, dayOfMonth)

                if (selectedCalendar >= currentDate) {
                    val selectedDate = "${year}-${month + 1}-${dayOfMonth}"
                    dateEditText.setText(selectedDate)
                } else {
                    Toast.makeText(this, "Selected date cannot be in the past", Toast.LENGTH_SHORT).show()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        selectDateButton.setOnClickListener {
            datePickerDialog.show()
        }

        dateEditText.setOnClickListener {
            datePickerDialog.show()
        }

        val startTimeEditText = findViewById<EditText>(R.id.start_time)
        val endTimeEditText = findViewById<EditText>(R.id.end_time)
        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMinute = cal.get(Calendar.MINUTE)

        val startTimePicker = TimePickerDialog(
            this,
            { view: TimePicker, hourOfDay: Int, minute: Int ->
                if (hourOfDay < currentHour || (hourOfDay == currentHour && minute < currentMinute)) {
                    Toast.makeText(this, "Start time cannot be before the current time", Toast.LENGTH_SHORT).show()
                }else if (hourOfDay > 20 || (hourOfDay == 20 && minute > 0)) {
                    Toast.makeText(this, "Online reservations can not be made after 8.00 pm", Toast.LENGTH_SHORT).show()
                } else {
                    startTimeEditText.setText("$hourOfDay:$minute")
                }
            },
            currentHour, currentMinute, true
        )

        val endTimePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->

                val startTimeText = startTimeEditText.text.toString()
                val startTime = if (startTimeText.isNotBlank()) {
                    val parts = startTimeText.split(":")
                    if (parts.size == 2) {
                        val startHour = parts[0].toInt()
                        val startMinute = parts[1].toInt()
                        Pair(startHour, startMinute)
                    } else {
                        null
                    }
                } else {
                    null
                }

                if (startTime == null || (hourOfDay < startTime.first) || (hourOfDay == startTime.first && minute < startTime.second)) {
                    Toast.makeText(this, "End time cannot be before the start time", Toast.LENGTH_SHORT).show()
                } else if ((hourOfDay == startTime.first && minute - startTime.second < 60) || (hourOfDay < startTime.first)) {

                    Toast.makeText(this, "End time must be at least one hour longer than the start time", Toast.LENGTH_SHORT).show()
                } else {
                    endTimeEditText.setText("$hourOfDay:$minute")
                }
            },
            currentHour, currentMinute, true
        )

        startTimeEditText.setOnClickListener {
            startTimePicker.show()
        }

        endTimeEditText.setOnClickListener {
            endTimePicker.show()
        }


        book.setOnClickListener {
            val Name = name.text.toString()
            val vehicnumer = vehiclenum.text.toString()
            val phonenumber = phonenum.text.toString()
            val Date = date.text.toString()
            val Starttime = starttime.text.toString()
            val Endtime = endtime.text.toString()
            val parkings = parkingSpinner.selectedItem.toString()
           //  slotbook = slot.text.toString()

            if (Name.isEmpty() || vehicnumer.isEmpty() || phonenumber.isEmpty() || Date.isEmpty() ||
                Starttime.isEmpty() || Endtime.isEmpty() || parkings.isEmpty() || slotbook.isEmpty()) {

                Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
            } else {

                dbconection = FirebaseDatabase.getInstance().getReference("Reservations")
                val addtime = Bookpark(Name, phonenumber, vehicnumer, Date, Starttime, Endtime, parkings, slotbook)

                dbconection.child(Name).setValue(addtime).addOnSuccessListener {
                    name.text.clear()
                    phonenum.text.clear()
                    vehiclenum.text.clear()
                    date.text.clear()
                    starttime.text.clear()
                    endtime.text.clear()
                    parkingSpinner.setSelection(0)


                    Toast.makeText(this, "Slot booked successfully", Toast.LENGTH_LONG).show()

                    val ye = Intent(this, displayBooking::class.java)
                    startActivity(ye)
                }.addOnFailureListener {
                    Toast.makeText(this, "DB connection fails", Toast.LENGTH_LONG).show()
                }
            }
        }

        cancel.setOnClickListener {

            name.text.clear()
            vehiclenum.text.clear()
            phonenum.text.clear()
            date.text.clear()
            starttime.text.clear()
            endtime.text.clear()
            parkingSpinner.setSelection(0)
            //slot.text.clear()

            val intent = Intent(this, homedashboredUser::class.java)
            startActivity(intent)


        }

    }
}