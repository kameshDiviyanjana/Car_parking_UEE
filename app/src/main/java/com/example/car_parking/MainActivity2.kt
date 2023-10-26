package com.example.car_parking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class mainActivity2 : AppCompatActivity() {
    private lateinit var tvcusId:TextView
    private lateinit var tvNames:TextView
    private lateinit var tvEmail:TextView
    private lateinit var tvPhone:TextView
    private lateinit var tvSubject: TextView
    private lateinit var tvMessage:TextView
    private lateinit var tvEdit:Button
    private lateinit var tvDelete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initView()
        setValuesToViews()

        tvEdit.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("customerId").toString(),
                intent.getStringExtra("customerName").toString()
            )
        }
        tvDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("customerId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this ,"Your data deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this, Newazctivety::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this ,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }
    }


    private fun initView() {
        tvcusId=findViewById(R.id.tv_cusId)
        tvNames = findViewById(R.id.tv_Names)
        tvEmail = findViewById(R.id.tv_Email)
        tvPhone = findViewById(R.id.tv_Phone)
        tvSubject = findViewById(R.id.tv_Subject)
        tvMessage = findViewById(R.id.tv_Message)
        tvEdit = findViewById(R.id.tvEdit)
        tvDelete = findViewById(R.id.tvDelete)
    }

    private fun setValuesToViews(){
        tvcusId.text= intent.getStringExtra("customerId")
        tvNames.text= intent.getStringExtra("customerName")
        tvEmail.text= intent.getStringExtra("customerEmail")
        tvPhone.text= intent.getStringExtra("customerPhone")
        tvSubject.text= intent.getStringExtra("customerSubject")
        tvMessage.text= intent.getStringExtra("customerMessage")
    }

    private fun openUpdateDialog(
        customerId: String,
        customerName:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_main5,null)

        mDialog.setView(mDialogView)

        val etName = mDialogView.findViewById<EditText>(R.id.etName)
        val etEmail = mDialogView.findViewById<EditText>(R.id.etEmail)
        val etPhone = mDialogView.findViewById<EditText>(R.id.etPhone)
        val etSubject = mDialogView.findViewById<EditText>(R.id.etSubject)
        val etMessage = mDialogView.findViewById<EditText>(R.id.etMessage)

        val updateconfirm = mDialogView.findViewById<Button>(R.id.updateconfirm)

        etName.setText( intent.getStringExtra("customerName").toString())
        etEmail.setText( intent.getStringExtra("customerEmail").toString())
        etPhone.setText( intent.getStringExtra("customerPhone").toString())
        etSubject.setText( intent.getStringExtra("customerSubject").toString())
        etMessage.setText( intent.getStringExtra("customerMessage").toString())

        mDialog.setTitle("Updating $customerName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        updateconfirm.setOnClickListener {
            updateCusData(
                customerId,
                etName.text.toString(),
                etEmail.text.toString(),
                etPhone.text.toString(),
                etSubject.text.toString(),
                etMessage.text.toString()
            )

            Toast.makeText(applicationContext,"Your Data Updated",Toast.LENGTH_LONG).show()

            //we wre setting the updated data to our textviews
            tvNames.text= etName.text.toString()
            tvEmail.text= etEmail.text.toString()
            tvPhone.text=  etPhone.text.toString()
            tvSubject.text= etSubject.text.toString()
            tvMessage.text=  etMessage.text.toString()

            alertDialog.dismiss()
        }


    }

    private fun updateCusData(
        id:String,
        name:String,
        email:String,
        phone:String,
        subject:String,
        message:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customers").child(id)
        val cusInfo = CustomerModel(id,name,email,phone,subject,message)
        dbRef.setValue(cusInfo)
    }

}