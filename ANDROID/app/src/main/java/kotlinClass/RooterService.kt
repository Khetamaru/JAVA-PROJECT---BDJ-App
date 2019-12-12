package kotlinClass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.bdjcrusadeinternalappli.MainPage

class RooterService {

    fun changeActivity(intent : Intent, context: Context) {

        startActivity(context, intent, Bundle())
    }

    fun changeActivity(intent : Intent, context: Context, idUser : Int) {

        intent.putExtra("idUser", idUser)
        startActivity(context, intent, Bundle())
    }

    fun changeActivity(intent : Intent, context: Context, idUser : Int, extraId : Int, extraName : String) {

        intent.putExtra("idUser", idUser)
        intent.putExtra(extraName, extraId)
        startActivity(context, intent, Bundle())
    }
}