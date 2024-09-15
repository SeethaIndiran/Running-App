package com.example.runningapp.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.runningapp.R
import com.example.runningapp.other.Constants.KEY_NAME
import com.example.runningapp.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {




   @Inject
   lateinit var  sharedPref : SharedPreferences



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()
            if(success){
                Snackbar.make(view,"Saved changes",Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(view,"Please enter all the fields",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private  fun loadFieldsFromSharedPref(){

        val name = sharedPref.getString(KEY_NAME, "")
        val weight = sharedPref.getFloat(KEY_WEIGHT,80f.toFloat())

        etName.setText(name)
        etWeight.setText(weight.toString())
    }

private  fun applyChangesToSharedPref():Boolean{
    val nametext = etName.text.toString()
    val weightText = etWeight.text.toString()
    if(nametext.isEmpty() || weightText.isEmpty()){
        return false
    }

    sharedPref.edit()
        .putString(KEY_NAME, nametext)
        .putFloat(KEY_WEIGHT,weightText.toFloat())
        .apply()


    val toolbarText = "Lets go $nametext"
    requireActivity().tvToolbarTitle.text = toolbarText
    return true

}

}