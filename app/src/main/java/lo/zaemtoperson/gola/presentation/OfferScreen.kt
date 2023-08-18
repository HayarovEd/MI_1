package lo.zaemtoperson.gola.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.TextView
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.data.VALUE_ONE
import lo.zaemtoperson.gola.data.permissions
import lo.zaemtoperson.gola.data.permissions34
import lo.zaemtoperson.gola.domain.model.ElementOffer
import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.basedto.BaseState
import lo.zaemtoperson.gola.ui.theme.absoluteDark
import lo.zaemtoperson.gola.ui.theme.baseBackground
import lo.zaemtoperson.gola.ui.theme.black
import lo.zaemtoperson.gola.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferScreen(
    modifier: Modifier = Modifier,
    elementOffer: ElementOffer,
    baseState: BaseState,
    onEvent: (MainEvent) -> Unit,
    launcherMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    context: Context
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseBackground
                ),
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            onEvent(
                                MainEvent.OnChangeStatusApplication(
                                    StatusApplication.Connect(baseState)
                                )
                            )
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                                tint = black,
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = modifier.width(16.dp))
                        Text(
                            color = black,
                            fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            text = elementOffer.name
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar (
                containerColor = baseBackground
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .weight(1f)
                            .padding(end = 4.dp, bottom = 4.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = white)
                            .clickable(onClick = {
                                onEvent(
                                    MainEvent.OnChangeStatusApplication(
                                        StatusApplication.Connect(baseState)
                                    )
                                )
                            })
                            .padding(vertical = 14.dp)
                    ) {
                        Icon(
                            modifier = modifier.align(alignment = Alignment.Center),
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_24),
                            tint = absoluteDark,
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = modifier.width(9.dp))
                    Box(
                        modifier = modifier
                            .weight(3f)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = black)
                            .clickable(onClick = {
                                onEvent(MainEvent.OnGoToWeb(
                                    urlOffer = elementOffer.order,
                                    nameOffer = elementOffer.name
                                ))
                            })
                            .padding(vertical = 16.dp)
                    ) {
                        Text(
                            modifier = modifier.align(alignment = Alignment.Center),
                            color = white,
                            fontStyle = FontStyle(R.font.soyuz_grotesk_bold),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            text = elementOffer.nameButton,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = baseBackground)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    /*.background(color = white)*/,
                model = elementOffer.pathImage,
                contentScale = ContentScale.FillWidth,
                contentDescription = ""
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Rang(
                    rang = elementOffer.rang
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            AndroidView(
                factory = { context -> TextView(context) },
                update = {
                    it.text = HtmlCompat.fromHtml(elementOffer.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
            )
            Spacer(modifier = modifier.height(24.dp))
            RowData(
                title = stringResource(id = R.string.amount),
                content = elementOffer.amount
            )
            if (elementOffer.showPercent == VALUE_ONE) {
                Spacer(modifier = modifier.height(8.dp))
                RowData(
                    title = stringResource(id = R.string.bet),
                    content = elementOffer.bet
                )
            }
            if (elementOffer.showTerm == VALUE_ONE) {
                Spacer(modifier = modifier.height(8.dp))
                RowData(
                    title = stringResource(id = R.string.term),
                    content = elementOffer.term
                )
            }
            Spacer(modifier = modifier.height(13.dp))
            RowCard(
                showVisa = elementOffer.showVisa,
                showMaster = elementOffer.showMaster,
                showYandex = elementOffer.showYandex,
                showMir = elementOffer.showMir,
                showQivi = elementOffer.showQiwi,
                showCache = elementOffer.showCache
            )
        }
    }
}