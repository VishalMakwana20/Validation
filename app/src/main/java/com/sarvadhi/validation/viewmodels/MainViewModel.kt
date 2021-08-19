package com.sarvadhi.validation.viewmodels

import Validator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var zipcode: String? = null
    var phoneNumber: String? = null
    var intent = MutableLiveData<Int>()
    var listError = MutableLiveData<List<Validator.InputError>>()

    fun onSubmitClick() {
        Validator.prepare()
        Validator.updateMinMaxLength(10, 15)
            .checkUserName(username)
            .checkPassWord(password)
            .checkEmail(email)
            .checkZipCode(zipcode)
            .checkPhoneNumber(phoneNumber)
            .finish({
                // success
                intent.postValue(1)
            }, {
                listError.postValue(it)
            })
    }
}