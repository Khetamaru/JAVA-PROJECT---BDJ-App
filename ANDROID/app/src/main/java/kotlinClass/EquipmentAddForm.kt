package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.Equipment
import com.example.bdjcrusadeinternalappli.R
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class EquipmentAddForm : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var equipment = Equipment()

        var nameEditText: EditText
        var statusEditText: EditText
        var dateRecupTextView: TextView
        var dateRecupButton: Button
        var stateEditText: EditText
        var originEditText: EditText
        var cfDocEditText: EditText
        var ableToBorrowSpinner: Spinner

        var back: Button
        var submit: Button

        setContentView(R.layout.equipment_add_form)

        nameEditText = findViewById(R.id.name)
        statusEditText = findViewById(R.id.status)
        dateRecupTextView = findViewById(R.id.dateRecup)
        dateRecupButton = findViewById(R.id.dateRecupButton)
        stateEditText = findViewById(R.id.state)
        originEditText = findViewById(R.id.origin)
        cfDocEditText = findViewById(R.id.cfDoc)
        ableToBorrowSpinner = findViewById(R.id.ableToBorrow)

        back = findViewById(R.id.back)
        submit = findViewById(R.id.submit)

        dateRecupButton.setOnClickListener(View.OnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog = DatePickerDialog(this@EquipmentAddForm, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var MonthOfYear = monthOfYear + 1
                dateRecupTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                equipment.dateRecup = Date("$dayOfMonth/$MonthOfYear/$year")

            }, year, month, day)

            datePickerDialog.show()
        })

        val choice = arrayOf("yes", "no")

        if (ableToBorrowSpinner != null) {

            val arrayAdapter = ArrayAdapter(this@EquipmentAddForm, R.layout.spinner_item, choice)
            ableToBorrowSpinner.adapter = arrayAdapter
            ableToBorrowSpinner.setSelection(0)

            ableToBorrowSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    equipment.ableToBorrow = choice[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                    equipment.ableToBorrow = ""
                }
            }
        }

        back.setOnClickListener ( View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
        })

        submit.setOnClickListener ( View.OnClickListener {

            equipment.name = nameEditText.text.toString()
            equipment.status = statusEditText.text.toString()
            equipment.state = stateEditText.text.toString()
            equipment.origin = originEditText.text.toString()
            equipment.cfDoc = cfDocEditText.text.toString()

            var stateCheck: String = equipment.allStatesCheck()
            if( stateCheck == "" ) {

                rooterService.changeActivity(Intent(context, EquipmentAddFormNext::class.java), context, intentUser, intentType, "type", equipment)
            }

            else {

                Toast.makeText(this@EquipmentAddForm, stateCheck, Toast.LENGTH_LONG).show()
            }
        })
    }
}