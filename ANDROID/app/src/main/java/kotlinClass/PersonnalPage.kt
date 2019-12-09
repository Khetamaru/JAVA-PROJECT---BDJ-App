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

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        val client: OkHttpClient = OkHttpClient()

        val request = Request.Builder()
                .url("http://192.168.43.110:8080/user/$intentUser")
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("UserManagingDetail", "fail", e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                user = mapper.readValue(response.body!!.string(), User::class.java)

                runOnUiThread {

                    when(user.level) {
                        "admin" -> adminView(user, client, context, intentUser)

                        else -> simpleUserView(user, client, context, intentUser)
                    }

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        val intent = Intent(context, MainPage::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        startActivity(intent)
                    })

                    changePassword = findViewById(id.changePassword)
                    changePassword.setOnClickListener (View.OnClickListener {

                        val intent = Intent(context, ChangePasswordPage::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        startActivity(intent)
                    })
                }
            }
        })
    }

    fun simpleUserView(user : User, client: OkHttpClient, context: Context, intentUser: Int) {

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

                        val rq = user.toString()

                        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), rq)

                        val request = Request.Builder()
                                .url("http://192.168.43.110:8080/user")
                                .put(body)
                                .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Log.e("PersonnalPage", "fail", e)
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                val intent = Intent(context, PersonnalPage::class.java)
                                intent.putExtra("idUser", intentUser)

                                /*Toast.makeText(this@UserManagingDetail, "New Information(s) saved !", Toast.LENGTH_LONG).show()
                    Log.i("UserManagingDetail", "New Information(s) saved !")*/

                                startActivity(intent)
                            }
                        })
                    }
                }
            }
        })
    }

    fun adminView(user : User, client: OkHttpClient, context: Context, intentUser: Int) {

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

                        val rq = user.toString()

                        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), rq)

                        val request = Request.Builder()
                                .url("http://192.168.43.110:8080/user")
                                .put(body)
                                .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Log.e("PersonnalPage", "fail", e)
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                val intent = Intent(context, PersonnalPage::class.java)
                                intent.putExtra("idUser", intentUser)

                                /*Toast.makeText(this@UserManagingDetail, "New Information(s) saved !", Toast.LENGTH_LONG).show()
                    Log.i("UserManagingDetail", "New Information(s) saved !")*/

                                startActivity(intent)
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