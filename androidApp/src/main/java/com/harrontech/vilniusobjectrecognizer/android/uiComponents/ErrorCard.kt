package com.harrontech.vilniusobjectrecognizer.android.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.harrontech.vilniusobjectrecognizer.android.R

@Composable
@Preview
fun ErrorCard(text: String = "Test") {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.error,
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.warning),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                tint = Color.White
            )
            Text(text = text,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}