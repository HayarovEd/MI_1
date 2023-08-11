package lo.zaemtoperson.gola.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            intent.extras?.let {
                for (key in it.keySet()) {
                    val value = intent.extras?.get(key)
                    Log.d("BBBBBB", "Key: $key Value: $value")
                }
            }
        }
    }
}