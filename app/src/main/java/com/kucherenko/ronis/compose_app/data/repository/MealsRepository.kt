package com.kucherenko.ronis.compose_app.data.repository

import com.kucherenko.ronis.compose_app.data.models.api.MealsWebService
import com.kucherenko.ronis.compose_app.data.models.network.MealCategoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealsRepository(private val webService: MealsWebService = MealsWebService()) {

    suspend fun getMeals(): MealCategoriesResponse {
        return withContext(Dispatchers.IO) {
            webService.getMeals()
        }
    }

}