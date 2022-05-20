package com.nikola.rxjavasample.data.repo

import com.nikola.rxjavasample.data.model.ApiNotesList
import com.nikola.rxjavasample.data.network.NotesApi
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject


class MainRepo @Inject constructor(private var api: NotesApi) {
    fun getNotesListFromApi(): Single<Response<ApiNotesList>> {
        return api.getNotesList()
    }
}