package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.*
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.change_password_page.*
import okhttp3.*
import java.io.IOException
import java.util.*
import okhttp3.MediaType.Companion.toMediaType

class ChangePasswordPage : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User

        var oldPasswordEditText: EditText
        var newPasswordEditText: EditText
        var confirmationPasswordEditText: EditText

        val context = this
        var intent = intent

        var mapper: ObjectMapper = ObjectMapper()
        val client: OkHttpClient = OkHttpClient()

        val intentUser = intent.getIntExtra("idUser", 0)

        var validation: Button
        var back: Button

        setContentView(R.layout.loading_page)

        val request: Request = Request.Builder()
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

                    setContentView(R.layout.change_password_page)

                    oldPasswordEditText = findViewById(id.oldPassword)
                    newPasswordEditText = findViewById(id.newPassword)
                    confirmationPasswordEditText = findViewById(id.secondNewPassword)

                    back = findViewById(id.back)
                    validation = findViewById(id.validation)

                    back.setOnClickListener(View.OnClickListener {

                        val intent = Intent(context, PersonnalPage::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        startActivity(intent)
                    })

                    validation.setOnClickListener(View.OnClickListener {

                        if (oldPassword.text.toString() == user.password) {

                            if (newPasswordEditText.text.toString() != "") {

                                if (newPasswordEditText.length() > 3) {

                                    if (newPasswordEditText.length() < 15) {

                                        if (confirmationPasswordEditText.text.toString() == newPasswordEditText.text.toString()) {

                                            user.password = newPasswordEditText.text.toString()

                                            mapper = ObjectMapper()

                                            val client = OkHttpClient()

                                            val rq = user.toString()

                                            val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), rq)

                                            val request = Request.Builder()
                                                    .url("http://192.168.43.110:8080/user")
                                                    .put(body)
                                                    .build()

                                            client.newCall(request).enqueue(object : Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    Log.e("LoaningAddNext", "fail", e)
                                                }

                                                @Throws(IOException::class)
                                                override fun onResponse(call: Call, response: Response) {

                                                    val intent: Intent
                                                    intent = Intent(context, PersonnalPage::class.java)
                                                    intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                                                    startActivity(intent)
                                                }
                                            })
                                        } else {

                                            Toast.makeText(this@ChangePasswordPage, "New password and confirmation password have to be the same", Toast.LENGTH_LONG).show()
                                        }
                                    } else {

                                        Toast.makeText(this@ChangePasswordPage, "New password to long", Toast.LENGTH_LONG).show()
                                    }
                                } else {

                                    Toast.makeText(this@ChangePasswordPage, "New password to short", Toast.LENGTH_LONG).show()
                                }
                            } else {

                                Toast.makeText(this@ChangePasswordPage, "You have to write a new password", Toast.LENGTH_LONG).show()
                            }
                        } else {

                            Toast.makeText(this@ChangePasswordPage, "Old password is wrong", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        })
    }
}