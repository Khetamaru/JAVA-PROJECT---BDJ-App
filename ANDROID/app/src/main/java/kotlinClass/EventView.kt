package kotlinClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import java.io.IOException

class EventView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        var events: Array<Event>
        var arrayList: ArrayList<Event> = ArrayList()
        var listView : ListView

        var addEvent: Button
        var back: Button

        requestService.requestBuilderGet("event")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EventView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                events = mapper.readValue(response.body!!.string(), Array<Event>::class.java)

                arrayList.addAll(events)

                runOnUiThread {

                    setContentView(R.layout.event_view)

                    listView = findViewById<View>(id.eventListView) as ListView
                    listView.adapter = Event_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val event = listView.getItemAtPosition(position) as Event
                        rooterService.changeActivity(Intent(v.context, EventDetail::class.java), context, intentUser, event.idEvent, "idEvent")
                    }

                    addEvent = findViewById(id.addEvent)
                    addEvent.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EventAdd::class.java), context, intentUser)
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EventMenu::class.java), context, intentUser)
                    })
                }
            }
        })
    }
}