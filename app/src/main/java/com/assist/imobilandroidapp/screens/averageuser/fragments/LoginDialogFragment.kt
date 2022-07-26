package com.assist.imobilandroidapp.screens.averageuser.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.assist.imobilandroidapp.R

class LoginDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.you_need_to_have_account))
            .setPositiveButton(getString(R.string.understood)) { _,_ -> }
            .create()

    companion object {
        const val TAG = "LogInRequirementDialog"
    }
}