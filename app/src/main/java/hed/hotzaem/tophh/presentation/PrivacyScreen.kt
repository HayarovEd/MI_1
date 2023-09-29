package hed.hotzaem.tophh.gola.presentation

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import lo.zaemtoperson.gola.R
import hed.hotzaem.tophh.gola.domain.model.StatusApplication
import hed.hotzaem.tophh.gola.domain.model.basedto.BaseState
import hed.hotzaem.tophh.gola.ui.theme.absoluteDark
import hed.hotzaem.tophh.gola.ui.theme.baseBackground

@Composable
fun PrivacyScreen(
    modifier: Modifier = Modifier,
    content: String,
    baseState: BaseState,
    onEvent: (MainEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = baseBackground)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    onEvent(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Connect(
                                baseState
                            )
                        )
                    )
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                    tint = absoluteDark,
                    contentDescription = ""
                )
            }
            Text(
                color = absoluteDark,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                text = stringResource(id = R.string.rules),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = modifier.height(24.dp))
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = baseBackground)
                .border(width = 1.dp, color = absoluteDark)
                .clickable(onClick = {
                    onEvent(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Connect(
                                baseState
                            )
                        )
                    )
                })
                .padding(vertical = 16.dp)
        ) {
            Text(
                modifier = modifier.align(alignment = Alignment.Center),
                color = absoluteDark,
                fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                text = stringResource(id = R.string.back),
                textAlign = TextAlign.Center
            )
        }
    }
}