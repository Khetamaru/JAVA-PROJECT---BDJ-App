package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import java.io.IOException
import java.util.*
import okhttp3.MediaType.Companion.toMediaType

class LoaningAddNext : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User
        var equipment: Equipment
        var startDate: Date = Date()
        var endDate: Date = Date()

        var startDateText: TextView
        var endDateText: TextView

        var setStartDate: Button
        var setEndDate: Button

        val context = this
        var intent = intent

        var mapper = ObjectMapper()

        var rooterService = RooterService()
        var requestService = RequestService()

        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEquipment = intent.getIntExtra("idEquipment", 0)

        var validation: Button
        var back: Button

        setContentView(layout.loaning_add_next)

        requestService.requestBuilderGet("user", intentUser)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                Toast.makeText(this@LoaningAddNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {

                user = mapper.readValue(response.body!!.string(), User::class.java)

                requestService.requestBuilderGet("equipment", intentEquipment)
                        .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {

                        Toast.makeText(this@LoaningAddNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call, response: Response) {

                        equipment = mapper.readValue(response.body!!.string(), Equipment::class.java)

                        startDateText = findViewById(id.startDateText)
                        endDateText = findViewById(id.endDateText)

                        setStartDate = findViewById(id.setStartDate)
                        setEndDate = findViewById(id.setEndDate)

                        back = findViewById(id.back)
                        validation = findViewById(id.validation)

                        setStartDate.setOnClickListener(View.OnClickListener {

                            val cal = Calendar.getInstance()
                            val year = cal.get(Calendar.YEAR)
                            val month: Int = cal.get(Calendar.MONTH)
                            val day = cal.get(Calendar.DAY_OF_MONTH)

                            var datePickerDialog = DatePickerDialog(this@LoaningAddNext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                                var MonthOfYear = monthOfYear + 1
                                startDateText.setText("$dayOfMonth/$MonthOfYear/$year")
                                startDate = Date("$MonthOfYear/$dayOfMonth/$year")

                            }, year, month, day)

                            datePickerDialog.show()
                        })

                        setEndDate.setOnClickListener(View.OnClickListener {

                            val cal = Calendar.getInstance()
                            val year = cal.get(Calendar.YEAR)
                            val month: Int = cal.get(Calendar.MONTH)
                            val day = cal.get(Calendar.DAY_OF_MONTH)

                            var datePickerDialog = DatePickerDialog(this@LoaningAddNext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                                var MonthOfYear = monthOfYear + 1
                                endDateText.setText("$dayOfMonth/$MonthOfYear/$year")
                                endDate = Date("$MonthOfYear/$dayOfMonth/$year")

                            }, year, month, day)

                            datePickerDialog.show()
                        })

                        back.setOnClickListener(View.OnClickListener {

                            rooterService.changeActivity(Intent(context, LoaningAddEquipment::class.java), context, intentUser)
                        })

                        validation.setOnClickListener { v ->
                            if (startDateText.text != "" && endDateText.text != "") {

                                if (testDateSet(startDate, endDate)) {

                                    var temp = startDate
                                    startDate = endDate
                                    endDate = temp
                                }

                                val loaning = Loaning(user, equipment, startDate, endDate, "In Progress")

                                mapper = ObjectMapper()

                                requestService.requestBuilderPut("loaning", loaning.toStringWithoutId())
                                        .enqueue(object : Callback {

                                    override fun onFailure(call: Call, e: IOException) {
                                        Toast.makeText(this@LoaningAddNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }

                                    @Throws(IOException::class)
                                    override fun onResponse(call: Call, response: Response) {

                                        rooterService.changeActivity(Intent(v.context, LoaningPage::class.java), context, intentUser)
                                    }
                                })
                            } else {

                                Toast.makeText(this@LoaningAddNext, "One or all field(s) is empty", Toast.LENGTH_LONG).show()
                                Log.i("LoaningAddNext", "One or all field(s) is empty")
                            }
                        }
                    }
                })
            }
        })
    }

    fun testDateSet(startDate: Date, endDate: Date): Boolean {

        if (startDate.time > endDate.time) {

            return true
        }

        return false
    }
}