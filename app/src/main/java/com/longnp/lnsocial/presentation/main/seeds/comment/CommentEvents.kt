package com.longnp.lnsocial.presentation.main.seeds.comment

sealed class CommentEvents {

    object GetComment: CommentEvents()

    object AddComment: CommentEvents()

    data class OnChangeComment(val comment: String): CommentEvents()
}