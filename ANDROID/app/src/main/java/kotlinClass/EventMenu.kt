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

class EventMenu : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var eventButton: Button
        var teamButton: Button

        var context = this
        var intent = intent
        var intentUser = intent.getIntExtra("idUser", 0)
        var rooterService = RooterService()

        var back: Button

        setContentView(layout.event_menu)

        eventButton = findViewById(id.eventButton)
        teamButton = findViewById(id.teamButton)

        back = findViewById(id.back)

        eventButton.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, EventView::class.java), context, intentUser)
        })

        teamButton.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, TeamView::class.java), context, intentUser)
        })

        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, MainPage::class.java), context, intentUser)
        })
    }
}