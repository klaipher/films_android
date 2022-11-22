package com.example.films.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object DialogFactory {
    fun createSimpleOkCancelDialog(
        context: Context?,
        message: String?,
        positive_text_btn: String?,
        negative_text_btn: String?,
        positiveButtonClickListener: DialogInterface.OnClickListener?,
        cancelButtonOnClickListener: DialogInterface.OnClickListener?
    ): Dialog {
        val alertDialog = MaterialAlertDialogBuilder(context!!)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positive_text_btn, positiveButtonClickListener)
            .setNegativeButton(negative_text_btn, cancelButtonOnClickListener)
        return alertDialog.create()
    }
}