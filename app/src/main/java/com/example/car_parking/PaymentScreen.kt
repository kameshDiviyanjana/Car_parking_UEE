package com.example.car_parking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentScreen : AppCompatActivity() {
    private lateinit var dbConnection: DatabaseReference
    private lateinit var edtCardName: EditText
    private lateinit var edtCVV: EditText
    private lateinit var edtCardNumber: EditText
    private lateinit var edtExpDate: EditText
    private lateinit var btnPay: Button
    private lateinit var btnCancelPay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_screen)

        // Find views by their IDs
        edtCardName = findViewById(R.id.edtcardname)
        edtCVV = findViewById(R.id.edtcvv)
        edtCardNumber = findViewById(R.id.edtcardnumber)
        edtExpDate = findViewById(R.id.edtexpdate)
        btnPay = findViewById(R.id.btnpay)
        btnCancelPay = findViewById(R.id.btncancelpay)

        btnPay.setOnClickListener {
            val cardName = edtCardName.text.toString()
            val cvv = edtCVV.text.toString()
            val cardNumber = edtCardNumber.text.toString()
            val expDate = edtExpDate.text.toString()

            if (cardName.isEmpty() || cvv.isEmpty() || cardNumber.isEmpty() || expDate.isEmpty()) {
                // Check if any input field is empty
                Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show()
            } else {
                // Handle the payment logic
                dbConnection = FirebaseDatabase.getInstance().getReference("PaymentDetails")

                // Create a PaymentDetails object
                val paymentDetails = PaymentDetails(cardName, cvv, cardNumber, expDate)

                // Store payment details in the Firebase database
                dbConnection.child(cardName).setValue(paymentDetails).addOnSuccessListener {
                    edtCardName.text.clear()
                    edtCVV.text.clear()
                    edtCardNumber.text.clear()
                    edtExpDate.text.clear()
                    Toast.makeText(this, "Payment details added successfully", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, homedashboredUser::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to add payment details to the database", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnCancelPay.setOnClickListener {
            // Handle cancel action if needed
        }
    }
}
