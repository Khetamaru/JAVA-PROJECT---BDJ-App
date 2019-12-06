package kotlinClass

import android.R
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.MediaType.Companion.toMediaType


class LocationAdd : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User
        var date : Date = Date()
        var startTime : Time = Time(0,0,0)
        var endTime : Time = Time(0,0,0)
        var place : String = ""

        var dateText : TextView
        var placeSpinner : Spinner
        var startTimeText : TextView
        var endTimeText : TextView

        var setDate : Button
        var setStartTime : Button
        var setEndTime : Button

        val context = this
        var intent = intent
        var mapper : ObjectMapper = ObjectMapper()
        val client : OkHttpClient = OkHttpClient()
        val intentUser = intent.getIntExtra("idUser", 0)

        var validation : Button
        var back : Button

        setContentView(layout.location_add)

        val request : Request = Request.Builder()
                .url("http://192.168.43.110:8080/user/$intentUser")
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                Log.e("LocationAdd", "fail", e)
            }

            override fun onResponse(call: Call, response: Response) {

                user = mapper.readValue(response.body!!.string(), User::class.java)

                runOnUiThread {

                    dateText = findViewById(id.dateText)
                    setDate = findViewById(id.setDate)

                    startTimeText = findViewById(id.startTime)
                    setStartTime = findViewById(id.setStartTime)

                    endTimeText = findViewById(id.endTime)
                    setEndTime = findViewById(id.setEndTime)

                    placeSpinner = findViewById(id.placeSpinner)

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        val intent = Intent(context, LocationView::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        startActivity(intent)
                    })


                    setDate.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month: Int = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)

                        var datePickerDialog = DatePickerDialog(this@LocationAdd, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            var MonthOfYear = monthOfYear + 1
                            dateText.setText("$dayOfMonth/$MonthOfYear/$year")
                            date = Date("$MonthOfYear/$dayOfMonth/$year")

                        }, year, month, day)

                        datePickerDialog.show()
                    })


                    setStartTime.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val timeSetListener = TimePickerDialog.OnTimeSetListener {

                            timePicker, hour, minute ->

                            cal.set(Calendar.HOUR_OF_DAY, hour)
                            cal.set(Calendar.MINUTE, minute)
                            startTimeText.text = SimpleDateFormat("HH:mm").format(cal.time)
                            startTime = Time(cal.time.time)
                        }
                        TimePickerDialog(this@LocationAdd, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                    })

                    setEndTime.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val timeSetListener = TimePickerDialog.OnTimeSetListener {

                            timePicker, hour, minute ->

                            cal.set(Calendar.HOUR_OF_DAY, hour)
                            cal.set(Calendar.MINUTE, minute)
                            endTimeText.text = SimpleDateFormat("HH:mm").format(cal.time)
                            endTime = Time(cal.time.time)
                        }
                        TimePickerDialog(this@LocationAdd, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                    })

                    val places = arrayOf("BDJ", "Cafet",
                            "I01", "I02", "I03",
                            "I11", "I12A", "I12B", "I13", "I14", "I15", "I16", "I17", "I18",
                            "I21", "I22", "I23", "I24", "I25",
                            "E01", "E02", "E03", "E04", "E05", "E06", "E07", "E08", "E09",
                            "E0S")

                    if (placeSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@LocationAdd, R.layout.simple_spinner_item, places)
                        placeSpinner.adapter = arrayAdapter

                        placeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                place = places[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                place = ""
                            }
                        }
                    }

                    validation = findViewById(id.validation)
                    validation.setOnClickListener { v ->
                        if (dateText.text != "" && startTimeText.text != "" && endTimeText.text != "") {

                            if (testTimeSet(startTime, endTime)) {

                                var temp = startTime
                                startTime = endTime
                                endTime = temp
                            }

                            val location = Location(user, place, date, startTime, endTime)

                            mapper = ObjectMapper()

                            val client = OkHttpClient()

                            val rq = location.toStringWithoutId()

                            val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), rq)

                            val request = Request.Builder()
                                    .url("http://192.168.43.110:8080/location")
                                    .put(body)
                                    .build()

                            client.newCall(request).enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    Log.e("LocationAdd", "fail", e)
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    val intent: Intent
                                    intent = Intent(v.context, LocationView::class.java)
                                    intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                                    startActivity(intent)
                                }
                            })
                        } else {

                            Toast.makeText(this@LocationAdd, "One or all field(s) is empty", Toast.LENGTH_LONG).show()
                            Log.i("LocationAdd", "One or all field(s) is empty")
                        }
                    }
                }
            }
        })
    }

    fun testTimeSet (startTime : Time, endTime : Time) : Boolean {

        if (startTime > endTime) {

                return true
            }

        return false
    }
}