package com.assist.imobilandroidapp.screens.listing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.FragmentListingScreenBinding


class ListingScreenFragment : Fragment() {

    private var _binding: FragmentListingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListingScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        initButtons()
        return view

    }

    private fun initButtons() {
        binding.ivMorePictureButton.setOnClickListener {
            Toast.makeText(
                this.requireActivity(),
                getString(R.string.placeholder),
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this.requireActivity(), ViewImagesActivity::class.java)
            this.startActivity(intent)
        }
        binding.btnShare.setOnClickListener {
            Toast.makeText(this.requireActivity(), getString(R.string.share), Toast.LENGTH_SHORT)
                .show()
        }
        binding.btnPurchase.setOnClickListener {
            Toast.makeText(this.requireActivity(), getString(R.string.purchase), Toast.LENGTH_SHORT)
                .show()
        }
        binding.btnHeart.setOnClickListener {
            Toast.makeText(
                this.requireActivity(),
                getString(R.string.favorites),
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnContactSeller.setOnClickListener {
            Toast.makeText(
                this.requireActivity(),
                getString(R.string.contact_seller),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}