package lo.zaemtoperson.gola.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.MimeTypeMap
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.R.layout
import lo.zaemtoperson.gola.data.FILECHOOSER_RESULTCODE
import lo.zaemtoperson.gola.data.INPUT_FILE_REQUEST_CODE

class WebActivity : AppCompatActivity() {
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var imageOutputFileUri: Uri? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it?.data?.data?.let{ uri ->
            mFilePathCallback?.onReceiveValue(arrayOf(uri))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_web)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    /*Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Greeting("Pick Image From Gallery") {
                            pickImageFromGallery()
                        }
                        Greeting("Pick Image From Camera") {
                            pickImageFromCamera()
                        }
                    }*/

                    MyContent()
                }
            }
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun MyContent(){

        // Declare a string that contains a url
        val mUrl = "https://ya.ru/images"

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                webChromeClient = object : WebChromeClient() {

                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {

                        val acceptTypes = fileChooserParams!!.acceptTypes
                        val allowMultiple =
                            fileChooserParams!!.mode === FileChooserParams.MODE_OPEN_MULTIPLE
                        val captureEnabled = fileChooserParams.isCaptureEnabled

                        return startPickerIntent(filePathCallback, acceptTypes, allowMultiple, captureEnabled)
                    }


                }
                settings.allowFileAccess = true
                settings.javaScriptEnabled = true
                isClickable = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                settings.loadWithOverviewMode = true
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }

    fun startPickerIntent(callback: ValueCallback<Array<Uri>>?,
                          acceptTypes: Array<String>,
                          allowMultiple: Boolean?,
                          captureEnabled: Boolean?
    ): Boolean{
        mFilePathCallback = callback;
        val extraIntents = ArrayList<Parcelable>()
        extraIntents.add(getPhotoIntent());
        val fileSelectionIntent = getFileChooserIntent(acceptTypes, allowMultiple)
        val pickerIntent = Intent(Intent.ACTION_CHOOSER);
        pickerIntent.putExtra(Intent.EXTRA_INTENT, fileSelectionIntent);
        pickerIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents.toTypedArray());
        launcher.launch(pickerIntent);
        return true;
    }

    private fun getPhotoIntent(): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageOutputFileUri = getOutputUri(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageOutputFileUri)
        return intent
    }


    private fun getOutputUri(intentType: String): Uri? {
        var capturedFile: File? = null
        try {
            capturedFile = getCapturedFile(intentType)
        } catch (e: IOException) {
            Log.e("web_view", "Error occurred while creating the File", e);
            e.printStackTrace()
        }
        if (capturedFile == null) {
            return null
        }
        // for versions below 6.0 (23) we use the old File creation & permissions model
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return Uri.fromFile(capturedFile)
        }
        val activity = getActivity()
        // for versions 6.0+ (23) we use the FileProvider to avoid runtime permissions
        val fileProviderAuthority: String =
            activity.applicationContext.packageName + "." + "webview.fileprovider"
        return FileProvider.getUriForFile(
            activity.applicationContext,
            fileProviderAuthority,
            capturedFile
        )
    }


    @Throws(IOException::class)
    private fun getCapturedFile(intentType: String): File? {
        var prefix = ""
        var suffix = ""
        var dir = ""
        if (intentType == MediaStore.ACTION_IMAGE_CAPTURE) {
            prefix = "image"
            suffix = ".jpg"
            dir = Environment.DIRECTORY_PICTURES
        } else if (intentType == MediaStore.ACTION_VIDEO_CAPTURE) {
            prefix = "video"
            suffix = ".mp4"
            dir = Environment.DIRECTORY_MOVIES
        }

        // for versions below 6.0 (23) we use the old File creation & permissions model
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // only this Directory works on all tested Android versions
            // ctx.getExternalFilesDir(dir) was failing on Android 5.0 (sdk 21)
            val storageDir: File =
                Environment.getExternalStoragePublicDirectory(dir)
            val filename =
                String.format("%s-%d%s", prefix, System.currentTimeMillis(), suffix)
            return File(storageDir, filename)
        }
        val activity = getActivity();
        val storageDir =
            activity.applicationContext.getExternalFilesDir(null)
        return File.createTempFile(prefix, suffix, storageDir)
    }

    private fun getFileChooserIntent(
        acceptTypes: Array<String>,
        allowMultiple: Boolean?
    ): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, getAcceptedMimeType(acceptTypes))
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)
        return intent
    }

    private fun getAcceptedMimeType(types: Array<String>): Array<String?>? {
        if (types.isEmpty()) {
            val DEFAULT_MIME_TYPES = "*/*";
            return arrayOf<String?>(DEFAULT_MIME_TYPES)
        }
        val mimeTypes = arrayOfNulls<String>(types.size)
        for (i in types.indices) {
            val t = types[i]
            val regex = Regex("\\.\\w+")
            if (t.matches(regex)) {
                val oldValue = ".";
                val newValue = "";
                val mimeType = getMimeTypeFromExtension(t.replace(oldValue, newValue))
                mimeTypes[i] = mimeType
            } else {
                mimeTypes[i] = t
            }
        }
        return mimeTypes
    }

    private fun getMimeTypeFromExtension(extension: String?): String? {
        var type: String? = null
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private fun getActivity(): Activity {
        return this
    }
}