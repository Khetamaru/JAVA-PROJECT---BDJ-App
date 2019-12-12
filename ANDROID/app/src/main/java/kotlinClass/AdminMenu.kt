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

class AdminMenu : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userManaging: Button

        var context = this
        var intent = intent
        var intentUser = intent.getIntExtra("idUser", 0)
        var rooterService = RooterService()

        var back: Button

        setContentView(layout.admin_menu)

        userManaging = findViewById(id.userManaging)
        back = findViewById(id.back)

        userManaging.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, UserManagingView::class.java), context, intentUser)
        })

        back.setOnClickListener(View.OnClickListener {

            rooterService.changeActivity(Intent(context, MainPage::class.java), context, intentUser)
        })
    }
}