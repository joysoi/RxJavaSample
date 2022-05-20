package com.nikola.rxjavasample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nikola.rxjavasample.data.model.NotesModel
import com.nikola.rxjavasample.data.model.ResultWrapper
import com.nikola.rxjavasample.data.model.UiNotesList
import com.nikola.rxjavasample.data.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val TAG = "MainViewModelNotes"

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: MainRepo) : ViewModel() {
    private val notesModel = NotesModel()
    private val compositeDisposable = CompositeDisposable()

    private val _notesFlow: MutableStateFlow<ResultWrapper<UiNotesList>> =
        MutableStateFlow(ResultWrapper.Success(
        UiNotesList(emptyList())
    ))
    val notesFlow: StateFlow<ResultWrapper<UiNotesList>> get() = _notesFlow

    init {
        getNotesListFromRepo()
    }

    private fun getNotesListFromRepo() {
        compositeDisposable.add(
            repo.getNotesListFromApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    notesModel.parseNotesListFromApiToUiModel(it)!!
                }.subscribe(
                    { result ->
                        Log.d(TAG, result.notesList.toString())
                        _notesFlow.value = ResultWrapper.Success(result)
                    },
                    { throwable ->
                        Log.e(TAG, throwable.message ?: "onError")
                        _notesFlow.value = ResultWrapper.GenericError(throwable)
                    })
        )

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}