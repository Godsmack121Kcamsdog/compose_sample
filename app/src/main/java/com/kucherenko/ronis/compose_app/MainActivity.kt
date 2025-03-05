package com.kucherenko.ronis.compose_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kucherenko.ronis.compose_app.ui.navigation.Navigation
import com.kucherenko.ronis.compose_app.ui.screens.FriendsScreen
import com.kucherenko.ronis.compose_app.ui.screens.LoginScreen
import com.kucherenko.ronis.compose_app.ui.screens.MealsCategoriesScreen
import com.kucherenko.ronis.compose_app.ui.screens.UserDetailsScreen
import com.kucherenko.ronis.compose_app.ui.screens.UserInfoScreen
import com.kucherenko.ronis.compose_app.ui.theme.MyApplicationTheme
import com.kucherenko.ronis.compose_app.vm.FriendsViewModel
import com.kucherenko.ronis.compose_app.vm.LoginViewModel
import com.kucherenko.ronis.compose_app.vm.LoginViewModel.Event
import com.kucherenko.ronis.compose_app.vm.MealsCategoriesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val viewModel: LoginViewModel by viewModels()
    private val friendsViewModel: FriendsViewModel by viewModels()
    private val mealsCategoriesViewModel: MealsCategoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InitNavigation()
        }

        lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    Event.LoginSuccess -> {
                        navController.navigate(Navigation.USER_SCREEN)
                    }

                    is Event.LoginError -> {
                        showMessage(it.errorMsg)
                    }
                }
            }
        }
    }

    @Composable
    fun InitNavigation() {
        navController = rememberNavController()
        MyApplicationTheme {
            NavHost(
                navController = navController, startDestination = Navigation.LOGIN_SCREEN
            ) {
                composable(route = Navigation.LOGIN_SCREEN) {
                    LoginScreen(vm = viewModel)
                }
                composable(route = Navigation.USER_SCREEN) {
                    UserInfoScreen(vm = viewModel, navController = navController)
                }
                composable(route = Navigation.USER_DETAILS) {
                    UserDetailsScreen(
                        vm = friendsViewModel,
                        mealsVm = mealsCategoriesViewModel,
                        navController = navController
                    )
                }
                composable(route = Navigation.USER_FRIENDS) {
                    FriendsScreen(vm = friendsViewModel, navController = navController)
                }
                composable(route = Navigation.USER_MEALS) {
                    MealsCategoriesScreen(vm = mealsCategoriesViewModel)
                }
            }
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
