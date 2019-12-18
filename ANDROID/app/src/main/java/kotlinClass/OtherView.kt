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

class OtherView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var listView : ListView
        var arrayList : ArrayList<User> = ArrayList()
        var listAdapter : ListAdapter
        var users : Array<User>

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var back: Button

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("user/allExept", intentUser)
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@OtherView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                users = mapper.readValue(response.body!!.string(), Array<User>::class.java)

                arrayList.addAll(users)

                runOnUiThread {

                    setContentView(R.layout.user_managing_view)

                    listView = findViewById<View>(id.UsersListView) as ListView
                    listView.adapter = Users_adapter(context, arrayList)

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, AdminMenu::class.java), context, intentUser)
                    })

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val user = listView.getItemAtPosition(position) as User
                        rooterService.changeActivity(Intent(v.context, UserManagingDetail::class.java), context, intentUser, user.idUser, "idUserManaging")
                    }
                }
            }
        })
    }
}