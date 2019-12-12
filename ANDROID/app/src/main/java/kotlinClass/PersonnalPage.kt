package kotlinClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.MainPage
import com.example.bdjcrusadeinternalappli.R
import com.example.bdjcrusadeinternalappli.R.id
import com.example.bdjcrusadeinternalappli.User
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

class PersonnalPage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User

        var back : Button
        var changePassword : Button

        val context : Context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper = ObjectMapper()
        val requestService = RequestService()
        var rooterService = RooterService()

        requestService.requestBuilderGet("user", intentUser)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@PersonnalPage, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                user = mapper.readValue(response.body!!.string(), User::class.java)

                runOnUiThread {

                    when(user.level) {
                        "admin" -> adminView(user, context)

                        else -> simpleUserView(user, context)
                    }

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, MainPage::class.java), context)
                    })

                    changePassword = findViewById(id.changePassword)
                    changePassword.setOnClickListener (View.OnClickListener {

                        rooterService.changeActivity(Intent(context, ChangePasswordPage::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    fun simpleUserView(user : User, context: Context) {

        setContentView(R.layout.personnal_page)

        var surnameView : EditText = findViewById(id.surnameView)
        surnameView.setText(user.getSurname())

        var loginView : EditText = findViewById(id.loginView)
        loginView.setText(user.getLogin())

        var mailView : EditText = findViewById(id.mailView)
        mailView.setText(user.getMail())

        var levelView : TextView = findViewById(id.levelView)
        levelView.setText(user.getLevel())

        var saveButton : Button = findViewById(id.save)
        saveButton.setOnClickListener(View.OnClickListener {

            user.login = loginView.text.toString()
            user.mail = mailView.text.toString()
            user.surname = surnameView.text.toString()

            if (verifSurname(surnameView)) {

                if (verificationMail(mailView)) {

                    if (verificationLogin(loginView)) {

                        val requestService : RequestService = RequestService()

                        requestService.requestBuilderPut("user", user.toString())
                                .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(this@PersonnalPage, "Conversation with server fail", Toast.LENGTH_LONG).show()
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                val rooterService: RooterService = RooterService()
                                rooterService.changeActivity(Intent(context, PersonnalPage::class.java), context)
                            }
                        })
                    }
                }
            }
        })
    }

    fun adminView(user : User, context: Context) {

        setContentView(R.layout.personnal_page_admin)

        var surnameView : EditText = findViewById(id.surnameView)
        surnameView.setText(user.getSurname())

        var loginView : EditText = findViewById(id.loginView)
        loginView.setText(user.getLogin())

        var mailView : EditText = findViewById(id.mailView)
        mailView.setText(user.getMail())

        val levels = arrayOf("student", "cotisant", "bdjMember", "admin")

        var levelSpinner : Spinner = findViewById(id.levelView)

        if (levelSpinner != null) {

            val arrayAdapter = ArrayAdapter(this@PersonnalPage, android.R.layout.simple_spinner_item, levels)
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

        var saveButton : Button = findViewById(id.save)
        saveButton.setOnClickListener(View.OnClickListener {

            user.login = loginView.text.toString()
            user.mail = mailView.text.toString()
            user.surname = surnameView.text.toString()

            if (verifSurname(surnameView)) {

                if (verificationMail(mailView)) {

                    if (verificationLogin(loginView)) {

                        val requestService = RequestService()

                        requestService.requestBuilderPut("user", user.toString())
                                .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(this@PersonnalPage, "Conversation with server fail", Toast.LENGTH_LONG).show()
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                val rooterService = RooterService()
                                rooterService.changeActivity(Intent(context, PersonnalPage::class.java), context)
                            }
                        })
                    }
                }
            }
        })
    }


    fun verifSurname(editText_surname : EditText): Boolean {
        if (editText_surname.length() > 3) {

            if (editText_surname.length() < 15) {

                return true
            }
            else {

                Toast.makeText(this@PersonnalPage, "Surname to long", Toast.LENGTH_LONG).show()
            }
        }
        else {

            Toast.makeText(this@PersonnalPage, "Surname to short", Toast.LENGTH_LONG).show()
        }
        return false
    }

    fun verificationMail(editText_mail : EditText): Boolean {

        if (editText_mail.text.toString() != "") {

            return true
        }
        else {

            Toast.makeText(this@PersonnalPage, "You have to write a mail address", Toast.LENGTH_LONG).show()
            return false
        }
    }

    fun verificationLogin(editText_login : EditText) : Boolean {
        if (editText_login.length() > 3) {

            if (editText_login.length() < 15) {

                return true
            }
            else {

                Toast.makeText(this@PersonnalPage, "Login to long (MAX 14)", Toast.LENGTH_LONG).show()
            }
        } else {

            Toast.makeText(this@PersonnalPage, "Login to short (MIN 4)", Toast.LENGTH_LONG).show()
        }

        return false
    }
}