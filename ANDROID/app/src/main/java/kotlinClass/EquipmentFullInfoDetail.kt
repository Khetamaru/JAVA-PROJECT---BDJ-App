package kotlinClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.bdjcrusadeinternalappli.*
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.select_equipment_type_view.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class EquipmentFullInfoDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var nameTextView: TextView
        var statusTextView: TextView
        var recupDateTextView: TextView
        var stateTextView: TextView
        var originTextView: TextView
        var cfdocTextView: TextView
        var ableToBorrowTextView: TextView

        var back: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEquipment = intent.getIntExtra("idEquipment", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var equipment: Equipment

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("equipment", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentFullInfoDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                equipment = switchType(intentType, response.body, mapper)

                setContentView(R.layout.equipment_details)

                nameTextView = findViewById(R.id.name)
                statusTextView = findViewById(R.id.status)
                recupDateTextView = findViewById(R.id.recupDate)
                stateTextView = findViewById(R.id.state)
                originTextView = findViewById(R.id.origin)
                cfdocTextView = findViewById(R.id.cfDoc)
                ableToBorrowTextView = findViewById(R.id.ableToBorrow)

                back = findViewById(R.id.back)

                nameTextView.text = equipment.name
                statusTextView.text = equipment.status

                var date = equipment.dateRecup.toString().split(" ")
                recupDateTextView.text = date[2] + " " + date[1] + " " + date[5]

                stateTextView.text = equipment.state
                originTextView.text = equipment.origin
                cfdocTextView.text = equipment.cfDoc
                ableToBorrowTextView.text = equipment.ableToBorrow

                back.setOnClickListener(View.OnClickListener {

                    rooterService.changeActivity(Intent(context, EquipmentInheritedDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                })
            }
        })
    }

    private fun boardGameSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), BoardGame::class.java)
        return equipment.extarct()
    }

    private fun videoGameSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), VideoGame::class.java)
        return equipment.extarct()
    }

    private fun gameConsoleSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), GameConsole::class.java)
        return equipment.extarct()
    }

    private fun hardwareSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), Hardware::class.java)
        return equipment.extarct()
    }

    private fun otherSet(body: ResponseBody?, mapper: ObjectMapper): Equipment {

        var equipment = mapper.readValue(body!!.string(), Other::class.java)
        return equipment.extarct()
    }

    fun switchType(intentType: String, body: ResponseBody?, mapper: ObjectMapper): Equipment {

        when (intentType) {

            "boardGame" -> return boardGameSet(body, mapper)

            "videoGame" -> return videoGameSet(body, mapper)

            "gameConsole" -> return gameConsoleSet(body, mapper)

            "hardware" -> return hardwareSet(body, mapper)

            else -> return otherSet(body, mapper)
        }
    }
}