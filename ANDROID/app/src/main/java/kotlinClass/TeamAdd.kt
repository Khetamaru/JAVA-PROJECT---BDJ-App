package kotlinClass

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.Equipment
import com.example.bdjcrusadeinternalappli.R
import com.example.bdjcrusadeinternalappli.Team
import com.example.bdjcrusadeinternalappli.User
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class TeamAdd : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var team = Team()

        var nameEditText: EditText
        var usersTextView: TextView
        var usersButton: Button

        var back: Button
        var submit: Button

        requestService.requestBuilderGet("user")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                runOnUiThread {

                    Toast.makeText(this@TeamAdd, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                var users = mapper.readValue(response.body!!.string(), Array<User>::class.java)

                runOnUiThread {

                    setContentView(R.layout.team_add)

                    nameEditText = findViewById(R.id.name)
                    usersTextView = findViewById(R.id.users)
                    usersButton = findViewById(R.id.usersButton)

                    back = findViewById(R.id.back)
                    submit = findViewById(R.id.submit)

                    var selectedList = ArrayList<Int>()
                    var items = Array<String>(users.size, { i -> users[i].surname })

                    usersButton.setOnClickListener(View.OnClickListener {

                        val builder = AlertDialog.Builder(this@TeamAdd)
                        selectedList = ArrayList<Int>()

                        builder.setTitle("User List")
                        builder.setMultiChoiceItems(items, null
                        ) { dialog, which, isChecked ->
                            if (isChecked) {
                                selectedList.add(which)
                                usersTextView.text = "Users = " + selectedList.size.toString()

                            } else if (selectedList.contains(which)) {
                                selectedList.remove(Integer.valueOf(which))
                            }
                        }

                        builder.show()
                    })

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, TeamView::class.java), context, intentUser)
                    })

                    submit.setOnClickListener(View.OnClickListener {

                        team.name = nameEditText.text.toString()
                        var usersArray = IntArray(selectedList.size, { i -> users[i].idUser })

                        var i = 0
                        for (cursor in selectedList) {

                            usersArray[i] = users[cursor].idUser
                            i++
                        }
                        team.users = usersArray

                        var stateCheck: String = team.allStatesCheck()
                        if (stateCheck == "") {

                            requestService.requestBuilderPut("team", team.toStringWithoutId())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@TeamAdd, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    rooterService.changeActivity(Intent(context, TeamView::class.java), context, intentUser)
                                }
                            })

                        } else {

                            Toast.makeText(this@TeamAdd, stateCheck, Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        })
    }
}