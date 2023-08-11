package lo.zaemtoperson.gola;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import static android.app.Activity.RESULT_OK;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class InAppWebViewChromeClient {

    protected static final String LOG_TAG = "IABWebChromeClient";
    Activity activity;
    private static final int PICKER = 1;
    private static final int PICKER_LEGACY = 3;
    final String DEFAULT_MIME_TYPES = "*/*";
    final Map<DialogInterface, JsResult> dialogs = new HashMap();

    @Nullable
    private ValueCallback<Uri> filePathCallbackLegacy;
    @Nullable
    private ValueCallback<Uri[]> filePathCallback;
    @Nullable
    private Uri videoOutputFileUri;
    @Nullable
    private Uri imageOutputFileUri;

    protected static final FrameLayout.LayoutParams FULLSCREEN_LAYOUT_PARAMS = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        String[] acceptTypes = fileChooserParams.getAcceptTypes();
        boolean allowMultiple = fileChooserParams.getMode() == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE;
        boolean captureEnabled = fileChooserParams.isCaptureEnabled();
        return startPickerIntent(filePathCallback, acceptTypes, allowMultiple, captureEnabled);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean startPickerIntent(final ValueCallback<Uri[]> callback, final String[] acceptTypes,
                                     final boolean allowMultiple, final boolean captureEnabled) {
        filePathCallback = callback;
        Intent pickerIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            pickerIntent =
                    new ActivityResultContracts.PickVisualMedia()
                            .createIntent(
                                    activity,
                                    new PickVisualMediaRequest.Builder()
                                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                            .build());
        }else{
            boolean images = acceptsImages(acceptTypes);
            boolean video = acceptsVideo(acceptTypes);

            if (captureEnabled) {
                if (!needsCameraPermission()) {
                    if (images) {
                        pickerIntent = getPhotoIntent();
                    }
                    else if (video) {
                        pickerIntent = getVideoIntent();
                    }
                }
            }
            if (pickerIntent == null) {
                ArrayList<Parcelable> extraIntents = new ArrayList<>();
                if (!needsCameraPermission()) {
                    if (images) {
                        extraIntents.add(getPhotoIntent());
                    }
                    if (video) {
                        extraIntents.add(getVideoIntent());
                    }
                }

                Intent fileSelectionIntent = getFileChooserIntent(acceptTypes, allowMultiple);

                pickerIntent = new Intent(Intent.ACTION_CHOOSER);
                pickerIntent.putExtra(Intent.EXTRA_INTENT, fileSelectionIntent);
                pickerIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents.toArray(new Parcelable[]{}));
            }
        }

        if (activity != null && pickerIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(pickerIntent, PICKER);
        } else {
            Log.d(LOG_TAG, "there is no Activity to handle this Intent");
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Intent getFileChooserIntent(String[] acceptTypes, boolean allowMultiple) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, getAcceptedMimeType(acceptTypes));
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple);
        return intent;
    }

    public void startPickerIntent(ValueCallback<Uri> filePathCallback, String acceptType, @Nullable String capture) {
        filePathCallbackLegacy = filePathCallback;

        boolean images = acceptsImages(acceptType);
        boolean video = acceptsVideo(acceptType);

        Intent pickerIntent = null;

        if (capture != null) {
            if (!needsCameraPermission()) {
                if (images) {
                    pickerIntent = getPhotoIntent();
                }
                else if (video) {
                    pickerIntent = getVideoIntent();
                }
            }
        }
        if (pickerIntent == null) {
            Intent fileChooserIntent = getFileChooserIntent(acceptType);
            pickerIntent = Intent.createChooser(fileChooserIntent, "");

            ArrayList<Parcelable> extraIntents = new ArrayList<>();
            if (!needsCameraPermission()) {
                if (images) {
                    extraIntents.add(getPhotoIntent());
                }
                if (video) {
                    extraIntents.add(getVideoIntent());
                }
            }
            pickerIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents.toArray(new Parcelable[]{}));
        }

        if (activity != null && pickerIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(pickerIntent, PICKER_LEGACY);
        } else {
            Log.d(LOG_TAG, "there is no Activity to handle this Intent");
        }
    }

    public void dispose() {
        for (Map.Entry<DialogInterface, JsResult> dialog : dialogs.entrySet()) {
            dialog.getValue().cancel();
            dialog.getKey().dismiss();
        }
        dialogs.clear();
        /*if (plugin != null && plugin.activityPluginBinding != null) {
            plugin.activityPluginBinding.removeActivityResultListener(this);
        }
        if (inAppBrowserDelegate != null) {
            inAppBrowserDelegate.getActivityResultListeners().clear();
            inAppBrowserDelegate = null;
        }*/
        filePathCallbackLegacy = null;
        filePathCallback = null;
        videoOutputFileUri = null;
        imageOutputFileUri = null;
        //inAppWebView = null;
       // plugin = null;
    }

    private Boolean acceptsImages(String types) {
        String mimeType = types;
        if (types.matches("\\.\\w+")) {
            mimeType = getMimeTypeFromExtension(types.replace(".", ""));
        }
        return mimeType.isEmpty() || mimeType.toLowerCase().contains("image");
    }

    private Boolean acceptsImages(String[] types) {
        String[] mimeTypes = getAcceptedMimeType(types);
        return acceptsAny(types) || arrayContainsString(mimeTypes, "image");
    }

    private Boolean acceptsVideo(String types) {
        String mimeType = types;
        if (types.matches("\\.\\w+")) {
            mimeType = getMimeTypeFromExtension(types.replace(".", ""));
        }
        return mimeType.isEmpty() || mimeType.toLowerCase().contains("video");
    }
    protected boolean needsCameraPermission() {
        boolean needed = false;

        if (activity == null) {
            return true;
        }
        PackageManager packageManager = activity.getPackageManager();
        try {
            String[] requestedPermissions = packageManager.getPackageInfo(activity.getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (Arrays.asList(requestedPermissions).contains(android.Manifest.permission.CAMERA)
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                needed = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            needed = true;
        }

        return needed;
    }
    private String getMimeTypeFromExtension(String extension) {
        String type = null;
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private String[] getAcceptedMimeType(String[] types) {
        if (isArrayEmpty(types)) {
            return new String[]{DEFAULT_MIME_TYPES};
        }
        String[] mimeTypes = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            String t = types[i];
            // convert file extensions to mime types
            if (t.matches("\\.\\w+")) {
                String mimeType = getMimeTypeFromExtension(t.replace(".", ""));
                mimeTypes[i] = mimeType;
            } else {
                mimeTypes[i] = t;
            }
        }
        return mimeTypes;
    }
    private Boolean isArrayEmpty(String[] arr) {
        return arr.length == 0 || (arr.length == 1 && arr[0].length() == 0);
    }

    private Boolean acceptsAny(String[] types) {
        if (isArrayEmpty(types)) {
            return true;
        }

        for (String type : types) {
            if (type.equals("*/*")) {
                return true;
            }
        }

        return false;
    }
    private Boolean arrayContainsString(String[] array, String pattern) {
        for (String content : array) {
            if (content != null && content.contains(pattern)) {
                return true;
            }
        }
        return false;
    }
    private Intent getPhotoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageOutputFileUri = getOutputUri(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageOutputFileUri);
        return intent;
    }

    private Intent getVideoIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        videoOutputFileUri = getOutputUri(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoOutputFileUri);
        return intent;
    }

    @Nullable
    private Uri getOutputUri(String intentType) {
        File capturedFile = null;
        try {
            capturedFile = getCapturedFile(intentType);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error occurred while creating the File", e);
            e.printStackTrace();
        }
        if (capturedFile == null) {
            return null;
        }

        // for versions below 6.0 (23) we use the old File creation & permissions model
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return Uri.fromFile(capturedFile);
        }

        if (activity == null) {
            return null;
        }
        // for versions 6.0+ (23) we use the FileProvider to avoid runtime permissions
        String fileProviderAuthority = activity.getApplicationContext().getPackageName();
        return FileProvider.getUriForFile(activity.getApplicationContext(),
                fileProviderAuthority,
                capturedFile);
    }
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (filePathCallback == null && filePathCallbackLegacy == null) {
            return true;
        }

        // based off of which button was pressed, we get an activity result and a file
        // the camera activity doesn't properly return the filename* (I think?) so we use
        // this filename instead
        switch (requestCode) {
            case PICKER:
                Uri[] results = null;
                if (resultCode == RESULT_OK) {
                    results = getSelectedFiles(data, resultCode);
                }

                if (filePathCallback != null) {
                    filePathCallback.onReceiveValue(results);
                }
                break;

            case PICKER_LEGACY:
                Uri result = null;
                if (resultCode == RESULT_OK) {
                    result = data != null ? data.getData() : getCapturedMediaFile();
                }
                if (filePathCallbackLegacy != null) {
                    filePathCallbackLegacy.onReceiveValue(result);
                }
                break;
        }

        filePathCallback = null;
        filePathCallbackLegacy = null;
        imageOutputFileUri = null;
        videoOutputFileUri = null;

        return true;
    }

    private Uri getCapturedMediaFile() {
        if (imageOutputFileUri != null && isFileNotEmpty(imageOutputFileUri)) {
            return imageOutputFileUri;
        }

        if (videoOutputFileUri != null && isFileNotEmpty(videoOutputFileUri)) {
            return videoOutputFileUri;
        }

        return null;
    }

    private boolean isFileNotEmpty(Uri uri) {
        if (activity == null) {
            return false;
        }

        long length;
        try {
            AssetFileDescriptor descriptor = activity.getContentResolver().openAssetFileDescriptor(uri, "r");
            length = descriptor.getLength();
            descriptor.close();
        } catch (IOException e) {
            return false;
        }

        return length > 0;
    }
    private Uri[] getSelectedFiles(Intent data, int resultCode) {
        // we have one file selected
        if (data != null && data.getData() != null) {
            if (resultCode == RESULT_OK && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return WebChromeClient.FileChooserParams.parseResult(resultCode, data);
            } else {
                return null;
            }
        }

        // we have multiple files selected
        if (data != null && data.getClipData() != null) {
            final int numSelectedFiles = data.getClipData().getItemCount();
            Uri[] result = new Uri[numSelectedFiles];
            for (int i = 0; i < numSelectedFiles; i++) {
                result[i] = data.getClipData().getItemAt(i).getUri();
            }
            return result;
        }

        // we have a captured image or video file
        Uri mediaUri = getCapturedMediaFile();
        if (mediaUri != null) {
            return new Uri[]{mediaUri};
        }

        return null;
    }

    @Nullable
    private File getCapturedFile(String intentType) throws IOException {
        String prefix = "";
        String suffix = "";
        String dir = "";

        if (intentType.equals(MediaStore.ACTION_IMAGE_CAPTURE)) {
            prefix = "image";
            suffix = ".jpg";
            dir = Environment.DIRECTORY_PICTURES;
        } else if (intentType.equals(MediaStore.ACTION_VIDEO_CAPTURE)) {
            prefix = "video";
            suffix = ".mp4";
            dir = Environment.DIRECTORY_MOVIES;
        }

        // for versions below 6.0 (23) we use the old File creation & permissions model
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // only this Directory works on all tested Android versions
            // ctx.getExternalFilesDir(dir) was failing on Android 5.0 (sdk 21)
            File storageDir = Environment.getExternalStoragePublicDirectory(dir);
            String filename = String.format("%s-%d%s", prefix, System.currentTimeMillis(), suffix);
            return new File(storageDir, filename);
        }

        if (activity == null) {
            return null;
        }
        File storageDir = activity.getApplicationContext().getExternalFilesDir(null);
        return File.createTempFile(prefix, suffix, storageDir);
    }

    private Intent getFileChooserIntent(String acceptTypes) {
        String _acceptTypes = acceptTypes;
        if (acceptTypes.isEmpty()) {
            _acceptTypes = DEFAULT_MIME_TYPES;
        }
        if (acceptTypes.matches("\\.\\w+")) {
            _acceptTypes = getMimeTypeFromExtension(acceptTypes.replace(".", ""));
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(_acceptTypes);
        return intent;
    }

    private Boolean acceptsVideo(String[] types) {
        String[] mimeTypes = getAcceptedMimeType(types);
        return acceptsAny(types) || arrayContainsString(mimeTypes, "video");
    }
}
