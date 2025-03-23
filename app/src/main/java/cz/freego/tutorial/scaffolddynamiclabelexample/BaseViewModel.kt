package cz.freego.tutorial.scaffolddynamiclabelexample

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// Generický ViewState
open class BaseViewState

// Generický Event
open class BaseEvent

// Generický base view model
open class BaseViewModel<VS : BaseViewState, E : BaseEvent>(initialState: VS) : ViewModel() {
    private val _viewState = mutableStateOf(initialState)
    val viewState: State<VS> get() = _viewState

    // Event flow pro veškeré události, které budou emitovány do UI
    private val _eventFlow = MutableSharedFlow<E>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Funkce pro posílání eventů
    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    // Metoda pro změnu stavu celého objektu / instance třídy BaseViewState. Pokud je náš ViewState
    // rozšířením BaseViewState a jedná se o konstrukt obsahující jen výčet jednotlivých
    // mutableStateOf hodnot (a jejich derivací), pak stačí měnit value každé z nich - to je dostatečné
    // pro rekompozici (a zároveň žádoucí, protože se překresluje jen konkrétní hodnota). Tato metoda
    // je užitečná jen v případě, kdy potřebujeme měnit vždy celý stav, což by byl případ např.
    // data class rozšiřující BaseViewState
    protected fun setState(newState: VS) {
        _viewState.value = newState
    }
}
