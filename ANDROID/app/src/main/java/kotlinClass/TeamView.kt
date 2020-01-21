package kotlinClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import java.io.IOException

class TeamView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        var teams: Array<Team>
        var arrayList: ArrayList<Team> = ArrayList()
        var listView : ListView

        var addTeam: Button
        var back: Button

        requestService.requestBuilderGet("team")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@TeamView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                teams = mapper.readValue(response.body!!.string(), Array<Team>::class.java)

                arrayList.addAll(teams)

                runOnUiThread {

                    setContentView(R.layout.team_view)

                    listView = findViewById<View>(id.teamListView) as ListView
                    listView.adapter = Team_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val team = listView.getItemAtPosition(position) as Team
                        rooterService.changeActivity(Intent(v.context, TeamDetail::class.java), context, intentUser, team.idTeam, "idTeam")
                    }

                    addTeam = findViewById(id.addTeam)
                    addTeam.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, TeamAdd::class.java), context, intentUser)
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