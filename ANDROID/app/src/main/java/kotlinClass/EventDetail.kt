package kotlinClass

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.bdjcrusadeinternalappli.Event
import com.example.bdjcrusadeinternalappli.Loaning
import com.example.bdjcrusadeinternalappli.R
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class EventDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEvent = intent.getIntExtra("idEvent", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        var event: Event

        var nameTextView: TextView
        var loansButton: Button
        var placeTextView: TextView
        var startDateTextView: TextView
        var endDateTextView: TextView
        var startHourTextView: TextView
        var endHourTextView: TextView
        var teamTextView: TextView

        var back: Button

        requestService.requestBuilderGet("event", intentEvent)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EventDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                event = mapper.readValue(response.body!!.string(), Event::class.java)

                requestService.requestBuilderGet("loaning")
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(this@EventDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                        var loans = mapper.readValue(response.body!!.string(), Array<Loaning>::class.java)

                        runOnUiThread {

                            setContentView(R.layout.event_details)

                            nameTextView = findViewById(id.name)
                            loansButton = findViewById(id.loansButton)
                            placeTextView = findViewById(id.place)
                            startDateTextView = findViewById(id.startDate)
                            endDateTextView = findViewById(id.endDate)
                            startHourTextView = findViewById(id.startHour)
                            endHourTextView = findViewById(id.endHour)
                            teamTextView = findViewById(id.team)

                            nameTextView.setText(event.name)

                            var items = Array<String>(loans.size, { i -> loans[i].user.surname})
                            var i = 0

                            for (loanId in event.loans) {

                                for (loan in loans) {

                                    if (loanId == loan.idLoaning) {

                                        items.set(i, loans[i].user.surname + " / " + loans[i].equipment.name)
                                    }
                                }

                                i++
                            }

                            loansButton.setOnClickListener(View.OnClickListener {


                                val builder = AlertDialog.Builder(this@EventDetail)
                                with(builder)
                                {
                                    setTitle("List of Loans")
                                    setItems(items) { dialog, which ->
                                        Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
                                    }

                                    show()
                                }
                            })

                            placeTextView.text = event.place

                            val startDate = event.getStartDate().toString().split(" ")
                            val endDate = event.getEndDate().toString().split(" ")
                            val startTime = event.getStartHour().toString().split(":")
                            val endTime = event.getEndHour().toString().split(":")

                            startDateTextView.text = startDate[2] + " " + startDate[1] + " " + startDate[5]
                            endDateTextView.text = endDate[2] + " " + endDate[1] + " " + endDate[5]
                            startHourTextView.text = startTime[0] + ":" + startTime[1]
                            endHourTextView.text = endTime[0] + ":" + endTime[1]

                            teamTextView.text = event.team.name

                            back = findViewById(id.back)

                            back.setOnClickListener(View.OnClickListener {

                                rooterService.changeActivity(Intent(context, EventView::class.java), context, intentUser)
                            })
                        }
                    }
                })
            }
        })
    }
}