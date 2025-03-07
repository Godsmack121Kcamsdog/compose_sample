package com.kucherenko.ronis.compose_app.ui.screens.meals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kucherenko.ronis.compose_app.ui.navigation.Navigation
import com.kucherenko.ronis.compose_app.vm.MealsCategoriesViewModel

@Composable
fun MealsCategoriesScreen(vm: MealsCategoriesViewModel, navController: NavHostController?) {
    LaunchedEffect(key1 = "GET_MEALS") {
        vm.getMeals()
    }
    val mealsFromApi = vm.mealsFLow.collectAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(mealsFromApi.value) { meal ->
            MealsCategory(meal) {
                vm.selectMeal(meal)
                navController?.navigate(Navigation.MEAL_DETAILS)
            }
        }
    }
}