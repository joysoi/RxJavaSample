package com.nikola.rxjavasample.data.network

import com.nikola.rxjavasample.data.model.ApiNotesList
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject


class NotesApi @Inject constructor(private val apiService: NotesApiService): ApiHelper{
    override fun getNotesList(): Single<Response<ApiNotesList>> {
       return apiService.getNotesList()
    }
}

interface NotesApiService {
    @GET("api/notes")
    fun getNotesList(): Single<Response<ApiNotesList>>
}

interface ApiHelper{
    fun getNotesList(): Single<Response<ApiNotesList>>
}