package kotlinClass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.bdjcrusadeinternalappli.Equipment
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

    fun changeActivity(intent : Intent, context: Context, idUser : Int, extraString : String, extraName : String) {

        intent.putExtra("idUser", idUser)
        intent.putExtra(extraName, extraString)
        startActivity(context, intent, Bundle())
    }

    fun changeActivity(intent : Intent, context: Context, idUser : Int, extraId : Int, extraName : String, extraIdTwo : String, extraNameTwo : String) {

        intent.putExtra("idUser", idUser)
        intent.putExtra(extraName, extraId)
        intent.putExtra(extraNameTwo, extraIdTwo)
        startActivity(context, intent, Bundle())
    }



    fun changeActivity(intent : Intent, context: Context, idUser : Int, extraId : String, extraName : String, equipment: Equipment) {

        intent.putExtra("idUser", idUser)
        intent.putExtra(extraName, extraId)
        intent.putExtra("name", equipment.name)
        intent.putExtra("status", equipment.status)
        intent.putExtra("dateRecup", equipment.dateRecup.time)
        intent.putExtra("state", equipment.state)
        intent.putExtra("origin", equipment.origin)
        intent.putExtra("cfDoc", equipment.cfDoc)
        intent.putExtra("ableToBorrow", equipment.ableToBorrow)
        startActivity(context, intent, Bundle())
    }
}