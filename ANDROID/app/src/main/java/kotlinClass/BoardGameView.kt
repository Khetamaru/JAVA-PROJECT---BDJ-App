package kotlinClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import java.io.IOException

class BoardGameView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var listView : ListView
        var arrayList : ArrayList<BoardGame> = ArrayList()
        var listAdapter : ListAdapter
        var boardGames : Array<BoardGame>

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var back: Button

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("boardGame")
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@BoardGameView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                boardGames = mapper.readValue(response.body!!.string(), Array<BoardGame>::class.java)

                arrayList.addAll(boardGames)

                runOnUiThread {

                    setContentView(R.layout.board_game_view)

                    listView = findViewById<View>(id.boardGameListView) as ListView
                    listView.adapter = BoardGames_adapter(context, arrayList)

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentSelectTypeMenu::class.java), context, intentUser)
                    })

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val boardGame = listView.getItemAtPosition(position) as BoardGame
                        rooterService.changeActivity(Intent(v.context, BoardGameDetail::class.java), context, intentUser, boardGame.idEquipment, "idEquipment")
                    }
                }
            }
        })
    }
}