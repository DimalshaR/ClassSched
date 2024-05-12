package com.example.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todolist.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdatePage : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCardBinding
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= Room.databaseBuilder(
            applicationContext,myDatabase::class.java,"To_Do"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = Data.getData(pos).title
            val priority = Data.getData(pos).priority
            binding.classAndTime.setText(title)
            binding.Cpriority.setText(priority)

            binding.deleteB.setOnClickListener {
                Data.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(pos+1,
                            binding.classAndTime.text.toString(),
                            binding.Cpriority.text.toString())
                    )
                }
                myIntent()
            }

            binding.updateB.setOnClickListener {
                Data.updateData(
                    pos,
                    binding.classAndTime.text.toString(),
                    binding.Cpriority.text.toString()


                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(pos+1,binding.classAndTime.text.toString(),
                            binding.Cpriority.text.toString())
                    )

                }

                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
