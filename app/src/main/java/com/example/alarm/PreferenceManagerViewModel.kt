package com.example.alarm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PreferenceManagerViewModel @Inject constructor(
    private val preferenceManager: IPreferenceManager
) : ViewModel() {
    private val _isAlarmEnabled = MutableStateFlow(preferenceManager.isAlarmEnabled())
    val isAlarmEnabled: StateFlow<Boolean> get() = _isAlarmEnabled.asStateFlow()

    private val _isDialogShown = MutableStateFlow(preferenceManager.isDialogShown())
    val isDialogShown: StateFlow<Boolean> get() = _isDialogShown.asStateFlow()

    fun setAlarmEnabled(isEnabled: Boolean) {
        preferenceManager.setAlarmEnabled(isEnabled)
        _isAlarmEnabled.value = isEnabled
    }

    fun setDialogShown(isShown: Boolean) {
        preferenceManager.setDialogShown(isShown)
        _isDialogShown.value = isShown
    }


}