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
import okhttp3.*
import java.io.IOException

class EquipmentManagingView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        setContentView(R.layout.loading_page)

        when (intentType) {

            "boardGame" -> boardGameType(requestService, rooterService, mapper, context, intentUser, intentType)

            "videoGame" -> videoGameType(requestService, rooterService, mapper, context, intentUser, intentType)

            "gameConsole" -> gameConsoleType(requestService, rooterService, mapper, context, intentUser, intentType)

            "hardware" -> hardwareType(requestService, rooterService, mapper, context, intentUser, intentType)

            else -> otherType(requestService, rooterService, mapper, context, intentUser, intentType)
        }
    }

    private fun boardGameType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intentUser: Int, intentType: String) {

        var boardGames: Array<BoardGame>
        var arrayList: ArrayList<BoardGame> = ArrayList()
        var listView : ListView

        var addEquipment: Button
        var back: Button

        requestService.requestBuilderGet("boardGame")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                boardGames = mapper.readValue(response.body!!.string(), Array<BoardGame>::class.java)

                arrayList.addAll(boardGames)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_view)

                    listView = findViewById<View>(id.equipmentInheritedListView) as ListView
                    listView.adapter = BoardGames_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val boardGame = listView.getItemAtPosition(position) as BoardGame
                        rooterService.changeActivity(Intent(v.context, EquipmentManagingDetail::class.java), context, intentUser, boardGame.idEquipment, "idEquipment", intentType, "type")
                    }

                    addEquipment = findViewById(id.addEquipment)
                    addEquipment.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingSelect::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    private fun videoGameType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intentUser: Int, intentType: String) {

        var videoGames: Array<VideoGame>
        var arrayList: ArrayList<VideoGame> = ArrayList()
        var listView : ListView

        var addEquipment: Button
        var back: Button

        requestService.requestBuilderGet("videoGame")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                videoGames = mapper.readValue(response.body!!.string(), Array<VideoGame>::class.java)

                arrayList.addAll(videoGames)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_view)

                    listView = findViewById<View>(id.equipmentInheritedListView) as ListView
                    listView.adapter = VideoGames_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val videoGame = listView.getItemAtPosition(position) as VideoGame
                        rooterService.changeActivity(Intent(v.context, EquipmentManagingDetail::class.java), context, intentUser, videoGame.idEquipment, "idEquipment", intentType, "type")
                    }

                    addEquipment = findViewById(id.addEquipment)
                    addEquipment.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingSelect::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    private fun gameConsoleType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intentUser: Int, intentType: String) {

        var gameConsoles: Array<GameConsole>
        var arrayList: ArrayList<GameConsole> = ArrayList()
        var listView : ListView

        var addEquipment: Button
        var back: Button

        requestService.requestBuilderGet("gameConsole")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                gameConsoles = mapper.readValue(response.body!!.string(), Array<GameConsole>::class.java)

                arrayList.addAll(gameConsoles)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_view)

                    listView = findViewById<View>(id.equipmentInheritedListView) as ListView
                    listView.adapter = GameConsoles_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val gameConsole = listView.getItemAtPosition(position) as GameConsole
                        rooterService.changeActivity(Intent(v.context, EquipmentManagingDetail::class.java), context, intentUser, gameConsole.idEquipment, "idEquipment", intentType, "type")
                    }

                    addEquipment = findViewById(id.addEquipment)
                    addEquipment.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingSelect::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    private fun hardwareType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intentUser: Int, intentType: String) {

        var hardwares: Array<Hardware>
        var arrayList: ArrayList<Hardware> = ArrayList()
        var listView : ListView

        var addEquipment: Button
        var back: Button

        requestService.requestBuilderGet("hardware")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {

                runOnUiThread {

                    Toast.makeText(this@EquipmentManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                hardwares = mapper.readValue(response.body!!.string(), Array<Hardware>::class.java)

                arrayList.addAll(hardwares)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_view)

                    listView = findViewById<View>(id.equipmentInheritedListView) as ListView
                    listView.adapter = Hardwares_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val hardware = listView.getItemAtPosition(position) as Hardware
                        rooterService.changeActivity(Intent(v.context, EquipmentManagingDetail::class.java), context, intentUser, hardware.idEquipment, "idEquipment", intentType, "type")
                    }

                    addEquipment = findViewById(id.addEquipment)
                    addEquipment.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingSelect::class.java), context, intentUser)
                    })
                }
            }
        })
    }

    private fun otherType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intentUser: Int, intentType: String) {

        var others: Array<Other>
        var arrayList: ArrayList<Other> = ArrayList()
        var listView : ListView

        var addEquipment: Button
        var back: Button

        requestService.requestBuilderGet("other")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                others = mapper.readValue(response.body!!.string(), Array<Other>::class.java)

                arrayList.addAll(others)

                runOnUiThread {

                    setContentView(R.layout.equipment_managing_view)

                    listView = findViewById<View>(id.equipmentInheritedListView) as ListView
                    listView.adapter = Others_adapter(context, arrayList)

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val other = listView.getItemAtPosition(position) as Other
                        rooterService.changeActivity(Intent(v.context, EquipmentManagingDetail::class.java), context, intentUser, other.idEquipment, "idEquipment", intentType, "type")
                    }

                    addEquipment = findViewById(id.addEquipment)
                    addEquipment.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingSelect::class.java), context, intentUser)
                    })
                }
            }
        })
    }
}