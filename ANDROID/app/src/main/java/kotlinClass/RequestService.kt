package kotlinClass

import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class RequestService() {

    val client : OkHttpClient = OkHttpClient()
    val requestType = "http"
    val address = "192.168.43.110"
    val port = "8080"

    fun requestBuilderGet(controller : String, id : Int) : Call {

        val request = Request.Builder()
                .url("$requestType://$address:$port/$controller/$id")
                .get()
                .build()

        return client.newCall(request)
    }

    fun requestBuilderGet(controller : String) : Call {

        val request = Request.Builder()
                .url("$requestType://$address:$port/$controller")
                .get()
                .build()

        return client.newCall(request)
    }

    fun requestBuilderPut(controller : String, obj : String) : Call {

        val body = RequestBody.create("application/json; charset=utf-8".toMediaType(), obj)

        val request = Request.Builder()
                .url("$requestType://$address:$port/$controller")
                .put(body)
                .build()

        return client.newCall(request)
    }

    fun requestBuilderDelete(controller : String, id : Int) : Call {

        val request = Request.Builder()
                .url("$requestType://$address:$port/$controller/$id")
                .delete()
                .build()

        return client.newCall(request)
    }

    fun requestBuilderDelete(controller : String) : Call {

        val request = Request.Builder()
                .url("$requestType://$address:$port/$controller")
                .delete()
                .build()

        return client.newCall(request)
    }
}