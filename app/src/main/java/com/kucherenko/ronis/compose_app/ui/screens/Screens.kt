package com.kucherenko.ronis.compose_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kucherenko.ronis.compose_app.ui.navigation.Navigation
import com.kucherenko.ronis.compose_app.ui.screens.items.AppBar
import com.kucherenko.ronis.compose_app.ui.screens.items.LoginPasswordFields
import com.kucherenko.ronis.compose_app.ui.screens.items.ProfileCard
import com.kucherenko.ronis.compose_app.ui.screens.items.ProfileContent
import com.kucherenko.ronis.compose_app.ui.screens.items.ProfilePicture
import com.kucherenko.ronis.compose_app.ui.theme.MyApplicationTheme
import com.kucherenko.ronis.compose_app.vm.FriendsViewModel
import com.kucherenko.ronis.compose_app.vm.LoginViewModel
import com.kucherenko.ronis.myapplication.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(vm: LoginViewModel) {
    val focusManager = LocalFocusManager.current
    Scaffold(topBar = { AppBar(title = "Users Application") }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoginPasswordFields(vm)
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    focusManager.clearFocus()
                    vm.validateForm()
                }) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserInfoScreen(
    vm: LoginViewModel,
    navController: NavHostController?
) {
    Scaffold(topBar = {
        AppBar(
            icon = Icons.Default.Home,
            title = "Users Application"
        )
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.default_avatar),
                    modifier = Modifier
                        .size(240.dp)
                        .padding(16.dp),
                    contentDescription = "user avatar"
                )
                Text(
                    text = "E-mail: ${vm.emailValue}",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    navController?.navigate(Navigation.USER_FRIENDS)
                }) {
                    Text(text = "Show Friends")
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FriendsScreen(vm: FriendsViewModel, navController: NavHostController?) {
    Scaffold(topBar = {
        AppBar(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            title = "Back",
            action = { navController?.navigateUp() }
        )
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            val friends by vm.usersFLow.collectAsStateWithLifecycle()
            LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                items(friends) { item ->
                    ProfileCard(item) {
                        vm.selectUser(item)
                        navController?.navigate(Navigation.USER_DETAILS)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserDetailsScreen(vm: FriendsViewModel, navController: NavHostController?) {
    val friend by vm.selectedUserFlow.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        AppBar(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            title = "Back",
            action = { navController?.navigateUp() }
        )
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                friend?.let {
                    ProfilePicture(it, 240.dp)
                    ProfileContent(it, alignment = Alignment.CenterHorizontally)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MyApplicationTheme {
        LoginScreen(vm = LoginViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    UserInfoScreen(LoginViewModel(), null)
}

@Preview
@Composable
fun FriendsPreview() {
    FriendsScreen(FriendsViewModel(), null)
}

@Preview
@Composable
fun DetailsScreen() {
    UserDetailsScreen(FriendsViewModel(), null)
}
