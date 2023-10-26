package com.example.car_parking

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class mainActivity : AppCompatActivity() {


    private lateinit var namecus:EditText
    private lateinit var emailcus:EditText
    private lateinit var phonecus:EditText
    private lateinit var subjecus:EditText
    private lateinit var Messagecus:EditText
    private lateinit var subbmitbtn:Button

    private lateinit var dbRef:DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recipientEditText = findViewById<EditText>(R.id.emailcus)
        val subjectEditText = findViewById<EditText>(R.id.subjecus)
        val messageEditText = findViewById<EditText>(R.id.Messagecus)
        val sendEmailButton = findViewById<Button>(R.id.mailbtn)

        sendEmailButton.setOnClickListener {
            val recipient = recipientEditText.text.toString().trim()
            val subject = subjectEditText.text.toString().trim()
            val message = messageEditText.text.toString().trim()

            val mIntent = Intent(Intent.ACTION_SEND)

            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            mIntent.putExtra(Intent.EXTRA_TEXT, message)
            try {
                startActivity(Intent.createChooser(mIntent, "Send Email"))
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
        namecus = findViewById(R.id.namecus)
        emailcus = findViewById(R.id.emailcus)
        phonecus = findViewById(R.id.phonecus)
        subjecus = findViewById(R.id.subjecus)
        Messagecus = findViewById(R.id.Messagecus)
        subbmitbtn = findViewById(R.id.subbmitbtn)


        dbRef= FirebaseDatabase.getInstance().getReference("Customers")

        subbmitbtn.setOnClickListener{
            saveCustomerData()
        }


    }
    private fun saveCustomerData(){
        //getting values
        val customerName = namecus.text.toString()
        val customerEmail= emailcus.text.toString()
        val customerPhone= phonecus.text.toString()
        val customerSubject= subjecus.text.toString()
        val customerMessage= Messagecus.text.toString()

        if(customerName.isEmpty()){
            namecus.error = "Please enter name"
        }
        if(customerEmail.isEmpty()){
            emailcus.error = "Please enter email"
        }
        if(customerPhone.isEmpty()){
            phonecus.error = "Please enter phone"
        }
        if(customerSubject.isEmpty()){
            subjecus.error = "Please enter subject"
        }
        if(customerMessage.isEmpty()){
            Messagecus.error = "Please enter message"
        }

        val customerId = dbRef.push().key!!

        val customer = CustomerModel(customerId,customerName,customerEmail,customerPhone,
        customerSubject,customerMessage)

        dbRef.child(customerId).setValue(customer)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted sucessfully",Toast.LENGTH_LONG).show()

                namecus.text.clear()
                emailcus.text.clear()
                phonecus.text.clear()
                subjecus.text.clear()
                Messagecus.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()

            }


    }

}