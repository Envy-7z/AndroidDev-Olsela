package com.wisnu.tesandroiddev_orsela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wisnu.tesandroiddev_orsela.data.repo.OrselaRepo
import com.wisnu.tesandroiddev_orsela.utils.UtilFunctions.loge

class OrselaViewModelFactory(
    private val repository: OrselaRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(OrselaRepo::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            loge(e.message.toString())
        }
        return super.create(modelClass)
    }
}