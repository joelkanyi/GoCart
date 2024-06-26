/*
 * Copyright 2023 Eton Otieno
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.devbits.gocart.authentication.ui.login

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.devbits.gocart.authentication.R
import io.devbits.gocart.authentication.ui.AuthenticationViewModel
import io.devbits.gocart.designsystem.component.CountriesBottomSheet
import io.devbits.gocart.designsystem.component.CountryIcon
import io.devbits.gocart.designsystem.component.GCPasswordTextField
import io.devbits.gocart.designsystem.component.SuccessDialog
import io.devbits.gocart.designsystem.model.defaultCountries
import io.devbits.gocart.designsystem.theme.GoCartTheme
import java.util.regex.Pattern

@Composable
fun LoginRoute(
    onBack: () -> Unit,
    onLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = hiltViewModel(),
) {
    LoginScreen(
        modifier = modifier,
        onBack = onBack,
        onLogin = {
            viewModel.setAuthenticated(true)
            onLogin()
        },
        navigateToSignUp = navigateToSignUp,
        onForgotPassword = onForgotPassword,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    var showLoginDialog by remember { mutableStateOf(false) }

    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    var rememberMe by remember { mutableStateOf(false) }

    var showCountries by remember { mutableStateOf(false) }
    var country by remember { mutableStateOf(defaultCountries.first()) }

    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Welcome Back") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_back),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Proceed to login")

            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = !Pattern.matches(Patterns.PHONE.pattern(), it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Phone Number")
                },
                leadingIcon = {
                    CountryIcon(
                        country = country,
                        onClick = { showCountries = true },
                        modifier = Modifier.padding(start = 16.dp),
                    )
                },
                supportingText = {
                    if (phoneError) {
                        Text(text = "Invalid phone number, try again")
                    }
                },
                isError = phoneError,
                singleLine = true,
            )

            GCPasswordTextField(
                password = password,
                onValueChange = {
                    password = it
                    passwordError = it.length < 8
                },
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                errorText = "This password doesn't look right, try again",
                isError = passwordError,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = { rememberMe = !rememberMe }),
                ) {
                    Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })

                    Text(text = "Remember Me")
                }

                Text(
                    text = "Forgot Password",
                    modifier = Modifier.clickable(onClick = onForgotPassword),
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(text = "Don't have an account, ")

                val signUp = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ) {
                        append("Sign Up")
                    }
                }

                Text(
                    text = signUp,
                    modifier = Modifier.clickable { navigateToSignUp() },
                )
            }

            Button(
                onClick = { showLoginDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            ) {
                Text(text = "Login")
            }
        }
    }

    if (showLoginDialog) {
        SuccessDialog(
            text = "Login Successful",
            onConfirm = {
                showLoginDialog = false
                onLogin()
            },
        )
    }

    if (showCountries) {
        val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        CountriesBottomSheet(
            onDismiss = { showCountries = false },
            onCheck = { country = it },
            sheetState = sheetState,
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    GoCartTheme {
        LoginScreen(
            onBack = {},
            onLogin = {},
            navigateToSignUp = {},
            onForgotPassword = {},
        )
    }
}
