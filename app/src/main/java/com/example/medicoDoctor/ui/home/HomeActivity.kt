package com.example.medicoDoctor.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.medicoDoctor.R
import com.example.medicoDoctor.data.model.DetailsRdvViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, "Pas d'infos !!!", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)
                    val vmDetails= ViewModelProvider(this).get(DetailsRdvViewModel::class.java)
                    // Show values in UI.
                    vmDetails.nomPatient = obj.getString("nomPatient")
                    vmDetails.nomMedecin = obj.getString("nomMedecin")
                    vmDetails.date = obj.getString("date")
                    vmDetails.heure = obj.getString("heure")
                    vmDetails.prix = obj.getString("prix")
                    vmDetails.specMedecin = obj.getString("spec")
                    val navController = this.findNavController(R.id.nav_from_home)
                    navController.navigate(R.id.action_homeFragment_to_detailsRdvFragment)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}