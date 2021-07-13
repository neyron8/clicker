package com.example.mvvm_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_app.model.Monster
import com.example.mvvm_app.viewmodel.MonsterViewModel
import com.example.mvvm_app.viewmodel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_monster.*
import kotlinx.android.synthetic.main.monster_item.*

class MonsterActivity : AppCompatActivity() {

    private lateinit var viewModel: MonsterViewModel
    var reward = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster)

        val monster = intent.getParcelableExtra<Monster>("Class")

        /*val imageId = intent.getIntExtra("ImageId", 0)
        val name = intent.getStringExtra("Name")
        val hp = intent.getIntExtra("HP",0)
        reward = intent.getIntExtra("Reward", 0)*/

        val imageId = monster?.imageId
        val name = monster?.name
        val hp = monster?.hp
        reward = monster!!.reward

        //All this must moved to viewmodel
        //monster = Monster(name,hp,reward)
        text_MonsterHP.text = hp.toString()
        text_MonsterName.text = name
        //player may be built in room db so putExtra will be depricated

        viewModel = ViewModelProvider(this,MyViewModelFactory(Monster(imageId!!, name!!, hp!!,reward)))
            .get(MonsterViewModel::class.java)

        imageView.setOnClickListener {
            hitMonster()
        }

        viewModel.hp.observe(this, {
            display(it)
        })

        viewModel.killed.observe(this, { isDead ->
            if (isDead) {
                Toast.makeText(this,"Monster was killed", Toast.LENGTH_SHORT).show()
                text_MonsterName.text = "KILLED"
                imageView.isEnabled = false
            }
        })
        //val player_money =
        //val player_damage =

    }

    private fun hitMonster(){
        viewModel.hit(5)
        viewModel.checkDeath()
        //Toast.makeText(this,"Dice rolled", Toast.LENGTH_SHORT).show()
    }

    private fun display(value : Int){
        text_MonsterHP.text = value.toString()
    }

    fun OnClick(view: View){
        if(viewModel.killed.value == true){
            Toast.makeText(this,"Dice rolled", Toast.LENGTH_SHORT).show()
            val i = Intent()
            i.putExtra("Rwd", reward)
            setResult(RESULT_OK,i)
            finish()
        }
    }
}