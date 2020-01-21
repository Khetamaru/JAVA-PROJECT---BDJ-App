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
import com.google.common.hash.Hashing
import kotlinx.android.synthetic.main.change_password_page.*
import okhttp3.*
import java.io.IOException
import java.util.*
import okhttp3.MediaType.Companion.toMediaType
import java.nio.charset.StandardCharsets

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

        val intentUser = intent.getIntExtra("idUser", 0)
        var rooterService = RooterService()
        var requestService = RequestService()

        var validation: Button
        var back: Button

        var password: String;

        setContentView(layout.loading_page)

        requestService.requestBuilderGet("user", intentUser)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                runOnUiThread {

                    Toast.makeText(this@ChangePasswordPage, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }
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

                        rooterService.changeActivity(Intent(context, PersonnalPage::class.java), context, intentUser)
                    })

                    validation.setOnClickListener(View.OnClickListener {

                        password = Hashing.sha256()
                                .hashString(oldPassword.text.toString(), StandardCharsets.UTF_8)
                                .toString()

                        if (password == user.password) {

                            if (newPasswordEditText.text.toString() != "") {

                                if (newPasswordEditText.length() > 3) {

                                    if (newPasswordEditText.length() < 15) {

                                        if (confirmationPasswordEditText.text.toString() == newPasswordEditText.text.toString()) {

                                            user.password = newPasswordEditText.text.toString()

                                            mapper = ObjectMapper()

                                            val client = OkHttpClient()

                                            requestService.requestBuilderPut("user", user.toString())
                                                    .enqueue(object : Callback {

                                                override fun onFailure(call: Call, e: IOException) {
                                                    runOnUiThread {

                                                        Toast.makeText(this@ChangePasswordPage, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                                @Throws(IOException::class)
                                                override fun onResponse(call: Call, response: Response) {

                                                    rooterService.changeActivity(Intent(context, PersonnalPage::class.java), context, intentUser)
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