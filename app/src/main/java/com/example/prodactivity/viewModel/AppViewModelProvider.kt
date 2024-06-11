
package com.example.prodactivity.viewModel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.prodactivity.ProdActivityApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            GoalsViewModel(prodActivityApplication().container.goalsRepository)
        }
        initializer {
            StatsViewModel(prodActivityApplication().container.goalsRepository)
        }
    }
}

fun CreationExtras.prodActivityApplication(): ProdActivityApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ProdActivityApplication)
