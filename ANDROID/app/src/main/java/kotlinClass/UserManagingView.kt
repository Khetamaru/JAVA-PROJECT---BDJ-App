package kotlinClass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import com.example.bdjcrusadeinternalappli.*
import com.example.bdjcrusadeinternalappli.R.id
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.*
import java.io.IOException

class UserManagingView : Activity() {

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
        val client: OkHttpClient = OkHttpClient()


        var back: Button

        setContentView(R.layout.user_managing_view)

        back = findViewById(R.id.back)

        val request = Request.Builder()
                .url("http://192.168.43.110:8080/user/allExept/$intentUser")
                .get()
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("LoaningAddNext", "fail", e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                users = mapper.readValue(response.body!!.string(), Array<User>::class.java)

                arrayList.addAll(users)

                runOnUiThread {

                    listView = findViewById<View>(id.UsersListView) as ListView
                    listView.adapter = Users_adapter(context, arrayList)

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        val intent = Intent(context, AdminMenu::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        startActivity(intent)
                    })

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val user = listView.getItemAtPosition(position) as User
                        val intent = Intent(v.context, UserManagingDetail::class.java)
                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0))
                        intent.putExtra("idUserManaging", user.idUser)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}