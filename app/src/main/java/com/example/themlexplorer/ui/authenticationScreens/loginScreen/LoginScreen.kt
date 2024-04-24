package com.example.themlexplorer.ui.authenticationScreens.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.themlexplorer.R
import com.example.themlexplorer.components.HorizontalSpace
import com.example.themlexplorer.components.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
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
            ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
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
            modifier = modifier.padding(it),
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
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(id = R.string.email_id_info),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    VerticalSpace(space = 20)

                    /**
                     * Password
                     */
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )

                    VerticalSpace(space = 20)

                    /**
                     * If user don't have an account, then navigate them to registration screen
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
                        TextButton(onClick = { /*TODO*/ }) {
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