package kotlinClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.bdjcrusadeinternalappli.MainPage
import com.example.bdjcrusadeinternalappli.R.*
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient

class EquipmentSelectTypeMenu : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var hardware: Button
        var videoGame: Button
        var gameConsole: Button
        var boardGame: Button
        var other: Button

        var context = this
        var intent = intent
        var intentUser = intent.getIntExtra("idUser", 0)
        var rooterService = RooterService()

        var back: Button

        setContentView(layout.select_equipment_type_view)

        hardware = findViewById(id.hardware)
        videoGame = findViewById(id.videoGame)
        gameConsole = findViewById(id.gameConsole)
        boardGame = findViewById(id.boardGame)
        other = findViewById(id.other)

        back = findViewById(id.back)

        hardware.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, HardwareView::class.java), context, intentUser)
        })

        videoGame.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, VideoGameView::class.java), context, intentUser)
        })

        gameConsole.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, GameConsoleView::class.java), context, intentUser)
        })

        boardGame.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, BoardGameView::class.java), context, intentUser)
        })

        other.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, OtherView::class.java), context, intentUser)
        })

        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, MainPage::class.java), context, intentUser)
        })
    }
}