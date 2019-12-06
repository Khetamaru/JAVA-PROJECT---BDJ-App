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

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)

        var back: Button

        setContentView(layout.admin_menu)

        userManaging = findViewById(id.userManaging)
        back = findViewById(id.back)

        userManaging.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, UserManagingView::class.java)
            intent.putExtra("idUser", intentUser)
            startActivity(intent)
        })

        back.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, MainPage::class.java)
            intent.putExtra("idUser", intentUser)
            startActivity(intent)
        })
    }
}