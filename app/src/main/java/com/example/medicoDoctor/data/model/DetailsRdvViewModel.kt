package com.example.medicoDoctor.data.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.medicoDoctor.ui.home.HomeFragment

class DetailsRdvViewModel: ViewModel() {

    var nomMedecin:String="00"
    var nomPatient:String="00"
    var specMedecin:String="00"
    var heure: String="00"
    var date: String="00"
    var prix: String="00"

}