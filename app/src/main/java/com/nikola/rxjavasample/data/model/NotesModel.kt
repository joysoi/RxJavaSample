package com.nikola.rxjavasample.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.Response


data class ApiNotesList(
    @SerializedName("notes")
    val notesList: List<ApiNotesItem>
)

data class ApiNotesItem(
    @SerializedName("id")
    val noteId: Int,
    @SerializedName("title")
    val noteTitle: String,
    @SerializedName("note")
    val noteBody: String
)


data class UiNotesList(
    val notesList: List<UiNotesItem>
){
    override fun toString(): String {
        return "NoteList $notesList "
    }
}

data class UiNotesItem(
    val noteId: Int,
    val noteTitle: String,
    val noteBody: String
){
    override fun toString(): String {
        return "\n" +
                "Note title $noteTitle"
    }
}

sealed class ResultWrapper<out T>{
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val error: Throwable? = null): ResultWrapper<Nothing>()
}

class NotesModel {

    companion object{
        const val BASE_URL = "http://10.0.2.2.2:3000/"
    }

    fun parseNotesListFromApiToUiModel(apiNotesList: Response<ApiNotesList>): UiNotesList? {
        return if (apiNotesList.isSuccessful){
            val body = apiNotesList.body()
            body?.let { response->
                UiNotesList(
                    notesList = List(response.notesList.size){
                        UiNotesItem(
                            noteId = response.notesList[it].noteId,
                            noteTitle = response.notesList[it].noteTitle,
                            noteBody = response.notesList[it].noteBody
                        )
                    }
                )
            }
        }else{
            UiNotesList(notesList = emptyList())
        }

    }
}