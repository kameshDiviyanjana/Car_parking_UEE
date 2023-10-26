package com.example.car_parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Displayeadapter(private val haltlist: ArrayList<Bookpark>) : RecyclerView.Adapter<Displayeadapter.MyviweHolder> () {


    lateinit var  l : onItemClickListener
    interface onItemClickListener{

        fun inItemckick(position: Int)
    }

    fun setonItemClickListener(ls : onItemClickListener) {
        l = ls
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviweHolder {
        val bushalt = LayoutInflater.from(parent.context).inflate(R.layout.display_book,parent,false)
        return MyviweHolder(bushalt,l)
    }

    override fun onBindViewHolder(holder: MyviweHolder, position: Int) {
        val currentitem = haltlist[position]
        holder.name.text =currentitem.names
        holder.phonenumber.text =currentitem.phonenumber
        holder.carnumder.text =currentitem.vehicnumer
        holder.slot.text =currentitem.slotbook
        holder.time.text =currentitem.timeresive
        holder.pakingplaces.text =currentitem.pakingplaces




    }

    override fun getItemCount(): Int {
        return haltlist.size
    }

    class MyviweHolder(itemviewdispaly: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemviewdispaly){

        val name : TextView = itemviewdispaly.findViewById(R.id.haltnew)
        val phonenumber : TextView = itemviewdispaly.findViewById(R.id.haltnew2)
        val carnumder : TextView = itemviewdispaly.findViewById(R.id.haltnew3)
        val slot : TextView = itemviewdispaly.findViewById(R.id.haltnew4)
        val time : TextView = itemviewdispaly.findViewById(R.id.haltnew5)
        val pakingplaces : TextView = itemviewdispaly.findViewById(R.id.haltnew6)



        init {
            /*Bushalt.setOnClickListener{v :View ->
                val position : Int =adapterPosition
                Toast.makeText(Bushalt.context,"you click work",Toast.LENGTH_LONG).show()*/

            itemviewdispaly.setOnClickListener{

                listener.inItemckick(adapterPosition)


            }
        }
    }
}