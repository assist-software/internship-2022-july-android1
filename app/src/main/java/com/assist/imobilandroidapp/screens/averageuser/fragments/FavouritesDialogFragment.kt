package com.assist.imobilandroidapp.screens.averageuser.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.assist.imobilandroidapp.R

class FavouritesDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.you_need_to_be_connected_to_access_favourites))
            .setPositiveButton(getString(R.string.understood)) { _, _ -> }
            .create()

    companion object {
        const val TAG = "LogInRequirementForFavDialog"
    }
}