package com.assist.imobilandroidapp.screens.averageuser.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.FragmentFavouritesEmptyBinding
import com.assist.imobilandroidapp.databinding.FragmentLatestBinding
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity

class FavouritesEmptyFragment : Fragment() {

    private var _binding: FragmentFavouritesEmptyBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogIn.setOnClickListener {
            val intent = Intent(activity, LogInActivity::class.java)
            this.startActivity(intent)
        }

        binding.btnHome.setOnClickListener {
            val startFragment = StartFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fc_fragments, startFragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }
}