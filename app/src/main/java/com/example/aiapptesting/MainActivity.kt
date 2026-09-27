package com.example.aiapptesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.aiapptesting.ui.theme.AIAppTestingTheme
import android.util.Patterns

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIAppTestingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var loginSucceeded by remember { mutableStateOf(false) }
    val requiredEmailError = stringResource(R.string.email_required_error)
    val invalidEmailError = stringResource(R.string.email_invalid_error)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.login_title))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
                loginSucceeded = false
            },
            label = { Text(stringResource(R.string.email_label)) },
            singleLine = true,
            isError = emailError != null,
            supportingText = emailError?.let { error -> { Text(error) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.widthIn(max = 400.dp)
        )
        Button(
            onClick = {
                val trimmedEmail = email.trim()
                emailError = when {
                    trimmedEmail.isEmpty() -> requiredEmailError
                    !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> invalidEmailError
                    else -> null
                }
                loginSucceeded = emailError == null
            }
        ) {
            Text(text = stringResource(R.string.login_button))
        }
        if (loginSucceeded) {
            Text(text = stringResource(R.string.login_success))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AIAppTestingTheme {
        LoginScreen()
    }
}