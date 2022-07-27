package com.assist.imobilandroidapp.screens.favorites

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.assist.imobilandroidapp.R

class FavoritesDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.you_need_to_have_account_favorites))
            .setPositiveButton(getString(R.string.understood)) { _,_ -> }
            .create()

    companion object {
        const val TAG = "LogInRequirementDialog"
    }
}