package com.bettigres.mi_1.ui.presentation

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bettigres.mi_1.R
import com.bettigres.mi_1.ui.theme.black
import com.bettigres.mi_1.ui.theme.green
import com.bettigres.mi_1.ui.theme.white
import java.io.File
import java.util.concurrent.ExecutorService

@Composable
fun SelfieScreen(
    modifier: Modifier = Modifier,
    setScreen: MutableState<ScreenState>,
    outputDirectory: File,
    executor: ExecutorService,
    onImageCaptured: (Uri) -> Unit,
    photoUri: Uri,
    shouldShowPhoto: MutableState<Boolean>,
    isShowCamera: MutableState<Boolean>,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = green)
            .padding(24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Title(
                onClick = {
                    setScreen.value = ScreenState.DateTime
                }
            )
            Spacer(modifier = modifier.height(47.dp))
            Text(
                text = stringResource(id = R.string.make_selfie),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                color = black
            )
            Spacer(modifier = modifier.height(16.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                ButtonImage(
                    modifier = modifier.weight(1f),
                    icon = ImageVector.vectorResource(id = R.drawable.outline_camera_alt_40),
                    content = stringResource(id = R.string.from_camera),
                    onClick = {isShowCamera.value=true}
                )
                Spacer(modifier = modifier.width(8.dp))
                ButtonImage(
                    modifier = modifier.weight(1f),
                    icon = ImageVector.vectorResource(id = R.drawable.ic24_photo_add),
                    content = stringResource(id = R.string.from_gallery),
                    onClick = {}
                )
            }
            if (isShowCamera.value) {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = {  }
                )
            }

            if (shouldShowPhoto.value) {
                Image(
                    painter = rememberImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = black,
                contentColor = white
            ),
            enabled = true,
            onClick = {
                setScreen.value = ScreenState.Confirm
            }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 18.sp,
                    color = white,
                    fontStyle = FontStyle(R.font.soyuz_grotesk_bold)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic24_redo),
                    tint = white,
                    contentDescription = "back"
                )
            }
        }
    }
}

@Composable
fun ButtonImage(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    content: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = white)
            .padding(
                top = 32.dp,
                bottom = 25.dp
            )
            .clickable { onClick.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            tint = black,
            contentDescription = "back"
        )
        Spacer(modifier = modifier.height(23.dp))
        Text(
            text = content,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle(R.font.onast_500),
            color = black
        )
    }
}