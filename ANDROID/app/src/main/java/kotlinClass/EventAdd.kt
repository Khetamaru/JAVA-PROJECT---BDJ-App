 package kotlinClass

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

 @Suppress("DEPRECATION")
 class EventAdd : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var event = Event()

        var nameEditText: EditText
        var loansTextView: TextView
        var loansButton: Button
        var placeTextView: TextView
        var placeButton: Button
        var startDateTextView: TextView
        var startDateButton: Button
        var endDateTextView: TextView
        var endDateButton: Button
        var startHourTextView: TextView
        var startHourButton: Button
        var endHourTextView: TextView
        var endHourButton: Button
        var teamTextView: TextView
        var teamButton: Button

        var back: Button
        var submit: Button

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("loaning")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                runOnUiThread {

                    Toast.makeText(this@EventAdd, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                var loans = mapper.readValue(response.body!!.string(), Array<Loaning>::class.java)

                requestService.requestBuilderGet("team")
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {

                        runOnUiThread {

                            Toast.makeText(this@EventAdd, "Conversation with server fail", Toast.LENGTH_LONG).show()
                        }
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                        var teams = mapper.readValue(response.body!!.string(), Array<Team>::class.java)

                        runOnUiThread {

                            setContentView(R.layout.event_add)

                            nameEditText = findViewById(R.id.name)

                            loansTextView = findViewById(R.id.loansTextView)
                            loansButton = findViewById(R.id.loansButton)

                            placeTextView = findViewById(R.id.placeTextView)
                            placeButton = findViewById(R.id.placeButton)

                            startDateTextView = findViewById(R.id.startDateTextView)
                            startDateButton = findViewById(R.id.startDateButton)

                            endDateTextView = findViewById(R.id.endDateTextView)
                            endDateButton = findViewById(R.id.endDateButton)

                            startHourTextView = findViewById(R.id.startHourTextView)
                            startHourButton = findViewById(R.id.startHourButton)

                            endHourTextView = findViewById(R.id.endHourTextView)
                            endHourButton = findViewById(R.id.endHourButton)

                            teamTextView = findViewById(R.id.teamTextView)
                            teamButton = findViewById(R.id.teamButton)

                            back = findViewById(R.id.back)
                            submit = findViewById(R.id.submit)

                            var selectedList = ArrayList<Int>()
                            var items = Array<String>(loans.size, { i -> loans[i].user.surname + " / " + loans[i].equipment.name})

                            loansButton.setOnClickListener(View.OnClickListener {

                                val builder = AlertDialog.Builder(this@EventAdd)
                                selectedList = ArrayList<Int>()

                                builder.setTitle("Loaning List")
                                builder.setMultiChoiceItems(items, null
                                ) { dialog, which, isChecked ->
                                    if (isChecked) {
                                        selectedList.add(which)
                                        loansTextView.text = "Loans = " + selectedList.size.toString()

                                    } else if (selectedList.contains(which)) {
                                        selectedList.remove(Integer.valueOf(which))
                                    }
                                }

                                builder.show()
                            })

                            startDateButton.setOnClickListener(View.OnClickListener {

                                val cal = Calendar.getInstance()
                                val year = cal.get(Calendar.YEAR)
                                val month: Int = cal.get(Calendar.MONTH)
                                val day = cal.get(Calendar.DAY_OF_MONTH)

                                var datePickerDialog = DatePickerDialog(this@EventAdd, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                                    var MonthOfYear = monthOfYear + 1
                                    startDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                                    event.startDate = Date("$MonthOfYear/$dayOfMonth/$year")

                                }, year, month, day)

                                datePickerDialog.show()
                            })

                            endDateButton.setOnClickListener(View.OnClickListener {

                                val cal = Calendar.getInstance()
                                val year = cal.get(Calendar.YEAR)
                                val month: Int = cal.get(Calendar.MONTH)
                                val day = cal.get(Calendar.DAY_OF_MONTH)

                                var datePickerDialog = DatePickerDialog(this@EventAdd, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                                    var MonthOfYear = monthOfYear + 1
                                    endDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                                    event.endDate = Date("$MonthOfYear/$dayOfMonth/$year")

                                }, year, month, day)

                                datePickerDialog.show()
                            })

                            startHourButton.setOnClickListener(View.OnClickListener {

                                val cal = Calendar.getInstance()
                                val timeSetListener = TimePickerDialog.OnTimeSetListener {

                                    timePicker, hour, minute ->

                                    cal.set(Calendar.HOUR_OF_DAY, hour)
                                    cal.set(Calendar.MINUTE, minute)
                                    startHourTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
                                    event.startHour = Time(cal.time.time)
                                }
                                TimePickerDialog(this@EventAdd, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                            })

                            endHourButton.setOnClickListener(View.OnClickListener {

                                val cal = Calendar.getInstance()
                                val timeSetListener = TimePickerDialog.OnTimeSetListener {

                                    timePicker, hour, minute ->

                                    cal.set(Calendar.HOUR_OF_DAY, hour)
                                    cal.set(Calendar.MINUTE, minute)
                                    endHourTextView.text = SimpleDateFormat("HH:mm").format(cal.time)
                                    event.endHour = Time(cal.time.time)
                                }
                                TimePickerDialog(this@EventAdd, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                            })

                            placeButton.setOnClickListener(View.OnClickListener {

                                val placeList = arrayOf("BDJ", "Cafet",
                                                        "I01", "I02", "I03",
                                                        "I11", "I12A", "I12B", "I13", "I14", "I15", "I16", "I17", "I18",
                                                        "I21", "I22", "I23", "I24", "I25",
                                                        "E01", "E02", "E03", "E04", "E05", "E06", "E07", "E08", "E09",
                                                        "E0S")

                                val builder = AlertDialog.Builder(this@EventAdd)
                                with(builder)
                                {
                                    setTitle("List of Items")
                                    setItems(placeList) { dialog, which ->

                                        placeTextView.text = placeList[which]
                                    }

                                    show()
                                }
                            })

                            var teamList = Array<String>(teams.size, { i -> teams[i].name + " / " + teams[i].users.size})
                            teamButton.setOnClickListener(View.OnClickListener {

                                val builder = AlertDialog.Builder(this@EventAdd)
                                with(builder)
                                {
                                    setTitle("List of Items")
                                    setItems(teamList) { dialog, which ->
                                        teamTextView.text = teamList[which]

                                        event.team = teams[which]
                                    }

                                    show()
                                }
                            })

                            back.setOnClickListener(View.OnClickListener {

                                rooterService.changeActivity(Intent(context, EventView::class.java), context, intentUser)
                            })

                            submit.setOnClickListener(View.OnClickListener {

                                event.name = nameEditText.text.toString()
                                var loansArray = IntArray(selectedList.size, { i -> loans[i].idLoaning })

                                var i = 0
                                for (cursor in selectedList) {

                                    loansArray[i] = loans[cursor].idLoaning
                                    i++
                                }
                                event.loans = loansArray
                                event.place = placeTextView.text.toString()


                                var stateCheck: String = event.allStatesCheck()
                                if (stateCheck == "") {

                                    requestService.requestBuilderPut("event", event.toStringWithoutId())
                                    .enqueue(object : Callback {

                                        override fun onFailure(call: Call, e: IOException) {

                                            runOnUiThread {

                                                Toast.makeText(this@EventAdd, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                            }
                                        }

                                        @Throws(IOException::class)
                                        override fun onResponse(call: Call, response: Response) {

                                            rooterService.changeActivity(Intent(context, EventView::class.java), context, intentUser)
                                        }
                                    })

                                } else {

                                    Toast.makeText(this@EventAdd, stateCheck, Toast.LENGTH_LONG).show()
                                }
                            })
                        }
                    }
                })
            }
        })
    }
}