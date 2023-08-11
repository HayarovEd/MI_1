package lo.zaemtoperson.gola.data

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi



val permissions = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val permissions34 = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_MEDIA_IMAGES
)

fun String.isValidFormat(): Boolean {
    val regex = Regex("""^[A-Za-z]+/\d+$""")
    return regex.matches(this)
}