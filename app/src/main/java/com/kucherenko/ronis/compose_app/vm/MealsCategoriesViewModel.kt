package com.kucherenko.ronis.compose_app.vm

import androidx.lifecycle.ViewModel
import com.kucherenko.ronis.compose_app.data.models.network.MealResponse
import com.kucherenko.ronis.compose_app.data.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MealsCategoriesViewModel(private val repository: MealsRepository = MealsRepository()) :
    ViewModel() {

    private val _mealsFLow = MutableStateFlow<List<MealResponse>>(emptyList())
    val mealsFLow = _mealsFLow.asStateFlow()

    suspend fun getMeals() {
        val categories = repository.getMeals().categories
        _mealsFLow.emit(categories)
    }
}