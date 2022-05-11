package com.harrontech.vilniusobjectrecognizer.android.uiComponents

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.harrontech.vilniusobjectrecognizer.android.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ObjectListCard(item: ObjectListCardData,
                   onObjectInfoOpen: ((id: String) -> Unit)? = {},
                   onBugReportOpen: ((id: String) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = {
            if (onObjectInfoOpen != null) {
                onObjectInfoOpen(item.id)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier
            ) {
                Image(
                    painter = rememberImagePainter(item.url),
                    contentDescription = "Contact profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(84.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)) {
                    Text(text = item.title, style = typography.h6)
                    Text(text = item.type, style = typography.caption)
                }
            }

            if(onBugReportOpen != null) {
                Image(
                    painter = painterResource(R.drawable.warning),
                    contentDescription = "Contact profile picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .clickable { onBugReportOpen(item.id) }
                        .align(Alignment.TopEnd)
                )
            }

        }
    }
}

data class ObjectListCardData(
    val id: String,
    val title: String,
    val type: String,
    val url: String
)
