package com.example.mvvm_app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_app.MainActivity
import com.example.mvvm_app.R
import com.example.mvvm_app.databinding.MonsterItemBinding
import com.example.mvvm_app.model.Monster
import kotlinx.android.synthetic.main.monster_item.view.*
import java.util.ArrayList

class CustomAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    val MonsterList = ArrayList<Monster>()

    inner class MyViewHolder(item: View, val listener: OnItemClickListener): RecyclerView.ViewHolder(item), View.OnClickListener {
        val binding = MonsterItemBinding.bind(item)

        fun bind(monster: Monster) = with(binding){
            /*monsterIcon.setImageResource(monster.imageId)
            monsterIcon.layoutParams.height = 60 // OR
            monsterIcon.layoutParams.width = 60*/
            textMonsterName.text = monster.name
            textMonsterHP.text = monster.hp.toString()
            textMonsterReward.text = monster.reward.toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monster_item, parent, false)
        return MyViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(MonsterList[position])
        holder.itemView.button.setOnClickListener{
            MonsterList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return MonsterList.size
    }

    fun addMonster(monster: Monster){
        MonsterList.add(monster)
        Log.e("Aboba",monster.name)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
