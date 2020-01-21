package kotlinClass

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class TeamDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentTeam = intent.getIntExtra("idTeam", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        var team: Team

        var nameTextView: TextView
        var usersButton: Button

        var back: Button

        requestService.requestBuilderGet("team", intentTeam)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@TeamDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                team = mapper.readValue(response.body!!.string(), Team::class.java)

                requestService.requestBuilderGet("user")
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(this@TeamDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                        var users = mapper.readValue(response.body!!.string(), Array<User>::class.java)

                        runOnUiThread {

                            setContentView(R.layout.team_details)

                            nameTextView = findViewById(id.name)
                            usersButton = findViewById(id.usersButton)

                            nameTextView.setText(team.name)

                            var items = Array<String>(team.users.size, { i -> (i * i).toString() })
                            var i = 0

                            for (userId in team.users) {

                                for (user in users) {

                                    if (userId == user.idUser) {

                                        items.set(i, user.surname)
                                    }
                                }

                                i++
                            }

                            usersButton.setOnClickListener(View.OnClickListener {


                                val builder = AlertDialog.Builder(this@TeamDetail)
                                with(builder)
                                {
                                    setTitle("List of Users")
                                    setItems(items) { dialog, which ->
                                        Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
                                    }

                                    show()
                                }
                            })

                            back = findViewById(id.back)

                            back.setOnClickListener(View.OnClickListener {

                                rooterService.changeActivity(Intent(context, TeamView::class.java), context, intentUser)
                            })
                        }
                    }
                })
            }
        })
    }
}