package com.example.timetonic.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.timetonic.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTimeTonic(
    canNavigate: Boolean,
    onClickLogOut: () -> Unit,
    onClickUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.timetonic),
                contentDescription = "Top app bar image",
                modifier = Modifier.size(200.dp)
            )
        },
        actions = {
            IconButton(
                onClick = onClickLogOut
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.log_out),
                    contentDescription = "Log out icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        navigationIcon = {
            if (canNavigate) {
                IconButton(
                    onClick = onClickUp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Log out icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    )
}