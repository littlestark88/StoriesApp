package com.example.storyapp.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.storyapp.databinding.CustomDialogLoadingBinding

object LoadingUtils {
    private var dialog: AlertDialog? = null

    fun showLoading(context: Context) {
        val binding = CustomDialogLoadingBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun hideLoading() {
        dialog?.dismiss()
    }
}