package com.example.mvvm_app

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_app.model.Monster
import com.example.mvvm_app.viewmodel.MonsterViewModel
import com.example.mvvm_app.viewmodel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_monster.*

class MonsterActivity : AppCompatActivity() {

    private lateinit var viewModel: MonsterViewModel
    private var reward = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monster)

        val monster = intent.getParcelableExtra<Monster>("Class")
        val damage = intent.getIntExtra("Damage", 0)

        val imageId = monster?.imageId
        val monsterIcon = monster?.monsterIcon
        val name = monster?.name
        val hp = monster?.hp

        reward = monster!!.reward

        progressBar.max = hp!!
        ObjectAnimator.ofInt(progressBar,"progress", hp).start()

        text_MonsterName.text = name

        imageView.setImageResource(imageId!!)

        viewModel = ViewModelProvider(
            this, MyViewModelFactory(
                Monster(
                    imageId,
                    monsterIcon!!, name!!, hp, reward
                )
            )
        ).get(MonsterViewModel::class.java)

        imageView.setOnClickListener {
            hitMonster(damage)
            progressBar.progress = viewModel.getHP()
        }

        viewModel.killed.observe(this, { isDead ->
            if (isDead) {
                Toast.makeText(this,"Monster was killed", Toast.LENGTH_SHORT).show()
                text_MonsterName.text = "KILLED"
                imageView.isEnabled = false
            }
        })

    }

    private fun hitMonster(dmg : Int){
        viewModel.hit(dmg)
        viewModel.checkDeath()
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