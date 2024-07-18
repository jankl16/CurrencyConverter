package com.kaleiczyk.domain.errorProvider

import com.kaleiczyk.domain.model.GlobalError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalErrorProvider @Inject constructor() {

    private val _globalError = MutableSharedFlow<GlobalError?>(1)
    val globalError = _globalError.asSharedFlow()

    suspend fun provideError(error: GlobalError) {
        _globalError.emit(error)
    }
}
