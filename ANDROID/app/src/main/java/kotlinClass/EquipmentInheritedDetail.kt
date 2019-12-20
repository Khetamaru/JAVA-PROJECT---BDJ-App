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

            "boardGame" -> boardGameType(requestService, intentEquipment, mapper)

            "videoGame" -> videoGameType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)

            "gameConsole" -> gameConsoleType(requestService, intentEquipment, mapper)

            "hardware" -> hardwareType(requestService, intentEquipment, mapper)

            else -> otherType(requestService, intentEquipment, mapper)
        }

        back = findViewById(id.back)
        fullInfo = findViewById(id.otherInfo)

        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentInheritedView::class.java), context, intentUser, intentType, "type")
        })

        fullInfo.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "context")
        })
    }

    private fun boardGameType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper) {

        var boardGame: BoardGame

        var nameTextView: TextView
        var typeTextView: TextView
        var nbMaxPlayerTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        requestService.requestBuilderGet("boardGame", intentEquipment)
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

    private fun gameConsoleType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper) {

        var gameConsole: GameConsole

        var nameTextView: TextView
        var nbMaxControllerTextView: TextView
        var videoCableTextView: TextView
        var powerCableTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        requestService.requestBuilderGet("gameConsole", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentInheritedDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                gameConsole = mapper.readValue(response.body!!.string(), GameConsole::class.java)

                runOnUiThread {

                    setContentView(R.layout.game_console_details)

                    nameTextView = findViewById(id.name)
                    nbMaxControllerTextView = findViewById(id.nbMaxController)
                    videoCableTextView = findViewById(id.videoCable)
                    powerCableTextView = findViewById(id.powerCable)
                    realiseDateTextView = findViewById(id.realiseDate)
                    editorTextView = findViewById(id.editor)

                    nameTextView.text = gameConsole.name
                    nbMaxControllerTextView.text = gameConsole.nbMaxController.toString()
                    videoCableTextView.text = gameConsole.videoCable
                    powerCableTextView.text = gameConsole.powerCable

                    var date = gameConsole.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorTextView.text = gameConsole.editor
                }
            }
        })
    }

    private fun videoGameType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var videoGame: VideoGame

        var nameTextView: TextView
        var gameConsoleButton: Button
        var nbMaxPlayerTextView: TextView
        var lanTextView: TextView
        var realiseDateTextView: TextView
        var editorTextView: TextView

        requestService.requestBuilderGet("videoGame", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentInheritedDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                videoGame = mapper.readValue(response.body!!.string(), VideoGame::class.java)

                runOnUiThread {

                    setContentView(R.layout.video_game_details)

                    nameTextView = findViewById(id.name)
                    gameConsoleButton = findViewById(id.gameConsole)
                    nbMaxPlayerTextView = findViewById(id.nbMaxPlayer)
                    lanTextView = findViewById(id.lan)
                    realiseDateTextView = findViewById(id.realiseDate)
                    editorTextView = findViewById(id.editor)

                    nameTextView.text = videoGame.name

                    gameConsoleButton.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, VideoGameConsoleDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    nbMaxPlayerTextView.text = videoGame.nbMaxPlayer.toString()
                    lanTextView.text = videoGame.lan

                    var date = videoGame.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorTextView.text = videoGame.editor
                }
            }
        })
    }

    private fun hardwareType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper) {

        var hardware: Hardware

        var nameTextView: TextView
        var CPUTextView: TextView
        var RAMTextView: TextView
        var HDDTextView: TextView
        var GPUTextView: TextView
        var OSTextView: TextView

        requestService.requestBuilderGet("hardware", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentInheritedDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                hardware = mapper.readValue(response.body!!.string(), Hardware::class.java)

                runOnUiThread {

                    setContentView(R.layout.hardware_details)

                    nameTextView = findViewById(id.name)
                    CPUTextView = findViewById(id.CPU)
                    RAMTextView = findViewById(id.RAM)
                    HDDTextView = findViewById(id.HDD)
                    GPUTextView = findViewById(id.GPU)
                    OSTextView = findViewById(id.OS)

                    nameTextView.text = hardware.name
                    CPUTextView.text = hardware.cpu
                    RAMTextView.text = hardware.ram
                    HDDTextView.text = hardware.hdd
                    GPUTextView.text = hardware.gpu
                    OSTextView.text = hardware.os
                }
            }
        })
    }

    private fun otherType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper) {

        var other: Other

        var nameTextView: TextView
        var typeTextView: TextView

        requestService.requestBuilderGet("other", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentInheritedDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                other = mapper.readValue(response.body!!.string(), Other::class.java)

                runOnUiThread {

                    setContentView(R.layout.other_details)

                    nameTextView = findViewById(id.name)
                    typeTextView = findViewById(id.type)

                    nameTextView.text = other.name
                    typeTextView.text = other.type
                }
            }
        })
    }
}