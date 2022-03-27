package com.mehmetcanmut.ogrencinot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetcanmut.ogrencinot.databinding.RecyclerRowBinding
import com.mehmetcanmut.ogrencinot.model.Notlar
import com.squareup.picasso.Picasso

class RecyclerAdapter( val notlarList : ArrayList<Notlar>) : RecyclerView.Adapter<RecyclerAdapter.PostHolder>() {


    class PostHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        Picasso.get().load(notlarList.get(position).dowloadUrl).into(holder.binding.recyclerViewImage)
    }

    override fun getItemCount(): Int {
        return notlarList.size
    }


}