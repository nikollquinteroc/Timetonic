package com.example.timetonic.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetonic.R
import com.example.timetonic.data.model.login.LoginResponse


@Composable
fun LoginScreen(
    navigateToLandingPage: (LoginResponse) -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.provideFactory())
) {
    val loginUiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            loginUiState.isLoading -> {
                CircularProgressIndicator()
            }

            loginUiState.loginResponse.o_u.isNotEmpty() && loginUiState.loginResponse.sesskey.isNotEmpty() -> {
                navigateToLandingPage(loginUiState.loginResponse)
            }

            else -> {
                LoginForm(
                    errorMessageFromServer = loginUiState.error,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}

@Composable
fun LoginForm(
    errorMessageFromServer: String,
    loginViewModel: LoginViewModel
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf(errorMessageFromServer) }

    var isUsernameEmpty by rememberSaveable { mutableStateOf(false) }
    var isPasswordEmpty by rememberSaveable { mutableStateOf(false) }

    Image(
        painter = painterResource(id = R.drawable.timetonic),
        contentDescription = "Company Logo",
        modifier = Modifier.size(200.dp)
    )
    Spacer(modifier = Modifier.height(30.dp))
    OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text(text = stringResource(id = R.string.user_label)) },
        supportingText = {
            if (isUsernameEmpty) {
                Text(text = stringResource(id = R.string.login_input_empty))
            }
        },
        isError = isUsernameEmpty,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
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
            errorMessage = ""

            if (username.isNotEmpty() && password.isNotEmpty()) {
                isUsernameEmpty = false
                isPasswordEmpty = false

                loginViewModel.login(username = username, password = password)
            } else {
                isUsernameEmpty = username.isEmpty()
                isPasswordEmpty = password.isEmpty()
            }
        }
    ) {
        Text(text = "Login")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = errorMessage.ifEmpty { "" },
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen({})
    }
}
