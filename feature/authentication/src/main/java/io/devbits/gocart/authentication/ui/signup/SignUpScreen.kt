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
package io.devbits.gocart.authentication.ui.signup

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.util.PatternsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.devbits.gocart.authentication.R
import io.devbits.gocart.authentication.ui.AuthenticationViewModel
import io.devbits.gocart.authentication.ui.HaveAccountText
import io.devbits.gocart.designsystem.theme.GoCartTheme
import io.devbits.gocart.resources.R as ResourcesR
import java.util.regex.Pattern

@Composable
fun SignUpScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthenticationViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpScreen(
        modifier = modifier,
        state = state,
        onLogin = onLogin,
        onSignUp = {
            viewModel.setAuthenticated(true)
            onSignUp()
        },
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SignUpScreen(
    state: String,
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
//    val kc = LocalSoftwareKeyboardController.current

    var fullName by remember { mutableStateOf("") }
    var fullNameError by remember { mutableStateOf(false) }

    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var agreesToTerms by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Create an Account") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
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
                .imeNestedScroll()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    val name = fullName.split(" ")
                    fullNameError = name.size <= 1
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Kevin Kaur")
                },
                label = {
                    Text(text = "Full Name")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.PersonOutline, null)
                },
                supportingText = {
                    if (fullNameError) {
                        Text(text = "Enter your full name")
                    }
                },
                isError = fullNameError,
                singleLine = true,
            )

            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                    phoneError = !Pattern.matches(Patterns.PHONE.pattern(), it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Phone Number")
                },
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp),
                    ) {
                        Image(
                            painter = painterResource(id = ResourcesR.drawable.ic_outlined_kenyaflag),
                            contentDescription = null,
                        )
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                singleLine = true,
                supportingText = {
                    if (phoneError) {
                        Text(text = "Invalid phone number, try again")
                    }
                },
                isError = phoneError,
            )

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !Pattern.matches(PatternsCompat.EMAIL_ADDRESS.pattern(), it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "kevinkaur60@gmail.com")
                },
                label = {
                    Text(text = "Email")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.MailOutline, null)
                },
                supportingText = {
                    if (emailError) {
                        Text(text = "Email does not exist, try a valid email")
                    }
                },
                isError = emailError,
                singleLine = true,
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = it.length < 8
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
                placeholder = {
                    Text(text = "Enter your password")
                },
                label = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null)
                },
                supportingText = {
                    if (passwordError) {
                        Text(text = "This password doesn't look right, try again")
                    }
                },
                isError = passwordError,
                singleLine = true,
            )

            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = password != confirmPassword
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Confirm your password")
                },
                label = {
                    Text(text = "Confirm Password")
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null)
                },
                supportingText = {
                    if (confirmPasswordError) {
                        Text(text = "Passwords do not match")
                    }
                },
                isError = confirmPasswordError,
                singleLine = true,
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreesToTerms, onCheckedChange = { agreesToTerms = it })

                Text(text = "I agree to the Terms and Conditions")
            }

            HaveAccountText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onLogin = onLogin,
                textColor = MaterialTheme.colorScheme.onSurface,
                loginStyle = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                ),
            )

            Button(
                onClick = onSignUp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            ) {
                Text(text = "SIGN UP")
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    GoCartTheme {
        SignUpScreen(
            state = "",
            onLogin = {},
            onSignUp = {},
            onBack = {},
        )
    }
}
