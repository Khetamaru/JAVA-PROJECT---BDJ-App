package kotlinClass

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.board_game_managing_details.view.*
import kotlinx.android.synthetic.main.loaning_details.*
import kotlinx.android.synthetic.main.video_game_form.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class EquipmentManagingDetail : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            "boardGame" -> boardGameType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)

            "videoGame" -> videoGameType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)

            "gameConsole" -> gameConsoleType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)

            "hardware" -> hardwareType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)

            else -> otherType(requestService, intentEquipment, mapper, rooterService, context, intentUser, intentType)
        }
    }

    private fun boardGameType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var boardGame: BoardGame

        var nameEditText: EditText
        var typeEditText: EditText
        var nbMaxPlayerEditText: EditText
        var realiseDateTextView: TextView
        var realiseDateButton: Button
        var editorEditText: EditText

        var back: Button
        var save: Button
        var fullInfo: Button
        var delete: Button

        requestService.requestBuilderGet("boardGame", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                boardGame = mapper.readValue(response.body!!.string(), BoardGame::class.java)

                runOnUiThread {

                    setContentView(R.layout.board_game_managing_details)

                    nameEditText = findViewById(id.name)
                    typeEditText = findViewById(id.type)
                    nbMaxPlayerEditText = findViewById(id.nbMaxPlayer)
                    realiseDateTextView = findViewById(id.realiseDate)
                    realiseDateButton = findViewById(id.realiseDateButton)
                    editorEditText = findViewById(id.editor)

                    nameEditText.setText(boardGame.name)
                    typeEditText.setText(boardGame.type)
                    nbMaxPlayerEditText.setText(boardGame.nbMaxPlayer.toString())

                    var date = boardGame.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorEditText.setText(boardGame.editor)

                    realiseDateButton.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month: Int = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)

                        var datePickerDialog = DatePickerDialog(this@EquipmentManagingDetail, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            var MonthOfYear = monthOfYear + 1
                            realiseDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                            boardGame.realiseDate = Date("$dayOfMonth/$MonthOfYear/$year")

                        }, year, month, day)

                        datePickerDialog.show()
                    })

                    back = findViewById(id.back)
                    save = findViewById(id.save)
                    fullInfo = findViewById(id.otherInfo)
                    delete = findViewById(id.delete)

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                    })

                    save.setOnClickListener(View.OnClickListener {

                        boardGame.name = nameEditText.text.toString()
                        boardGame.status = typeEditText.text.toString()
                        boardGame.state = nbMaxPlayerEditText.text.toString()
                        boardGame.origin = editorEditText.text.toString()

                        var toastMessage: String = boardGame.allOtherStatesCheck()
                        if(toastMessage == "") {

                            requestService.requestBuilderPut(intentType, boardGame.toString())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Saved", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                        else {

                            runOnUiThread {

                                Toast.makeText(this@EquipmentManagingDetail, toastMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    fullInfo.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    delete.setOnClickListener(View.OnClickListener {

                        requestService.requestBuilderDelete(intentType, intentEquipment)
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                runOnUiThread {

                                    rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                }
                            }
                        })
                    })
                }
            }
        })
    }

    private fun gameConsoleType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var gameConsole: GameConsole

        var nameEditText: EditText
        var nbMaxControllerEditText: EditText
        var videoCableSpinner: Spinner
        var powerCableSpinner: Spinner
        var realiseDateTextView: TextView
        var realiseDateButton: Button
        var editorEditText: EditText

        var back: Button
        var save: Button
        var fullInfo: Button
        var delete: Button

        requestService.requestBuilderGet("gameConsole", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                gameConsole = mapper.readValue(response.body!!.string(), GameConsole::class.java)

                runOnUiThread {

                    setContentView(R.layout.game_console_managing_details)

                    nameEditText = findViewById(id.name)
                    nbMaxControllerEditText = findViewById(id.nbMaxController)
                    videoCableSpinner = findViewById(id.videoCable)
                    powerCableSpinner = findViewById(id.powerCable)
                    realiseDateTextView = findViewById(id.realiseDate)
                    realiseDateButton = findViewById(id.realiseDateButton)
                    editorEditText = findViewById(id.editor)

                    nameEditText.setText(gameConsole.name)
                    nbMaxControllerEditText.setText(gameConsole.nbMaxController.toString())

                    val choice = arrayOf("yes", "no")
                    var i = 0
                    var videoCableSpinnerSetter = 0
                    var powerCableSpinnerSetter = 0

                    for(choice in choice) {

                        if(choice == gameConsole.videoCable) {

                            videoCableSpinnerSetter = i
                        }

                        if(choice == gameConsole.powerCable) {

                            powerCableSpinnerSetter = i
                        }

                        i++
                    }

                    if (videoCableSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@EquipmentManagingDetail, R.layout.spinner_item, choice)
                        videoCableSpinner.adapter = arrayAdapter
                        videoCableSpinner.setSelection(videoCableSpinnerSetter)

                        videoCableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                gameConsole.videoCable = choice[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                gameConsole.videoCable = ""
                            }
                        }
                    }

                    if (powerCableSpinner != null) {

                        val arrayAdapter = ArrayAdapter(this@EquipmentManagingDetail, R.layout.spinner_item, choice)
                        powerCableSpinner.adapter = arrayAdapter
                        powerCableSpinner.setSelection(powerCableSpinnerSetter)

                        powerCableSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                gameConsole.powerCable = choice[position]
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                                gameConsole.powerCable = ""
                            }
                        }
                    }

                    realiseDateButton.setOnClickListener(View.OnClickListener {

                        val cal = Calendar.getInstance()
                        val year = cal.get(Calendar.YEAR)
                        val month: Int = cal.get(Calendar.MONTH)
                        val day = cal.get(Calendar.DAY_OF_MONTH)

                        var datePickerDialog = DatePickerDialog(this@EquipmentManagingDetail, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            var MonthOfYear = monthOfYear + 1
                            realiseDateTextView.setText("$dayOfMonth/$MonthOfYear/$year")
                            gameConsole.realiseDate = Date("$dayOfMonth/$MonthOfYear/$year")

                        }, year, month, day)

                        datePickerDialog.show()
                    })

                    var date = gameConsole.realiseDate.toString().split(" ")

                    realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                    editorEditText.setText(gameConsole.editor)

                    back = findViewById(id.back)
                    save = findViewById(id.save)
                    fullInfo = findViewById(id.otherInfo)
                    delete = findViewById(id.delete)

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                    })

                    save.setOnClickListener(View.OnClickListener {

                        gameConsole.name = nameEditText.text.toString()
                        gameConsole.nbMaxController = nbMaxControllerEditText.text.toString().toInt()
                        gameConsole.editor = editorEditText.text.toString()

                        var toastMessage: String = gameConsole.allOtherStatesCheck()
                        if(toastMessage == "") {

                            requestService.requestBuilderPut(intentType, gameConsole.toString())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Saved", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                        else {

                            runOnUiThread {

                                Toast.makeText(this@EquipmentManagingDetail, toastMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    fullInfo.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    delete.setOnClickListener(View.OnClickListener {

                        requestService.requestBuilderDelete(intentType, intentEquipment)
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                runOnUiThread {

                                    rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                }
                            }
                        })
                    })
                }
            }
        })
    }

    private fun videoGameType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var videoGame: VideoGame

        var nameEditText: EditText
        var gameConsoleSpinner: Spinner
        var nbMaxPlayerEditText: EditText
        var lanSpinner: Spinner
        var realiseDateTextView: TextView
        var realiseDateButton: Button
        var editorEditText: EditText

        var back: Button
        var save: Button
        var fullInfo: Button
        var delete: Button

        requestService.requestBuilderGet("videoGame", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                videoGame = mapper.readValue(response.body!!.string(), VideoGame::class.java)

                requestService.requestBuilderGet("gameConsole")
                .enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {

                       var gameConsoles = mapper.readValue(response.body!!.string(), Array<GameConsole>::class.java)

                        runOnUiThread {

                            setContentView(R.layout.video_game_managing_details)

                            nameEditText = findViewById(id.name)
                            gameConsoleSpinner = findViewById(id.gameConsole)
                            nbMaxPlayerEditText = findViewById(id.nbMaxPlayer)
                            lanSpinner = findViewById(id.lan)
                            realiseDateTextView = findViewById(id.realiseDate)
                            realiseDateButton = findViewById(id.realiseDateButton)
                            editorEditText = findViewById(id.editor)

                            nameEditText.setText(videoGame.name)

                            nbMaxPlayerEditText.setText(videoGame.nbMaxPlayer.toString())

                            var gameConsolesName = kotlin.arrayOfNulls<String>(gameConsoles.size)
                            var simpleList = arrayOf("yes", "no")
                            var i = 0
                            var gameConsoleSpinnerSetter = 0
                            var lanSpinnerSetter = 0

                            for(console in gameConsoles) {

                                if(console.name == videoGame.gameConsole.name) {

                                    gameConsoleSpinnerSetter = i
                                }

                                gameConsolesName[i] = console.name
                                i++
                            }

                            i = 0

                            for(name in simpleList) {

                                if(name == videoGame.lan) {

                                    lanSpinnerSetter = i
                                }

                                i++
                            }

                            if (gameConsoleSpinner != null) {

                                val arrayAdapter = ArrayAdapter(this@EquipmentManagingDetail, R.layout.spinner_item, gameConsolesName)
                                gameConsoleSpinner.adapter = arrayAdapter
                                gameConsoleSpinner.setSelection(gameConsoleSpinnerSetter)

                                gameConsoleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                        videoGame.gameConsole = gameConsoles[position]
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {

                                        videoGame.gameConsole = null
                                    }
                                }
                            }

                            if (lanSpinner != null) {

                                val arrayAdapter = ArrayAdapter(this@EquipmentManagingDetail, R.layout.spinner_item, simpleList)
                                lanSpinner.adapter = arrayAdapter
                                lanSpinner.setSelection(lanSpinnerSetter)

                                lanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                                        videoGame.lan = simpleList[position]
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {

                                        videoGame.lan = null
                                    }
                                }
                            }

                            var date = videoGame.realiseDate.toString().split(" ")

                            realiseDateTextView.text = date[2] + " " + date[1] + " " + date[5]
                            editorEditText.setText(videoGame.editor)

                            back = findViewById(id.back)
                            save = findViewById(id.save)
                            fullInfo = findViewById(id.otherInfo)
                            delete = findViewById(id.delete)

                            back.setOnClickListener(View.OnClickListener {

                                rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                            })

                            save.setOnClickListener(View.OnClickListener {

                                videoGame.name = nameEditText.text.toString()
                                videoGame.nbMaxPlayer = nbMaxPlayerEditText.text.toString().toInt()
                                videoGame.editor = editorEditText.text.toString()

                                var toastMessage: String = videoGame.allOtherStatesCheck()
                                if(toastMessage == "") {

                                    requestService.requestBuilderPut(intentType, videoGame.toString())
                                    .enqueue(object : Callback {

                                        override fun onFailure(call: Call, e: IOException) {

                                            runOnUiThread {

                                                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                            }
                                        }

                                        @Throws(IOException::class)
                                        override fun onResponse(call: Call, response: Response) {

                                            runOnUiThread {

                                                Toast.makeText(this@EquipmentManagingDetail, "Saved", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    })
                                }
                                else {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, toastMessage, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })

                            fullInfo.setOnClickListener(View.OnClickListener {

                                rooterService.changeActivity(Intent(context, EquipmentManagingFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                            })

                            delete.setOnClickListener(View.OnClickListener {

                                requestService.requestBuilderDelete(intentType, intentEquipment)
                                .enqueue(object : Callback {

                                    override fun onFailure(call: Call, e: IOException) {

                                        runOnUiThread {

                                            Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                    @Throws(IOException::class)
                                    override fun onResponse(call: Call, response: Response) {

                                        runOnUiThread {

                                            rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                        }
                                    }
                                })
                            })
                        }
                    }
                })
            }
        })
    }

    private fun hardwareType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var hardware: Hardware

        var nameEditText: EditText
        var CPUEditText: EditText
        var RAMEditText: EditText
        var HDDEditText: EditText
        var GPUEditText: EditText
        var OSEditText: EditText

        var back: Button
        var save: Button
        var fullInfo: Button
        var delete: Button

        requestService.requestBuilderGet("hardware", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                hardware = mapper.readValue(response.body!!.string(), Hardware::class.java)

                runOnUiThread {

                    setContentView(R.layout.hardware_managing_details)

                    nameEditText = findViewById(id.name)
                    CPUEditText = findViewById(id.CPU)
                    RAMEditText = findViewById(id.RAM)
                    HDDEditText = findViewById(id.HDD)
                    GPUEditText = findViewById(id.GPU)
                    OSEditText = findViewById(id.OS)

                    nameEditText.setText(hardware.name)
                    CPUEditText.setText(hardware.cpu)
                    RAMEditText.setText(hardware.ram)
                    HDDEditText.setText(hardware.hdd)
                    GPUEditText.setText(hardware.gpu)
                    OSEditText.setText(hardware.os)

                    back = findViewById(id.back)
                    save = findViewById(id.save)
                    fullInfo = findViewById(id.otherInfo)
                    delete = findViewById(id.delete)

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                    })

                    save.setOnClickListener(View.OnClickListener {

                        hardware.name = nameEditText.text.toString()
                        hardware.cpu = CPUEditText.text.toString()
                        hardware.ram = RAMEditText.text.toString()
                        hardware.hdd = HDDEditText.text.toString()
                        hardware.gpu = GPUEditText.text.toString()
                        hardware.os = OSEditText.text.toString()

                        var toastMessage: String = hardware.allOtherStatesCheck()
                        if(toastMessage == "") {

                            requestService.requestBuilderPut(intentType, hardware.toString())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Saved", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                        else {

                            runOnUiThread {

                                Toast.makeText(this@EquipmentManagingDetail, toastMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    fullInfo.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    delete.setOnClickListener(View.OnClickListener {

                        requestService.requestBuilderDelete(intentType, intentEquipment)
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                runOnUiThread {

                                    rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                }
                            }
                        })
                    })
                }
            }
        })
    }

    private fun otherType(requestService: RequestService, intentEquipment: Int, mapper: ObjectMapper, rooterService: RooterService, context: Context, intentUser: Int, intentType: String) {

        var other: Other

        var nameEditText: EditText
        var typeEditText: EditText

        var back: Button
        var save: Button
        var fullInfo: Button
        var delete: Button

        requestService.requestBuilderGet("other", intentEquipment)
        .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                other = mapper.readValue(response.body!!.string(), Other::class.java)

                runOnUiThread {

                    setContentView(R.layout.other_managing_details)

                    nameEditText = findViewById(id.name)
                    typeEditText = findViewById(id.type)

                    nameEditText.setText(other.name)
                    typeEditText.setText(other.type)

                    back = findViewById(id.back)
                    save = findViewById(id.save)
                    fullInfo = findViewById(id.otherInfo)
                    delete = findViewById(id.delete)

                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                    })

                    save.setOnClickListener(View.OnClickListener {

                        other.name = nameEditText.text.toString()
                        other.type = typeEditText.text.toString()

                        var toastMessage: String = other.allOtherStatesCheck()
                        if(toastMessage == "") {

                            requestService.requestBuilderPut(intentType, other.toString())
                            .enqueue(object : Callback {

                                override fun onFailure(call: Call, e: IOException) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                    }
                                }

                                @Throws(IOException::class)
                                override fun onResponse(call: Call, response: Response) {

                                    runOnUiThread {

                                        Toast.makeText(this@EquipmentManagingDetail, "Saved", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        }
                        else {

                            runOnUiThread {

                                Toast.makeText(this@EquipmentManagingDetail, toastMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    fullInfo.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, EquipmentManagingFullInfoDetail::class.java), context, intentUser, intentEquipment, "idEquipment", intentType, "type")
                    })

                    delete.setOnClickListener(View.OnClickListener {

                        requestService.requestBuilderDelete(intentType, intentEquipment)
                        .enqueue(object : Callback {

                            override fun onFailure(call: Call, e: IOException) {

                                runOnUiThread {

                                    Toast.makeText(this@EquipmentManagingDetail, "Conversation with server fail", Toast.LENGTH_LONG).show()
                                }
                            }

                            @Throws(IOException::class)
                            override fun onResponse(call: Call, response: Response) {

                                runOnUiThread {

                                    rooterService.changeActivity(Intent(context, EquipmentManagingView::class.java), context, intentUser, intentType, "type")
                                }
                            }
                        })
                    })
                }
            }
        })
    }
}