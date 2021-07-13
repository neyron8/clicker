package com.example.mvvm_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_app.adapter.CustomAdapter
import com.example.mvvm_app.databinding.ActivityMainBinding
import com.example.mvvm_app.db.PlayerEntity
import com.example.mvvm_app.model.Monster
import com.example.mvvm_app.viewmodel.MainActivityViewModel
import com.example.mvvm_app.viewmodel.RollViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CustomAdapter
    lateinit var viewModel1: MainActivityViewModel
    private var launcher: ActivityResultLauncher<Intent>? = null

    private val viewModel by lazy {
        ViewModelProvider(this).get(RollViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = CustomAdapter(this@MainActivity)
        setContentView(binding.root)

        viewModel1 = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val answer = result.data?.getIntExtra("Rwd", 0)
                val sum = Integer.parseInt(text_money.text as String) + answer!!
                text_money.text = sum.toString()
            }
        }
        init()
        roll_button.setOnClickListener{
            Toast.makeText(this,"${viewModel1.getPlayer(0).money}", Toast.LENGTH_SHORT).show()
            val damage = Integer.parseInt(text_money.text as String)
        }
        /*roll_button.setOnClickListener{
            val damage = Integer.parseInt(text_money.text as String)
            viewModel1.insertPlayer(PlayerEntity(0,5,damage))
        }*/

        viewModel.roll_value.observe(this, {
            displayDice(it)
        })
    }

    private fun roll_dice(){
        viewModel.roll()
        Toast.makeText(this,"Dice rolled", Toast.LENGTH_SHORT).show()
    }

    private fun displayDice(value : Int){
        textView.text = if (value > 0) value.toString() else " "
    }

    private fun init() {
        recycler_v.layoutManager = LinearLayoutManager(this@MainActivity)
        recycler_v.adapter = adapter
        //Maybe will be moved into adapter
        adapter.addMonster(Monster(12, "ABOBA", 30, 5000))
        adapter.addMonster(Monster(13, "Punisher", 900, 531000))
    }

    override fun onItemClick(position: Int) {
        val clickedMonster = adapter.MonsterList[position]
        val i = Intent(this,MonsterActivity::class.java)
        i.putExtra("Class", clickedMonster)
        /*val i = Intent(this,MonsterActivity::class.java)
        i.putExtra("ImageId", clickedMonster.imageId)
        i.putExtra("Name", clickedMonster.name)
        i.putExtra("HP", clickedMonster.hp)
        i.putExtra("Reward", clickedMonster.reward)*/
        launcher?.launch(i)
    }
}