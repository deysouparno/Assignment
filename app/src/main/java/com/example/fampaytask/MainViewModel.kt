package com.example.fampaytask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fampaytask.model.CardGroup
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    val uiState:MutableLiveData<UiState> = MutableLiveData(UiState.Idle)

    private var _cardGroups: MutableLiveData<List<CardGroup>> = MutableLiveData(emptyList())
    val cardGroups: LiveData<List<CardGroup>>
        get() = _cardGroups


    fun loadData(famApi: FamApi) {
        viewModelScope.launch {
            val res = famApi.getData()
            _cardGroups.postValue(res.body()!!.cardGroups)
        }
    }
}

enum class UiState {
    Idle,
    Loading,
    Success,
    Failure
}