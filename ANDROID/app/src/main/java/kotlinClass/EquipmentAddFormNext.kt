package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class EquipmentAddFormNext : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        var intent = intent
        val intentType = intent.getStringExtra("type")
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var back: Button

        setContentView(R.layout.loading_page)

        when (intentType) {

            "boardGame" -> boardGameType(requestService, rooterService, mapper, context, intent)

            "videoGame" -> videoGameType(requestService, rooterService, mapper, context, intent)

            "gameConsole" -> gameConsoleType(requestService, rooterService, mapper, context, intent)

            "hardware" -> hardwareType(requestService, rooterService, mapper, context, intent)

            else -> otherType(requestService, rooterService, mapper, context, intent)
        }
    }

    private fun boardGameType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intent: Intent) {

        setContentView(R.layout.board_game_form)

        var boardGame: BoardGame = BoardGame()

        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")

        boardGame.name = intent.getStringExtra("name")
        boardGame.status = intent.getStringExtra("status")
        boardGame.dateRecup = Date(intent.getLongExtra("dateRecup", 0))
        boardGame.state = intent.getStringExtra("state")
        boardGame.origin = intent.getStringExtra("origin")
        boardGame.cfDoc = intent.getStringExtra("cfDoc")
        boardGame.ableToBorrow = intent.getStringExtra("ableToBorrow")

        var typeEditText: EditText
        var nbMaxPlayerEditText: EditText
        var realiseDateTextView: TextView
        var realiseDateButton: Button
        var editorEditText: EditText

        var submit: Button
        var back: Button

        typeEditText = findViewById(R.id.type)
        nbMaxPlayerEditText = findViewById(R.id.nbMaxPlayer)
        realiseDateTextView = findViewById(R.id.realiseDate)
        realiseDateButton = findViewById(R.id.realiseDateButton)
        editorEditText = findViewById(R.id.editor)

        realiseDateButton.setOnClickListener(View.OnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog = DatePickerDialog(this@EquipmentAddFormNext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var MonthOfYear = monthOfYear + 1
                realiseDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                boardGame.realiseDate = Date("$dayOfMonth/$MonthOfYear/$year")

            }, year, month, day)

            datePickerDialog.show()
        })

        submit = findViewById(R.id.submit)
        submit.setOnClickListener(View.OnClickListener {

            boardGame.type = typeEditText.text.toString()
            boardGame.nbMaxPlayer = nbMaxPlayerEditText.text.toString().toInt()
            boardGame.editor = editorEditText.text.toString()

            var stateCheck: String = boardGame.allOtherStatesCheck()
            if( stateCheck == "" ) {

                requestService.requestBuilderPut("boardGame", boardGame.toStringWithoutId())
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                        rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                    }
                })
            }
            else {

                Toast.makeText(this@EquipmentAddFormNext, stateCheck, Toast.LENGTH_LONG).show()
            }
        })

        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
        })
    }



    private fun videoGameType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intent: Intent) {

        requestService.requestBuilderGet("gameConsole")
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                var gameConsoles = mapper.readValue(response.body!!.string(), Array<GameConsole>::class.java)
                var gameConsolesName = arrayOfNulls<String>(gameConsoles.size)
                var i = 0

                for(gameConsole in gameConsoles) {

                    gameConsolesName[i] = gameConsole.name
                    i++
                }

                runOnUiThread {

                    setContentView(R.layout.video_game_form)

                    var videoGame: VideoGame = VideoGame()

                    val intentUser = intent.getIntExtra("idUser", 0)
                    val intentType = intent.getStringExtra("type")

                    videoGame.name = intent.getStringExtra("name")
                    videoGame.status = intent.getStringExtra("status")
                    videoGame.dateRecup = Date(intent.getLongExtra("dateRecup", 0))
                    videoGame.state = intent.getStringExtra("state")
                    videoGame.origin = intent.getStringExtra("origin")
                    videoGame.cfDoc = intent.getStringExtra("cfDoc")
                    videoGame.ableToBorrow = intent.getStringExtra("ableToBorrow")

                    var nbMaxPlayerEditText: EditText
                    var lanSpinner: Spinner
                    var gameConsoleSpinner: Spinner
                    var realiseDateTextView: TextView
                    var realiseDateButton: Button
                    var editorEditText: EditText

                    var submit: Button
                    var back: Button

                    nbMaxPlayerEditText = findViewById(R.id.nbMaxPlayer)
                    lanSpinner = findViewById(R.id.lanSpinner)
                    gameConsoleSpinner = findViewById(R.id.gameConsoleSpinner)
                    realiseDateTextView = findViewById(R.id.realiseDate)
                    realiseDateButton = findViewById(R.id.realiseDateButton)
                    editorEditText = findViewById(R.id.editor)

                    var simpleList = arrayOf("yes", "no")

                    if (lanSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@EquipmentAddFormNext, android.R.layout.simple_spinner_item, simpleList)
                        lanSpinner.adapter = arrayAdapter

                        lanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                videoGame.lan = simpleList[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                videoGame.lan = ""
                            }
                        }
                    }

                    if (gameConsoleSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@EquipmentAddFormNext, android.R.layout.simple_spinner_item, gameConsolesName)
                        gameConsoleSpinner.adapter = arrayAdapter

                        gameConsoleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                videoGame.gameConsole = gameConsoles[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                videoGame.gameConsole = null
                            }
                        }
                    }

                    realiseDateButton.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month: Int = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)

                        var datePickerDialog = DatePickerDialog(this@EquipmentAddFormNext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            var MonthOfYear = monthOfYear + 1
                            realiseDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                            videoGame.realiseDate = Date("$dayOfMonth/$MonthOfYear/$year")

                        }, year, month, day)

                        datePickerDialog.show()
                    })

                    submit = findViewById(R.id.submit)
                    submit.setOnClickListener(View.OnClickListener {

                        videoGame.nbMaxPlayer = nbMaxPlayerEditText.text.toString().toInt()
                        videoGame.editor = editorEditText.text.toString()

                        var stateCheck: String = videoGame.allOtherStatesCheck()
                        if (stateCheck == "") {

                            requestService.requestBuilderPut("videoGame", videoGame.toStringWithoutId())
                                    .enqueue(object : Callback {

                                        override fun onFailure(call: Call, e: IOException) {
                                            Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                        }

                                        @Throws(IOException::class)
                                        override fun onResponse(call: Call, response: Response) {

                                            rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                        }
                                    })
                        } else {

                            Toast.makeText(this@EquipmentAddFormNext, stateCheck, Toast.LENGTH_LONG).show()
                        }
                    })

                    back = findViewById(R.id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
                    })
                }
            }
        })
    }



    private fun gameConsoleType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intent: Intent) {

        setContentView(R.layout.game_console_form)

        var gameConsole: GameConsole = GameConsole()

        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")

        gameConsole.name = intent.getStringExtra("name")
        gameConsole.status = intent.getStringExtra("status")
        gameConsole.dateRecup = Date(intent.getLongExtra("dateRecup", 0))
        gameConsole.state = intent.getStringExtra("state")
        gameConsole.origin = intent.getStringExtra("origin")
        gameConsole.cfDoc = intent.getStringExtra("cfDoc")
        gameConsole.ableToBorrow = intent.getStringExtra("ableToBorrow")

        var nbMaxControllerEditText: EditText
        var powerCableSpinner: Spinner
        var videoCableSpinner: Spinner
        var realiseDateTextView: TextView
        var realiseDateButton: Button
        var editorEditText: EditText

        var submit: Button
        var back: Button

        nbMaxControllerEditText = findViewById(R.id.nbMaxController)
        powerCableSpinner = findViewById(R.id.powerCableSpinner)
        videoCableSpinner = findViewById(R.id.videoCableSpinner)
        realiseDateTextView = findViewById(R.id.realiseDate)
        realiseDateButton = findViewById(R.id.realiseDateButton)
        editorEditText = findViewById(R.id.editor)

        var simpleList = arrayOf("yes", "no")

        if (powerCableSpinner != null) {

            val arrayAdapter = ArrayAdapter(this@EquipmentAddFormNext, android.R.layout.simple_spinner_item, simpleList)
            powerCableSpinner.adapter = arrayAdapter

            powerCableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    gameConsole.powerCable = simpleList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                    gameConsole.videoCable = ""
                }
            }
        }

        if (videoCableSpinner != null) {

            val arrayAdapter = ArrayAdapter(this@EquipmentAddFormNext, android.R.layout.simple_spinner_item, simpleList)
            videoCableSpinner.adapter = arrayAdapter

            videoCableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    gameConsole.videoCable = simpleList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                    gameConsole.videoCable = ""
                }
            }
        }

        realiseDateButton.setOnClickListener(View.OnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            var datePickerDialog = DatePickerDialog(this@EquipmentAddFormNext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                var MonthOfYear = monthOfYear + 1
                realiseDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                gameConsole.realiseDate = Date("$dayOfMonth/$MonthOfYear/$year")

            }, year, month, day)

            datePickerDialog.show()
        })

        submit = findViewById(R.id.submit)
        submit.setOnClickListener(View.OnClickListener {

            gameConsole.nbMaxController = nbMaxControllerEditText.text.toString().toInt()
            gameConsole.editor = editorEditText.text.toString()

            var stateCheck: String = gameConsole.allOtherStatesCheck()
            if( stateCheck == "" ) {

                requestService.requestBuilderPut("gameConsole", gameConsole.toStringWithoutId())
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                            }
                        })
            }
            else {

                Toast.makeText(this@EquipmentAddFormNext, stateCheck, Toast.LENGTH_LONG).show()
            }
        })

        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
        })
    }



    private fun hardwareType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intent: Intent) {

        setContentView(R.layout.hardware_form)

        var hardware: Hardware = Hardware()

        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")

        hardware.name = intent.getStringExtra("name")
        hardware.status = intent.getStringExtra("status")
        hardware.dateRecup = Date(intent.getLongExtra("dateRecup", 0))
        hardware.state = intent.getStringExtra("state")
        hardware.origin = intent.getStringExtra("origin")
        hardware.cfDoc = intent.getStringExtra("cfDoc")
        hardware.ableToBorrow = intent.getStringExtra("ableToBorrow")

        var CPUEditText: EditText
        var RAMEditText: EditText
        var HDDEditText: TextView
        var GPUEditText: TextView
        var OSEditText: EditText

        var submit: Button
        var back: Button

        CPUEditText = findViewById(R.id.CPU)
        RAMEditText = findViewById(R.id.RAM)
        HDDEditText = findViewById(R.id.HDD)
        GPUEditText = findViewById(R.id.GPU)
        OSEditText = findViewById(R.id.OS)

        submit = findViewById(R.id.submit)
        submit.setOnClickListener(View.OnClickListener {

            hardware.cpu = CPUEditText.text.toString()
            hardware.ram = RAMEditText.text.toString()
            hardware.hdd = HDDEditText.text.toString()
            hardware.gpu = GPUEditText.text.toString()
            hardware.os = OSEditText.text.toString()

            var stateCheck: String = hardware.allOtherStatesCheck()
            if( stateCheck == "" ) {

                requestService.requestBuilderPut("hardware", hardware.toStringWithoutId())
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                            }
                        })
            }
            else {

                Toast.makeText(this@EquipmentAddFormNext, stateCheck, Toast.LENGTH_LONG).show()
            }
        })

        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
        })
    }



    private fun otherType(requestService: RequestService, rooterService: RooterService, mapper: ObjectMapper, context: Context, intent: Intent) {

        setContentView(R.layout.other_form)

        var other: Other = Other()

        val intentUser = intent.getIntExtra("idUser", 0)
        val intentType = intent.getStringExtra("type")

        other.name = intent.getStringExtra("name")
        other.status = intent.getStringExtra("status")
        other.dateRecup = Date(intent.getLongExtra("dateRecup", 0))
        other.state = intent.getStringExtra("state")
        other.origin = intent.getStringExtra("origin")
        other.cfDoc = intent.getStringExtra("cfDoc")
        other.ableToBorrow = intent.getStringExtra("ableToBorrow")

        var typeEditText: EditText

        var submit: Button
        var back: Button

        typeEditText = findViewById(R.id.type)

        submit = findViewById(R.id.submit)
        submit.setOnClickListener(View.OnClickListener {

            other.type = typeEditText.text.toString()

            var stateCheck: String = other.allOtherStatesCheck()
            if( stateCheck == "" ) {

                requestService.requestBuilderPut("other", other.toStringWithoutId())
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(this@EquipmentAddFormNext, "Conversation with server fail", Toast.LENGTH_LONG).show()
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                            }
                        })
            }
            else {

                Toast.makeText(this@EquipmentAddFormNext, stateCheck, Toast.LENGTH_LONG).show()
            }
        })

        back = findViewById(R.id.back)
        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EquipmentAddForm::class.java), context, intentUser, intentType, "type")
        })
    }
}