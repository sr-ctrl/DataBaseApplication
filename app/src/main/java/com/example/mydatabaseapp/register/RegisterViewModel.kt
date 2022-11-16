package com.example.mydatabaseapp.register

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.mydatabaseapp.database.RegisterEntity
import com.example.mydatabaseapp.database.RegisterRepository
import kotlinx.coroutines.*


class RegisterViewModel(private val repository: RegisterRepository, application: Application) :
    AndroidViewModel(application), Observable {

    init {
        Log.i("MYTAG", "init")
    }

    private var userdata: String? = null



    var userDetailsLiveData = MutableLiveData<Array<RegisterEntity>>()

    @Bindable
    val inputFirstName = MutableLiveData<String>()

    @Bindable
    val inputLastName = MutableLiveData<String>()

    @Bindable
    val inputUsername = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val phoneNumber = MutableLiveData<String>()

    @Bindable
    val selectedGender = MutableLiveData<String>()

    @Bindable
    val selectedYear = MutableLiveData<String>()

    @Bindable
    val selectedMonth = MutableLiveData<String>()

    @Bindable
    val selectedDay = MutableLiveData<String>()



    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateto = MutableLiveData<Boolean>()

    val navigateto: LiveData<Boolean>
        get() = _navigateto

    private val _errorToast = MutableLiveData<Boolean>()

    private var _errorToastMsg:String? = null

    val errotoast: LiveData<Boolean>
        get() = _errorToast

    val errorToastMsg: String?
        get() = _errorToastMsg

    private val _errorToastUsername = MutableLiveData<Boolean>()

    val errotoastUsername: LiveData<Boolean>
        get() = _errorToastUsername


    fun sumbitButton() {
        Log.i("MYTAG", "Inside SUBMIT BUTTON")
        if (inputFirstName.value == null || inputLastName.value == null || inputUsername.value == null || inputPassword.value == null
            || inputEmail.value ==  null || phoneNumber.value == null || selectedGender.value == null
            ||selectedYear.value == null || selectedMonth.value == null || selectedDay.value == null) {
            if (inputFirstName.value == null){
                _errorToastMsg = "please Enter First Name"
            }else if (inputLastName.value == null ){
                _errorToastMsg = "please Enter Last Name"
            }else if (inputEmail.value == null ){
                _errorToastMsg = "please Enter Email id"
            }else if (!(inputEmail.value!!.matches(Patterns.EMAIL_ADDRESS.toRegex())) ){
                _errorToastMsg = "please Enter Valid Email id"
            }else if (phoneNumber.value == null ){
                _errorToastMsg = "please Enter Phone Number"
            }else if (phoneNumber.value!!.length < 10){
                _errorToastMsg = "please Enter Valid Phone number"
            }else if (selectedGender.value == null){
                _errorToastMsg = "please Select Gender"
            }else if (selectedYear.value == null){
                _errorToastMsg = "please Select Birth Year"
            }else if (selectedMonth.value == null){
                _errorToastMsg = "please Select Birth Month"
            }else if (selectedDay.value == null){
                _errorToastMsg = "please Select Birth Day"
            }else if (inputUsername.value == null){
                _errorToastMsg = "please Enter User Name"
            }else if (inputPassword.value == null){
                _errorToastMsg = "please Select Password"
            }else if (inputPassword.value!!.length < 4){
                _errorToastMsg = "please Password Must Have At Least 4 Characters."
            }else{
                _errorToastMsg = "Something Went Wrong."
            }

            _errorToast.value = true
        } else {
            uiScope.launch {
//            withContext(Dispatchers.IO) {
                val usersNames = repository.getUserName(inputUsername.value!!)
                Log.i("MYTAG", usersNames.toString() + "------------------")
                if (usersNames != null) {
                    _errorToastUsername.value = true
                    Log.i("MYTAG", "Inside if Not null")
                } else {
                    Log.i("MYTAG", userDetailsLiveData.value.toString() + "ASDFASDFASDFASDF")
                    Log.i("MYTAG", "OK im in")
                    val firstName = inputFirstName.value!!
                    val lastName = inputLastName.value!!
                    val username = inputUsername.value!!
                    val password = inputPassword.value!!
                    val email_id = inputEmail.value!!
                    val phonenumber = phoneNumber.value!!
                    val selectect_gender = selectedGender.value!!
                    val selected_year = selectedYear.value!!
                    val selected_month = selectedMonth.value!!
                    val selected_day = selectedDay.value!!

                    Log.i("MYTAG", "insidi Sumbit")
                    insert(RegisterEntity(0, firstName, lastName, username, password,email_id,phonenumber,
                        selectect_gender,selected_year,selected_month,selected_day))
                    inputFirstName.value = null
                    inputLastName.value = null
                    inputUsername.value = null
                    inputPassword.value = null
                    inputEmail.value = null
                    phoneNumber.value = null
                    selectedGender.value = null
                    selectedYear.value = null
                    selectedMonth.value = null
                    selectedDay.value = null
                    _navigateto.value = true
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun doneNavigating() {
        _navigateto.value = false
        Log.i("MYTAG", "Done navigating ")
    }

    fun donetoast() {
        _errorToast.value = false
        Log.i("MYTAG", "Done taoasting ")
    }

    fun donetoastUserName() {
        _errorToast.value = false
        Log.i("MYTAG", "Done taoasting  username")
    }

    private fun insert(user: RegisterEntity): Job = viewModelScope.launch {
        repository.insert(user)
    }

//    fun clearALl():Job = viewModelScope.launch {
//        repository.deleteAll()
//    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}





