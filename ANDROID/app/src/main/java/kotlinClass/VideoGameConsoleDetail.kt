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
import kotlinx.android.synthetic.main.select_equipment_type_view.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class VideoGameConsoleDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var videoGame: VideoGame
        var gameConsole: GameConsole

        var nameTextView: TextView
        var nbMaxControllerTextView: TextView
        var videoCableTextView: TextView
        var powerCableTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        var back: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEquipment = intent.getIntExtra("idEquipment", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("equipment", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@VideoGameConsoleDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                videoGame = mapper.readValue(response.body!!.string(), VideoGame::class.java)
                gameConsole = videoGame.gameConsole

                runOnUiThread {

                    setContentView(R.layout.video_game_console_details)

                    nameTextView = findViewById(id.name)
                    nbMaxControllerTextView = findViewById(id.nbMaxController)
                    videoCableTextView = findViewById(id.videoCable)
                    powerCableTextView = findViewById(id.powerCable)
                    realiseDateTextView = findViewById(id.realiseDate)
                    editorTextView = findViewById(id.editor)

                    back = findViewById(id.back)

                    nameTextView.text = gameConsole.name
                    nbMaxControllerTextView.text = gameConsole.nbMaxController.toString()
                    videoCableTextView.text = gameConsole.videoCable
                    powerCableTextView.text = gameConsole.powerCable

                    var date = gameConsole.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorTextView.text = gameConsole.editor

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentInheritedDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })
                }
            }
        })
    }
}
