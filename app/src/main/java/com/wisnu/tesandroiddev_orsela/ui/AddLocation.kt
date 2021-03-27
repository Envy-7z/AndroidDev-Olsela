package com.wisnu.tesandroiddev_orsela.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wisnu.tesandroiddev_orsela.R
import com.wisnu.tesandroiddev_orsela.data.db.OrselaDatabase
import com.wisnu.tesandroiddev_orsela.data.db.entity.Orsela
import com.wisnu.tesandroiddev_orsela.data.repo.OrselaRepo
import com.wisnu.tesandroiddev_orsela.ui.fragment.AllFragment
import com.wisnu.tesandroiddev_orsela.ui.viewmodel.OrselaViewModel
import com.wisnu.tesandroiddev_orsela.ui.viewmodel.OrselaViewModelFactory
import com.wisnu.tesandroiddev_orsela.utils.Coroutines
import com.wisnu.tesandroiddev_orsela.utils.UtilExtensions.myToast
import com.wisnu.tesandroiddev_orsela.utils.UtilExtensions.setTextEditable
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocation : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var viewModel: OrselaViewModel
    private lateinit var orselaDatabase: OrselaDatabase
    private lateinit var repository: OrselaRepo
    private lateinit var factory: OrselaViewModelFactory
    private lateinit var mMap: GoogleMap

    private var orsela: Orsela? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)

        //@todo bad practice because boilerplate code, but i'll be change this later using DI.
        orselaDatabase = OrselaDatabase(this)
        repository = OrselaRepo(orselaDatabase)
        factory = OrselaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[OrselaViewModel::class.java]

        orsela = intent.extras?.getParcelable(AllFragment.ORSELA_DATA)


        initClick()
        initView()


    }
    private fun initView() {

        if (orsela != null) { //this is for set data to form and update data
            etNama.setTextEditable(orsela?.name ?: "")
            etAddress.setTextEditable(orsela?.address ?: "")
            etCity.setTextEditable(orsela?.city ?:"")
            etZip.setTextEditable(orsela?.zip .toString()?:"")

            btnAccept.text = getString(R.string.update)
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun initClick() {
        btnCancel.setOnClickListener {
            finish()
        }
        btnAccept.setOnClickListener {
            saveData()
            finish()
        }
        close.setOnClickListener {
            finish()
        }
    }
    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rbLeft ->
                    if (checked) {
                        rbLeft.setTextColor(Color.WHITE)
                        rbRight.setTextColor(Color.BLUE)
                    }
                R.id.rbRight ->
                    if (checked) {
                        rbLeft.setTextColor(Color.BLUE)
                        rbRight.setTextColor(Color.WHITE)
                    }
            }
        }
    }


    private fun saveData() {
        val id = if (orsela != null) orsela?.id else null
        val title = etNama.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val city = etCity.text.toString().trim()
        val zip = etZip.text.toString().toInt()

        if (title.isEmpty() || address.isEmpty() || city.isEmpty() ) {
            myToast(getString(R.string.form_empty))
            return
        }

        val orsela = Orsela(id = id, name = title, address = address, city = city,zip = zip)
        Coroutines.main {
            if (id != null) { //for update note
                viewModel.updateNote(orsela).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert note
                viewModel.insertNote(orsela).also {
                    myToast(getString(R.string.success_save))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}