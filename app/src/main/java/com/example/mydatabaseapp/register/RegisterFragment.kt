package com.example.mydatabaseapp.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.mydatabaseapp.R
import com.example.mydatabaseapp.database.RegisterDatabase
import com.example.mydatabaseapp.database.RegisterRepository
import com.example.mydatabaseapp.databinding.RegisterHomeFragmentBinding
import com.example.mydatabaseapp.model.Gender


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var genderList: ArrayList<Gender>
    var listofGender = ArrayList<String>()
    var ageRange = ArrayList<String>()
    var listofyears = ArrayList<String>()
    var listofmonths = ArrayList<String>()
    var listofdays = ArrayList<String>()

    lateinit var binding:RegisterHomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_home_fragment, container, false
        )

        val application = requireNotNull(this.activity).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.myViewModel = registerViewModel

        addDummyData()

        Log.d("list of gender", "List of genders: $listofGender")
        Log.d("list of age", "List of age: $ageRange")

        genderspinner(listofGender)
        yearSpinner(listofyears)
        monthSpinner(listofmonths)
        daySpinner(listofdays)
        binding.lifecycleOwner = this

        
        registerViewModel.navigateto.observe(requireActivity(), Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                displayUsersList()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.userDetailsLiveData.observe(requireActivity(), Observer {
            Log.i("MYTAG",it.toString()+"000000000000000000000000")
        })


        registerViewModel.errotoast.observe(requireActivity(), Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), registerViewModel.errorToastMsg.toString(), Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errotoastUsername.observe(requireActivity(), Observer { hasError->
            if(hasError==true){
                Toast.makeText(requireContext(), "UserName Already taken", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })

        registerViewModel

        return binding.root
    }

    private fun daySpinner(listDays: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, listDays)
        binding.selectDaySpinner.setAdapter(adapter)
        binding.selectDaySpinner.dropDownHeight = 600
        binding.selectDaySpinner.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                registerViewModel.selectedDay.value = listDays.get(position)
            }
    }

    private fun monthSpinner(monthsList: ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, monthsList)
        binding.selectMonthSpinner.setAdapter(adapter)
        binding.selectMonthSpinner.dropDownHeight = 600
        binding.selectMonthSpinner.onItemClickListener = object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                registerViewModel.selectedMonth.value = monthsList.get(position)
            }

        }
    }

    private fun yearSpinner(yearList:ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, listofyears)
        binding.selectYearSpinner.setAdapter(adapter)
        binding.selectYearSpinner.dropDownHeight = 600
        binding.selectYearSpinner.onItemClickListener = object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                registerViewModel.selectedYear.value = yearList.get(position)
            }
        }
    }

    private fun genderspinner(genderList:ArrayList<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, genderList)
        binding.selectGenderSpinner.setAdapter(adapter)
        binding.selectGenderSpinner.onItemClickListener = object :
            AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                registerViewModel.selectedGender.value = genderList.get(position)
            }

        }
    }

    private fun addDummyData() {
        listofGender = ArrayList()
        listofGender.add("Male")
        listofGender.add("Female")
        for (age in 18..50){
            ageRange.add(age.toString())
        }
        for (year in 1972..2022){
            listofyears.add(year.toString())
        }
        for (month in 1..12){
            listofmonths.add(month.toString())
        }
        for (day in 1..31){
            listofdays.add(day.toString())
        }

    }


    private fun displayUsersList() {
        Log.i("MYTAG","insidisplayUsersList")
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}