package com.example.themlexplorer.ui.authenticationScreens.loginScreen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themlexplorer.R
import com.example.themlexplorer.components.HorizontalSpace
import com.example.themlexplorer.components.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    createAccount: () -> Unit
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var emailIdError by remember {
        mutableStateOf(false)
    }
    var emailIdErrorCause by remember {
        mutableStateOf<String?>(null)
    }
    
    var passwordError by remember {
        mutableStateOf(false)
    }
    var passwordErrorCause by remember {
        mutableStateOf<String?>(null)
    }
    val context  = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    /**
                     * Email-Id Validation
                     */
                    if (loginScreenViewModel.userEmail.isNotEmpty()){
                        if (Patterns.EMAIL_ADDRESS.matcher(loginScreenViewModel.userEmail).matches()){
                            emailIdError = false
                            emailIdErrorCause = null
                        } else {
                            emailIdError = true
                            emailIdErrorCause = "Please enter valid email-id without spaces" 
                        }
                    } else {
                        emailIdError = true
                        emailIdErrorCause = "Please enter email-id"
                    }

                    /**
                     * Password Field Validation
                     */
                    if (loginScreenViewModel.password.isNotEmpty()){
                        if (loginScreenViewModel.password.length in  6..15){
                            passwordError = false
                            passwordErrorCause = null
                        } else {
                            passwordError = true
                            passwordErrorCause = "Password Lenght should be in b|w 6-15"
                        }
                    } else {
                        passwordError = true
                        passwordErrorCause = "Please enter the password"
                    }
                    
                    if (emailIdError || passwordError){
                        Toast.makeText(context, "Validation Failed :(", Toast.LENGTH_SHORT).show()
                        return@ExtendedFloatingActionButton
                    } else {
                        loginScreenViewModel.signInUser(
                            inSuccess = {
                                loginScreenViewModel.resetStates()
                                Toast.makeText(context, "Wowwwww", Toast.LENGTH_SHORT).show()
                            },
                            inFailure = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Create"
                )
                HorizontalSpace(space = 2)
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter your details",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    VerticalSpace(space = 10)

                    /**
                     * Email-Id
                     */
                    Text(
                        text = "Email-Id",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = loginScreenViewModel.userEmail,
                        onValueChange = { emailId ->
                            loginScreenViewModel.updateStates(
                                UserActionOnLoginScreen.OnEmailIdClick(
                                    emailId = emailId
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.email_id_info),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        isError = emailIdError,
                        supportingText = {
                            emailIdErrorCause?.let { 
                                Text(text = it)
                            }
                        }
                    )

                    VerticalSpace(space = 5)

                    /**
                     * Password
                     */
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = loginScreenViewModel.password,
                        onValueChange = { password ->
                            loginScreenViewModel.updateStates(
                                UserActionOnLoginScreen.OnPasswordClick(
                                    password = password
                                )
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        trailingIcon = {
                            val icon =
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(imageVector = icon, contentDescription = "Password")
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        isError = passwordError,
                        supportingText = {
                            passwordErrorCause?.let { 
                                Text(text = it)
                            }
                        }
                    )

                    VerticalSpace(space = 20)

                    /**
                     *  Create
                     *  If user don't have an account, then navigate them to registration screen
                     */
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_account),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        TextButton(onClick = createAccount) {
                            Text(
                                text = "Create",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}