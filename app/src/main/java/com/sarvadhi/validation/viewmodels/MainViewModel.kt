package com.sarvadhi.validation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var zipcode: String? = null
    var phoneNumber: String? = null
    var intent = MutableLiveData<Int>()

    fun onSubmitClick() {
        intent.postValue(1)
    }
}