package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.util.*

class EquipmentManagingFullInfoDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var nameEditText: EditText
        var statusEditText: EditText
        var recupDateTextView: TextView
        var recupDateButton: Button
        var stateEditText: EditText
        var originEditText: EditText
        var cfdocEditText: EditText
        var ableToBorrowSpinner: Spinner

        var back: Button
        var save: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEquipment = intent.getIntExtra("idEquipment", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var equipment: Equipment

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("equipment", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingFullInfoDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                equipment = switchType(intentType, response.body, mapper)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_details)

                    nameEditText = findViewById(R.id.name)
                    statusEditText = findViewById(R.id.status)
                    recupDateTextView = findViewById(R.id.recupDate)
                    recupDateButton = findViewById(R.id.dateRecupButton)
                    stateEditText = findViewById(R.id.state)
                    originEditText = findViewById(R.id.origin)
                    cfdocEditText = findViewById(R.id.cfDoc)
                    ableToBorrowSpinner = findViewById(R.id.ableToBorrow)

                    back = findViewById(R.id.back)
                    save = findViewById(R.id.save)

                    nameEditText.setText(equipment.name)
                    statusEditText.setText(equipment.status)

                    var date = equipment.dateRecup.toString().split(" ")
                    recupDateTextView.setText(date[2] + " " + date[1] + " " + date[5])

                    stateEditText.setText(equipment.state)
                    originEditText.setText(equipment.origin)
                    cfdocEditText.setText(equipment.cfDoc)

                    val choice = arrayOf("yes", "no")
                    var i = 0
                    var ableToBorrowSpinnerSetter = 0

                    for(choice in choice) {

                        if(choice == equipment.ableToBorrow) {

                            ableToBorrowSpinnerSetter = i
                        }

                        i++
                    }

                    if (ableToBorrowSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@EquipmentManagingFullInfoDetail, R.layout.spinner_item, choice)
                        ableToBorrowSpinner.adapter = arrayAdapter
                        ableToBorrowSpinner.setSelection(ableToBorrowSpinnerSetter)

                        ableToBorrowSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                equipment.ableToBorrow = choice[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                equipment.ableToBorrow = ""
                            }
                        }
                    }

                    recupDateButton.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month: Int = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)

                        var datePickerDialog = DatePickerDialog(this@EquipmentManagingFullInfoDetail, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            var MonthOfYear = monthOfYear + 1
                            recupDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                            equipment.dateRecup = Date("$dayOfMonth/$MonthOfYear/$year")

                        }, year, month, day)

                        datePickerDialog.show()
                    })

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    save.setOnClickListener(View.OnClickListener {

                        equipment.name = nameEditText.text.toString()
                        equipment.status = statusEditText.text.toString()
                        equipment.state = stateEditText.text.toString()
                        equipment.origin = originEditText.text.toString()
                        equipment.cfDoc = cfdocEditText.text.toString()

                        var toastMessage: String = equipment.allStatesCheck()
                        if(toastMessage == "") {

                            requestService.requestBuilderPut(intentType, equipment.toString())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingFullInfoDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingFullInfoDetail, "Saved", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                        else {

                            runOnUiThread {

                                Toast.makeText(this@EquipmentManagingFullInfoDetail, toastMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        })
    }

    private fun boardGameSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), BoardGame::class.java)
        return equipment.extarct()
    }

    private fun videoGameSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), VideoGame::class.java)
        return equipment.extarct()
    }

    private fun gameConsoleSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), GameConsole::class.java)
        return equipment.extarct()
    }

    private fun hardwareSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), Hardware::class.java)
        return equipment.extarct()
    }

    private fun otherSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), Other::class.java)
        return equipment.extarct()
    }

    fun switchType(intentType: String, body: ResponseBody?, mapper: ObjectMapper): Equipment {

        when (intentType) {

            "boardGame" -> return boardGameSet(body, mapper)

            "videoGame" -> return videoGameSet(body, mapper)

            "gameConsole" -> return gameConsoleSet(body, mapper)

            "hardware" -> return hardwareSet(body, mapper)

            else -> return otherSet(body, mapper)
        }
    }
}