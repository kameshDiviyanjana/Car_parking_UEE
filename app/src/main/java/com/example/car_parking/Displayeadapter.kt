package com.example.car_parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Displayeadapter(private val haltlist: ArrayList<Bookpark>) : RecyclerView.Adapter<Displayeadapter.MyviweHolder> () {


    lateinit var  l : onItemClickListener
    lateinit var jh :okkk

    interface onItemClickListener{

        fun inItemclick(position: Int)

    }
   interface  okkk  {
       fun paymove(position: Int)
   }
    fun setonItemClickListener(ls : onItemClickListener) {
        l = ls
    }
    fun sdsf(lsd : okkk){
        jh = lsd
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviweHolder {
        val bushalt = LayoutInflater.from(parent.context).inflate(R.layout.display_book,parent,false)
        return MyviweHolder(bushalt,l,jh)
    }

    override fun onBindViewHolder(holder: MyviweHolder, position: Int) {
        val currentitem = haltlist[position]
        holder.name.text =currentitem.name
        holder.phonenumber.text = currentitem.phoneNumber.toString()
        holder.Vehiclenumber.text =currentitem.vehicleNumber
        holder.date.text = currentitem.date
        holder.slot.text = currentitem.slotNumber.toString()
        holder.starttime.text =currentitem.startTime
        holder.endtime.text =currentitem.endTime
        holder.pakingplaces.text =currentitem.parkingName




    }

    override fun getItemCount(): Int {
        return haltlist.size
    }

    class MyviweHolder(itemviewdispaly: View, listener: onItemClickListener, jh: okkk) : RecyclerView.ViewHolder(itemviewdispaly){

        val name : TextView = itemviewdispaly.findViewById(R.id.haltnew)
        val phonenumber : TextView = itemviewdispaly.findViewById(R.id.haltnew2)
        val Vehiclenumber : TextView = itemviewdispaly.findViewById(R.id.haltnew6)
        val date : TextView = itemviewdispaly.findViewById(R.id.haltnew16)
        val starttime : TextView = itemviewdispaly.findViewById(R.id.haltnew13)
        val endtime : TextView = itemviewdispaly.findViewById(R.id.haltnew3)
        val pakingplaces : TextView = itemviewdispaly.findViewById(R.id.haltnew4)
        val slot : TextView = itemviewdispaly.findViewById(R.id.haltnew5)
        val f : Button = itemviewdispaly.findViewById(R.id.btngotopayment)

        init {
            /*Bushalt.setOnClickListener{v :View ->
                val position : Int =adapterPosition
                Toast.makeText(Bushalt.context,"you click work",Toast.LENGTH_LONG).show()*/

            itemviewdispaly.setOnClickListener{

                listener.inItemclick(adapterPosition)

                jh.paymove(adapterPosition)
            }





        }
    }
}