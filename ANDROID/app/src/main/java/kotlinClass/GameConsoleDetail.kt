package kotlinClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.LocationView
import com.example.bdjcrusadeinternalappli.R
import com.example.bdjcrusadeinternalappli.R.id
import com.example.bdjcrusadeinternalappli.User
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.util.logging.Level

class GameConsoleDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentClient = intent.getIntExtra("idUserManaging", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        requestService.requestBuilderGet("user", intentClient)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@GameConsoleDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                user = mapper.readValue(response.body!!.string(), User::class.java)

                runOnUiThread {

                    when(user.level) {
                        "admin" -> adminView(user)

                        else -> noneAdminView(user, context, intentUser, intentClient)
                    }

                    var back : Button = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, UserManagingView::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    fun adminView(user : User) {

        setContentView(R.layout.user_managing_admin)

        var surnameView : TextView = findViewById(id.surnameView)
        surnameView.setText(user.getSurname())

        var loginView : TextView = findViewById(id.loginView)
        loginView.setText(user.getLogin())

        var mailView : TextView = findViewById(id.mailView)
        mailView.setText(user.getMail())

        var levelView : TextView = findViewById(id.levelView)
        levelView.setText(user.getLevel())
    }

    fun noneAdminView(user : User, context: Context, intentUser: Int, intentClient: Int) {

        setContentView(R.layout.user_managing_none_admin)

        var requestService = RequestService()
        var rooterService = RooterService()

        var deleteButton : Button = findViewById(id.delete)
        deleteButton.setOnClickListener(View.OnClickListener {


            requestService.requestBuilderDelete("user", user.idUser)
                    .enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(this@GameConsoleDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {

                    rooterService.changeActivity(Intent(context, UserManagingView::class.java), context, intentUser)
                }
            })
        })

        var saveButton : Button = findViewById(id.save)
        saveButton.setOnClickListener(View.OnClickListener {

            requestService.requestBuilderPut("user", user.toString())
                    .enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(this@GameConsoleDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {

                    rooterService.changeActivity(Intent(context, GameConsoleDetail::class.java), context, intentUser, intentClient, "idUserManaging")
                }
            })
        })

        var surnameView : TextView = findViewById(id.surnameView)
        surnameView.setText(user.getSurname())

        var loginView : TextView = findViewById(id.loginView)
        loginView.setText(user.getLogin())

        var mailView : TextView = findViewById(id.mailView)
        mailView.setText(user.getMail())

        val levels = arrayOf("student", "cotisant", "bdjMember", "admin")

        var levelSpinner : Spinner = findViewById(id.levelView)

        if (levelSpinner != null) {

            val arrayAdapter = ArrayAdapter(this@GameConsoleDetail, android.R.layout.simple_spinner_item, levels)
            levelSpinner.adapter = arrayAdapter

            levels.forEachIndexed { index, level ->

                if (user.level.equals(level)) levelSpinner.setSelection(index)
            }

            levelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    user.level = levels[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}