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

class LoaningManagingView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var listView : ListView
        var arrayList : ArrayList<Loaning> = ArrayList()
        var listAdapter : ListAdapter
        var loans : Array<Loaning>

        val context = this
        var intent = intent
        val intentUser = intent.getIntExtra("idUser", 0)
        val mapper: ObjectMapper = ObjectMapper()
        var rooterService = RooterService()
        var requestService = RequestService()

        var back: Button

        setContentView(R.layout.loading_page)

        requestService.requestBuilderGet("loaning")
                .enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@LoaningManagingView, "Conversation with server fail", Toast.LENGTH_LONG).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                loans = mapper.readValue(response.body!!.string(), Array<Loaning>::class.java)

                arrayList.addAll(loans)

                runOnUiThread {

                    setContentView(R.layout.loaning_all)

                    listView = findViewById<View>(id.loaningListView) as ListView
                    listView.adapter = Loaning_adapter(context, arrayList)

                    back = findViewById(id.back)
                    back.setOnClickListener(View.OnClickListener {

                        rooterService.changeActivity(Intent(context, AdminMenu::class.java), context, intentUser)
                    })

                    listView.setOnItemClickListener { adapterView, v, position: Int, id: Long ->

                        val loaning = listView.getItemAtPosition(position) as Loaning
                        rooterService.changeActivity(Intent(v.context, LoaningManagingDetail::class.java), context, intentUser, loaning.idLoaning, "idLoaningManaging")
                    }
                }
            }
        })
    }
}