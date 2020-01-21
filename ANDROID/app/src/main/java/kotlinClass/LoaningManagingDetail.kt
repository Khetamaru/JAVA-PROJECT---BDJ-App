package kotlinClass

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.bdjcrusadeinternalappli.Loaning
import com.example.bdjcrusadeinternalappli.R
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoaningManagingDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userName: TextView
        var equipmentName: TextView
        var startDate: TextView
        var endDate: TextView
        var validation: TextView

        var back: Button
        var delete: Button
        var valid: Button
        var refuse: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentLoaning = intent.getIntExtra("idLoaningManaging", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var loaning: Loaning

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("loaning", intentLoaning)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@LoaningManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                loaning = mapper.readValue(response.body!!.string(), Loaning::class.java)

                runOnUiThread {

                    setContentView(R.layout.loaning_managing_details)

                    userName = findViewById(R.id.textUserName)
                    equipmentName = findViewById(R.id.textEquipmentName)
                    startDate = findViewById(R.id.startDate)
                    endDate = findViewById(R.id.endDate)
                    validation = findViewById(R.id.validation)

                    back = findViewById(R.id.back)
                    delete = findViewById(R.id.delete)
                    valid = findViewById(R.id.valid)
                    refuse = findViewById(R.id.refuse)

                    userName.text = loaning.user.surname
                    equipmentName.text = loaning.equipment.name

                    var date = loaning.startDate.toString().split(" ")
                    startDate.text = date[2] + " " + date[1] + " " + date[5]

                    date = loaning.endDate.toString().split(" ")
                    endDate.text = date[2] + " " + date[1] + " " + date[5]

                    validation.text = loaning.validation

                    when (loaning.validation) {

                        "Refused" -> validation.setTextColor(Color.parseColor("#5b1502"))
                        "In Progress" -> validation.setTextColor(Color.parseColor("#02265b"))
                        "Valid" -> validation.setTextColor(Color.parseColor("#085b02"))
                    }

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, LoaningManagingView::class.java), context, intentUser)
                    })

                    delete.setOnClickListener(View.OnClickListener {

                        requestService.requestBuilderDelete("loaning", intentLoaning)
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@LoaningManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                if(response.code == 200) {

                                    runOnUiThread {

                                        Toast.makeText(this@LoaningManagingDetail, "Deleted", Toast.LENGTH_LONG).show()
                                        rooterService.changeActivity(Intent(context, LoaningManagingView::class.java), context, intentUser)
                                    }
                                }
                            }
                        })
                    })

                    valid.setOnClickListener(View.OnClickListener {

                        loaning.validation = "Valid"

                        requestService.requestBuilderPut("loaning", loaning.toString())
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@LoaningManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                if(response.code == 200) {

                                    runOnUiThread {

                                        Toast.makeText(this@LoaningManagingDetail, "Validated", Toast.LENGTH_LONG).show()
                                        rooterService.changeActivity(Intent(context, LoaningManagingView::class.java), context, intentUser)
                                    }
                                }
                            }
                        })
                    })

                    refuse.setOnClickListener(View.OnClickListener {

                        loaning.validation = "Refused"

                        requestService.requestBuilderPut("loaning", loaning.toString())
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@LoaningManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                if(response.code == 200) {

                                    runOnUiThread {

                                        Toast.makeText(this@LoaningManagingDetail, "Refused", Toast.LENGTH_LONG).show()
                                        rooterService.changeActivity(Intent(context, LoaningManagingView::class.java), context, intentUser)
                                    }
                                }
                            }
                        })
                    })
                }
            }
        })
    }
}