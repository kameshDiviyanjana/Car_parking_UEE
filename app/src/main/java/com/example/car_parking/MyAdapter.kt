package com.example.car_parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val usersList: ArrayList<Users>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener=clickListener
    }

    class MyViewHolder(itemView:View,clickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        val tvName:TextView = itemView.findViewById(R.id.tv_name)
        val tvSubject:TextView= itemView.findViewById(R.id.tv_subject)
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = usersList[position].customerName
        holder.tvSubject.text= usersList[position].customerSubject
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}