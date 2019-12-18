package kotlinClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.BoardGame
import com.example.bdjcrusadeinternalappli.EquipmentDetail
import com.example.bdjcrusadeinternalappli.R
import com.example.bdjcrusadeinternalappli.R.id
import com.example.bdjcrusadeinternalappli.User
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class BoardGameDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var user: User
        var boardGame: BoardGame

        var nameTextView: TextView
        var typeTextView: TextView
        var nbMaxPlayerTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        var back: Button
        var fullInfo: Button

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentBoardGame = intent.getIntExtra("idEquipment", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("boardGame", intentBoardGame)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@BoardGameDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
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

                    back = findViewById(id.back)
                    fullInfo = findViewById(id.otherInfo)

                    nameTextView.text = boardGame.name
                    typeTextView.text = boardGame.type
                    nbMaxPlayerTextView.text = boardGame.nbMaxPlayer.toString()

                    var date = boardGame.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorTextView.text = boardGame.editor

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, BoardGameView::class.java), context, intentUser)
                    })

                    fullInfo.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentDetail::class.java), context, intentUser, boardGame.idEquipment, "idEquipment", "BoardGame", "context")
                    })
                }
            }
        })
    }
}