package com.example.timetonic.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetonic.R

@Composable
fun LoginScreen(
    navigateToLandingPage: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginForm(onClickLogin = navigateToLandingPage)
    }
}

@Composable
fun LoginForm(
    onClickLogin: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var isUsernameEmpty by rememberSaveable { mutableStateOf(false) }
    var isPasswordEmpty by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text(text = stringResource(id = R.string.user_label)) },
        supportingText = {
            if (isUsernameEmpty) {
                Text(text = stringResource(id = R.string.login_input_empty))
            }
        },
        isError = isUsernameEmpty
    )
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = stringResource(id = R.string.password_label)) },
        supportingText = {
            if (isPasswordEmpty) {
                Text(text = stringResource(id = R.string.login_input_empty))
            }
        },
        isError = isPasswordEmpty,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            if (username.isNotEmpty() && password.isNotEmpty()) {
                //Call the viewmodel.login
                isUsernameEmpty = false
                isPasswordEmpty = false
                onClickLogin()
            } else {
                isUsernameEmpty = username.isEmpty()
                isPasswordEmpty = password.isEmpty()
            }
        }
    ) {
        Text(text = "Login")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(navigateToLandingPage = {})
    }
}
