package com.example.medicoDoctor.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.medicoDoctor.R
import com.example.medicoDoctor.data.model.DetailsRdvViewModel
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {
    internal var qrScanIntegrator: IntentIntegrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val scanButton =  view.findViewById(R.id.scanButton) as Button
        scanButton.setOnClickListener {
            performAction()
        }

        qrScanIntegrator = IntentIntegrator(context as Activity)

        super.onViewCreated(view, savedInstanceState)
    }
    private fun performAction() {
        qrScanIntegrator?.initiateScan()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, "Pas d'infos !!!", Toast.LENGTH_LONG).show()
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
                    vmDetails.prix = obj.getString("nomPatient")
                    vmDetails.specMedecin = obj.getString("prix")

                } catch (e: JSONException) {
                    e.printStackTrace()
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}