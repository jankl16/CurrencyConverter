package com.kaleiczyk.currencyconverter

import androidx.lifecycle.ViewModel
import com.kaleiczyk.domain.errorProvider.GlobalErrorProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    val globalErrorProvider: GlobalErrorProvider
) : ViewModel()
