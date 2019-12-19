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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class EquipmentInheritedDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var back: Button
        var fullInfo: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentEquipment = intent.getIntExtra("idEquipment", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        when (intentType) {

            "boardGame" -> boardGameType(requestService, rooterService, mapper, context, intentUser)

            "videoGame" -> videoGameType(requestService, rooterService, mapper, context, intentUser)

            "gameConsole" -> gameConsoleType(requestService, rooterService, mapper, context, intentUser)

            "hardware" -> hardwareType(requestService, rooterService, mapper, context, intentUser)

            else -> otherType(requestService, rooterService, mapper, context, intentUser)
        }

        back = findViewById(id.back)
        fullInfo = findViewById(id.otherInfo)

        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentInheritedView::class.java), context, intentUser, intentType, "type")
        })

        fullInfo.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentDetail::class.java), context, intentUser, intentEquipment, "idEquipment", "BoardGame", "context")
        })
    }

    private fun BoardGameType() {

        var boardGame: BoardGame

        var nameTextView: TextView
        var typeTextView: TextView
        var nbMaxPlayerTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        requestService.requestBuilderGet("boardGame", intentBoardGame)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentInheritedDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                boardGame = mapper.readValue(response.body!!.string(), BoardGame::class.java)

                runOnUiThread {

                    setContentView(R.layout.board_game_details)

                    nameTextView = findViewById(id.name)
                    typeTextView = findViewById(id.type)
                    nbMaxPlayerTextView = findViewById(id.nbMaxPlayer)
                    realiseDateTextView = findViewById(id.realiseDate)
                    editorTextView = findViewById(id.editor)

                    nameTextView.text = boardGame.name
                    typeTextView.text = boardGame.type
                    nbMaxPlayerTextView.text = boardGame.nbMaxPlayer.toString()

                    var date = boardGame.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorTextView.text = boardGame.editor
                }
            }
        })
    }

    private fun GameConsoleType() {


    }

    private fun VideoGameType() {


    }

    private fun HardwareType() {


    }

    private fun OtherType() {


    }
}