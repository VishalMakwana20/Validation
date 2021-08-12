package com.sarvadhi.validation.viewmodels

import Validator
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarvadhi.validation.Utils.Global

class MainViewModel : ViewModel() {
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var zipcode: String? = null
    var phoneNumber: String? = null
    var intent = MutableLiveData<Int>()
    var listError = MutableLiveData<List<Validator.InputError>>()

    fun onSubmitClick() {
//        intent.postValue(1)
        Global.MIN_LENGTH = 10
        Global.MAX_LENGTH = 15
        Validator.prepare()
        Validator.checkUserName(username)
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