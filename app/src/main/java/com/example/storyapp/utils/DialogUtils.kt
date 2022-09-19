package com.example.storyapp.utils

import android.content.Context
import com.example.storyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showCustomAlertDialogTwoButton(
    context: Context,
    title: String? = null,
    message: String? = null,
    positive: String? = null,
    positiveListener: (() -> Unit)? = null,
    negative: String? = null,
    negativeListener: (() -> Unit)? = null,
    isCancelable: Boolean = false,
) {
    val dialog = MaterialAlertDialogBuilder(context)
        .setTitle(
            if (title.isNullOrBlank()) context.getString(R.string.label_information)
            else title
        )
        .setMessage(message.orEmpty())
        .setPositiveButton(
            if (positive.isNullOrBlank()) context.getString(R.string.action_yes)
            else positive
        ) { dialog, _ ->
            dialog.dismiss()
            positiveListener?.invoke()
        }
        .setNegativeButton(
            if (negative.isNullOrBlank()) context.getString(R.string.action_no)
            else positive
        ) { dialog, _ ->
            dialog.dismiss()
            negativeListener?.invoke()
        }
        .setCancelable(isCancelable)
    dialog.show()
}

fun showCustomAlertDialogOneButton(
    context: Context,
    title: String? = null,
    message: String? = null,
    positive: String? = null,
    positiveListener: (() -> Unit)? = null,
    isCancelable: Boolean = false,
) {
    val dialog = MaterialAlertDialogBuilder(context)
        .setTitle(
            if (title.isNullOrBlank()) context.getString(R.string.label_information)
            else title
        )
        .setMessage(message.orEmpty())
        .setPositiveButton(
            if (positive.isNullOrBlank()) context.getString(R.string.action_oke)
            else positive
        ) { dialog, _ ->
            dialog.dismiss()
            positiveListener?.invoke()
        }
        .setCancelable(isCancelable)
    dialog.show()
}
