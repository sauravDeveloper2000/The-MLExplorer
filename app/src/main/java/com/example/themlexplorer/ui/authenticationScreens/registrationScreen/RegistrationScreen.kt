package com.example.themlexplorer.ui.authenticationScreens.registrationScreen

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
import androidx.compose.material.icons.filled.Create
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

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    registrationScreenViewModel: RegistrationScreenViewModel = hiltViewModel(),
    loginAccount: () -> Unit
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var emailFieldError by remember {
        mutableStateOf(false)
    }
    var emailFieldErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var newPasswordError by remember {
        mutableStateOf(false)
    }
    var newPasswordErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var confirmPasswordError by remember {
        mutableStateOf(false)
    }
    var confirmPasswordErrorCause by remember {
        mutableStateOf<String?>(null)
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBarOfSignUpScreen(modifier = Modifier)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    /**
                     * Email-Id Validation check
                     */
                    if (registrationScreenViewModel.userEmail.isNotEmpty()){
                        if (Patterns.EMAIL_ADDRESS.matcher(registrationScreenViewModel.userEmail).matches()){
                            emailFieldError = false
                            emailFieldErrorCause = null
                        } else {
                            emailFieldError = true
                            emailFieldErrorCause = "Please enter valid email without spaces!"
                        }
                    } else {
                        emailFieldError = true
                        emailFieldErrorCause = "Please enter your email-id"
                    }

                    /**
                     * NewPassword and Confirm Password validation
                     */
                    if (registrationScreenViewModel.newPassword.isNotEmpty() && registrationScreenViewModel.confirmPassword.isNotEmpty()){
                        if ((registrationScreenViewModel.newPassword.length in 6..15) && (registrationScreenViewModel.confirmPassword.length in 6..15)){
                            if (registrationScreenViewModel.newPassword == registrationScreenViewModel.confirmPassword){
                                newPasswordError = false
                                confirmPasswordError = false

                                newPasswordErrorCause = null
                                confirmPasswordErrorCause = null
                            } else {
                                newPasswordError = true
                                confirmPasswordError = true

                                newPasswordErrorCause = "Passwords are not matching"
                                confirmPasswordErrorCause = "Passwords are not matching"
                            }
                        } else {
                            newPasswordError = registrationScreenViewModel.newPassword.length !in 6..15
                            newPasswordErrorCause = if (registrationScreenViewModel.newPassword.length !in 6..15) "Password Length should be in b|w 6-15 " else null

                            confirmPasswordError = registrationScreenViewModel.confirmPassword.length !in 6..15
                            confirmPasswordErrorCause = if (registrationScreenViewModel.confirmPassword.length !in 6..15) "confirm password length should be in b|w 6-15" else null
                        }
                    }else{
                        newPasswordError = registrationScreenViewModel.newPassword.isEmpty()
                        newPasswordErrorCause = if (registrationScreenViewModel.newPassword.isEmpty()) "Please enter your new password" else null

                        confirmPasswordError = registrationScreenViewModel.confirmPassword.isEmpty()
                        confirmPasswordErrorCause = if (registrationScreenViewModel.confirmPassword.isEmpty()) "Please enter your confirm password" else null
                    }
                  if (newPasswordError || confirmPasswordError || emailFieldError){
                      Toast.makeText(context, "Validation Failed!!", Toast.LENGTH_SHORT).show()
                      return@ExtendedFloatingActionButton
                  } else {
                      Toast.makeText(context, "Validation Succeeded :)", Toast.LENGTH_SHORT).show()
                  }
                }
            ) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
                HorizontalSpace(space = 2)
                Text(
                    text = "Create",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) {
        Card(
            modifier = modifier
                .padding(it)
                .padding(horizontal = 5.dp, vertical = 5.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
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
                    value = registrationScreenViewModel.userEmail,
                    onValueChange = { emailId ->
                        registrationScreenViewModel.updateStates(
                            UserActionOnRegistrationScreen.OnEmailIdClick(
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
                    isError = emailFieldError,
                    supportingText = {
                        emailFieldErrorCause?.let {
                            Text(text = it)
                        }
                    }
                )

                VerticalSpace(space = 5)

                /**
                 * New Password
                 */
                Text(
                    text = "New Password",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.newPassword,
                    onValueChange = { newPassword ->
                        registrationScreenViewModel.updateStates(
                            UserActionOnRegistrationScreen.OnNewPasswordClick(
                                newPassword = newPassword
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.new_password),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingIcon = {
                        val icon =
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = icon, contentDescription = "Password")
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    isError = newPasswordError,
                    supportingText = {
                        newPasswordErrorCause?.let {
                            Text(text = it)
                        }
                    }
                )

                VerticalSpace(space = 5)

                /**
                 * Confirm Password
                 */
                Text(
                    text = "Confirm Password",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.confirmPassword,
                    onValueChange = { confirmPassword ->
                        registrationScreenViewModel.updateStates(
                            event = UserActionOnRegistrationScreen.OnConfirmPasswordClick(
                                confirmPassword = confirmPassword
                            )
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.confirm_password),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingIcon = {
                        val icon =
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = icon, contentDescription = "Password")
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    isError = confirmPasswordError,
                    supportingText = {
                        confirmPasswordErrorCause?.let {
                            Text(text = it)
                        }
                    }
                )

                VerticalSpace(space = 10)

                /**
                 *  Login
                 *  If user have an account, then navigate to login screen when user clicks on it.
                 */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.have_an_account),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextButton(onClick = loginAccount) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

/**
 * TopAppBar of Registration Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarOfSignUpScreen(
    modifier: Modifier
) {
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
}