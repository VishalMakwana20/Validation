package com.sarvadhi.validation.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sarvadhi.validation.R
import com.sarvadhi.validation.databinding.ActivityMainBinding
import com.sarvadhi.validation.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
    }
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        binding.viewModel = viewModel
    }

    private fun initObserver() {
        viewModel.listError.observe(this, {
            for (item in it) {
                Log.d("DDD", getString(item.stringId))
            }

            Toast.makeText(this, getString(it[0].stringId), Toast.LENGTH_SHORT).show()
        })

        viewModel.intent.observe(this, {
            if (it == 1)
                Toast.makeText(this, " Validation Success", Toast.LENGTH_SHORT).show()
        })
    }


    private fun initBind() {

    }
}