package com.example.medicoDoctor.ui.RendezVous

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.medicoDoctor.R
import com.example.medicoDoctor.data.model.DetailsRdvViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.fragment_details_rdv.*

class DetailsRdvFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_rdv, container, false)
        val vmDetails= ViewModelProvider(requireActivity()).get(DetailsRdvViewModel::class.java)
        // Show values in UI.
        val nomDoctorX = (view.findViewById(R.id.nomDoctorX) as TextView)
        val specDoctorX = (view.findViewById(R.id.specDoctorX) as TextView)
        val prixDoctorX = (view.findViewById(R.id.prixDoctorX) as TextView)
        val nomPatientX = (view.findViewById(R.id.nomPatientX) as TextView)
        val dateHeureX = (view.findViewById(R.id.dateHeureX) as TextView)
        nomDoctorX.text = vmDetails.nomMedecin
        specDoctorX.text = vmDetails.specMedecin
        prixDoctorX.text = vmDetails.prix
        nomPatientX.text = vmDetails.nomPatient
        dateHeureX.text = vmDetails.date+" à "+vmDetails.heure
        val content = "{'nomPatient' : '"+vmDetails.nomPatient+"','nomMedecin' : '" +vmDetails.nomMedecin+"', 'date' : '"+vmDetails.date+"', 'heure' : '"+vmDetails.heure+"', 'spec' : '"+vmDetails.specMedecin+"', 'prix' : '"+vmDetails.prix+"'}"
        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width: Int = bitMatrix.getWidth()
            val height: Int = bitMatrix.getHeight()
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            (view.findViewById(R.id.qrImageX) as ImageView).setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        val backButtonBooking =  view.findViewById(R.id.backButtonBooking) as ImageButton
        backButtonBooking.setOnClickListener {
            val navController = (context as Activity).findNavController(R.id.nav_from_home)
            navController.navigate(R.id.action_detailsRdvFragment_to_homeFragment)
        }
        return view
    }


}