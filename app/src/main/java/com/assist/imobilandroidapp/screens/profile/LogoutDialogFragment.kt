package com.assist.imobilandroidapp.screens.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.assist.imobilandroidapp.databinding.FragmentLogoutDialogBinding
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity

class LogoutDialogFragment : DialogFragment() {
    private var _binding: FragmentLogoutDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogoutDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        initButtons()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    private fun initButtons() {
        binding.yesButton.setOnClickListener {
            val intent = Intent(this.requireActivity(), LogInActivity::class.java)
            this.startActivity(intent)
        }
        binding.noButton.setOnClickListener {
            dismiss()
        }
    }
}