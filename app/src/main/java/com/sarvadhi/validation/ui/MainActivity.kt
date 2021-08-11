package com.sarvadhi.validation.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sarvadhi.validation.R
import com.sarvadhi.validation.databinding.ActivityMainBinding
import com.sarvadhi.validation.rule.EmailRule
import com.sarvadhi.validation.rule.LengthRule
import com.sarvadhi.validation.rule.NonEmptyRule
import com.sarvadhi.validation.rule.PasswordRule
import com.sarvadhi.validation.validation.FormValidator
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
        viewModel.intent.observe(this, {
            if (it == 1) {
                if (isValidForm())
                    Toast.makeText(this, "validation succesfully", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(binding.etUserName, NonEmptyRule("Empty User Name"))
            .addField(
                binding.etPassword,
                NonEmptyRule("Please enter Password"),
                PasswordRule("Please provide strong Password")
            )
            .addField(
                binding.etEmail,
                NonEmptyRule("Please enter Email"),
                EmailRule("Invalid Email")
            )
            .addField(
                binding.etZipCode,
                NonEmptyRule("Please enter zipcode")
            )
            .addField(
                binding.etPhone,
                NonEmptyRule("Please enter Phone Number"),
                LengthRule(10, "Please enter valid Phone Number")
            )
            .setErrorListener {
                for (error in it) {
                    Log.e("Error", error.error)
                }
            }
            .validate()
    }

    private fun initBind() {

    }
}