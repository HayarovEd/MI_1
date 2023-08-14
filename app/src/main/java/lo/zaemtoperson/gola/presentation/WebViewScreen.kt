package lo.zaemtoperson.gola.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
private var imageOutputFileUri: Uri? = null

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    url: String,
) {
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.data.let {uri->
            if (uri!=null) {
                mFilePathCallback?.onReceiveValue(arrayOf(uri))
            }
        }
    }
    val context = LocalContext.current
    /*val isLoadingUrl: MutableState<Boolean> = remember {
        mutableStateOf(true)
    }
    webView.loadUrl(url)
    webView.webViewClient = WebViewClientHand(isLoadingUrl = isLoadingUrl)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(5.dp)
    ) {
        AndroidView(
            modifier = modifier
                .fillMaxSize()
                .background(color = baseBackground)
                .padding(5.dp),
            factory = { webView })
        if (isLoadingUrl.value) {
            CircularProgressIndicator(
                modifier = modifier
                    .size(100.dp)
                    .align(alignment = Alignment.Center),
                color = green
            )
        }
    }*/

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

                    return startPickerIntent(
                        callback = filePathCallback,
                        acceptTypes =acceptTypes,
                        allowMultiple = allowMultiple,
                        captureEnabled = captureEnabled,
                        activityResultLauncher = activityResultLauncher,
                        context = context)
                }


            }
            settings.allowFileAccess = true
            settings.javaScriptEnabled = true
            isClickable = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            settings.loadWithOverviewMode = true
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}

/*
class WebViewClientHand(val isLoadingUrl: MutableState<Boolean>) : android.webkit.WebViewClient() {

    // Load the URL
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return false
    }

    // ProgressBar will disappear once page is loaded
    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        isLoadingUrl.value = false
    }
}*/

fun startPickerIntent(
    callback: ValueCallback<Array<Uri>>?,
    acceptTypes: Array<String>,
    allowMultiple: Boolean?,
    captureEnabled: Boolean?,
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
): Boolean{
    mFilePathCallback = callback;
    val extraIntents = ArrayList<Parcelable>()
    extraIntents.add(getPhotoIntent(
        activityResultLauncher = activityResultLauncher,
        context = context))
    val fileSelectionIntent = getFileChooserIntent(acceptTypes, allowMultiple)
    val pickerIntent = Intent(Intent.ACTION_CHOOSER)
    pickerIntent.putExtra(Intent.EXTRA_INTENT, fileSelectionIntent);
    pickerIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents.toTypedArray());
    activityResultLauncher.launch(pickerIntent)
    return true;
}

private fun getPhotoIntent(
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
): Intent {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    imageOutputFileUri = getOutputUri(
        intentType = MediaStore.ACTION_IMAGE_CAPTURE,
        activityResultLauncher = activityResultLauncher,
        context = context)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageOutputFileUri)
    return intent
}

private fun getOutputUri(
    intentType: String,
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
): Uri? {
    var capturedFile: File? = null
    try {
        capturedFile = getCapturedFile(
            intentType = intentType,
            activityResultLauncher = activityResultLauncher,
            context = context
            )
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
    // for versions 6.0+ (23) we use the FileProvider to avoid runtime permissions
    val fileProviderAuthority: String =
        context.packageName + "." + "webview.fileprovider"
    return FileProvider.getUriForFile(
        context,
        fileProviderAuthority,
        capturedFile
    )
}


@Throws(IOException::class)
private fun getCapturedFile(
    intentType: String,
    activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    context: Context
): File? {
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
    val storageDir =
        context.getExternalFilesDir(null)
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
