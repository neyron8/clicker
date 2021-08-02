package com.example.mvvm_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_app.adapter.CustomAdapter
import com.example.mvvm_app.databinding.ActivityMainBinding
import com.example.mvvm_app.db.PlayerEntity
import com.example.mvvm_app.model.Monster
import com.example.mvvm_app.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomAdapter.OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CustomAdapter
    private lateinit var viewModel1: MainActivityViewModel
    lateinit var player: PlayerEntity
    private var launcher: ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = CustomAdapter(this@MainActivity)
        viewModel1 = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val answer = result.data?.getIntExtra("Rwd", 0)
                player.increaseMoney(answer!!)
                viewModel1.updatePlayer(player)
                text_money.text = player.money.toString()
            }
        }
        init()

        text_money.text = player.money.toString()
        text_damage.text = player.damage.toString()

        upgrade_button.setOnClickListener{
            player = viewModel1.getPlayer(0)
            if(player.money >= player.damage * 3){
                updatePlayerChanges()
            } else {
                Toast.makeText(this,"There is not enough money: ${player.damage * 3} needed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePlayerChanges() {
        player = viewModel1.getPlayer(0)
        player.money -= player.damage * 3
        player.increaseDamage(5)
        text_damage.text = player.damage.toString()
        text_money.text = player.money.toString()
        viewModel1.updatePlayer(player)
    }

    private fun init() {
        recycler_v.layoutManager = LinearLayoutManager(this@MainActivity)
        recycler_v.adapter = adapter

        adapter.addMonster(
            Monster(
                resources.getIdentifier("monster_1", "drawable", packageName),
                "Aboba",
                30,
                50
            )
        )

        adapter.addMonster(
            Monster(
                resources.getIdentifier("monster_2", "drawable", packageName),
                "Punisher",
                90,
                100
            )
        )

        adapter.addMonster(
            Monster(
                resources.getIdentifier("monster_3", "drawable", packageName),
                "Destroyer",
                90,
                300
            )
        )

        player = viewModel1.getPlayer(0)

        if(!this::player.isInitialized){
            Toast.makeText(this,"Playing first time? New player added to database",
                Toast.LENGTH_SHORT).
            show()
            viewModel1.insertPlayer(PlayerEntity(0,5,0))
            player = viewModel1.getPlayer(0)
        }
    }

    override fun onItemClick(position: Int) {
        val clickedMonster = adapter.MonsterList[position]
        val i = Intent(this,MonsterActivity::class.java)
        i.putExtra("Class", clickedMonster)
        i.putExtra("Damage", player.damage)
        launcher?.launch(i)
    }


}