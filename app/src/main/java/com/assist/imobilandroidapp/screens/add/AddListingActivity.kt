package com.assist.imobilandroidapp.screens.add

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.databinding.ActivityAddListingBinding
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.assist.imobilandroidapp.utils.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddListingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddListingBinding

    private var listingTitle: String = ""
    private var listingCategory: Int = 0
    private var listingPrice: Double = 0.0
    private var listingImages: ArrayList<String> = ArrayList()
    private var listingDescription: String = ""
    private var listingLocation: String = ""
    private var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
        onPublishBtnClick()
    }

    private fun allFieldsValidated(): Boolean {
        return !(!validateTitle() or !validateLocation() or !validatePrice() or !validateDescription() or !validatePhoneNumber())
    }

    private fun onPublishBtnClick() {
        binding.btnPublish.setOnClickListener {
            if (allFieldsValidated()) {
                RetrofitClient.instance.addListing(
                    PostListing(
                        binding.etListingTitle.text.toString(),
                        binding.etListingDescription.text.toString(),
                        binding.etListingLocation.text.toString(),
                        binding.etListingPrice.text.toString().toDouble(),
                        listingImages,
                        listingCategory,
                        SharedPrefManager.getInstance().fetchUserID().toString(),
                        true,
                        binding.etPhoneNumber.text.toString()
                    ), SharedPrefManager.getInstance().fetchToken()
                ).enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        when(response.code()) {
                            200, 201 -> {
                                Toast.makeText(applicationContext,
                                    getText(R.string.success_create_listing).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {
                                Toast.makeText(applicationContext,
                                    getText(R.string.something_wrong).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(applicationContext,
                            getText(R.string.something_wrong).toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            } else {
                Toast.makeText(
                    applicationContext,
                    getText(R.string.something_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun validateTitle(): Boolean {
        val validate =
            Validator(binding.etListingTitle, binding.tvTitleError, applicationContext, resources)
        return validate.validateNewListingFields()
    }

    private fun validatePrice(): Boolean {
        val priceString = binding.etListingPrice.text.toString()
        val padding = binding.etListingPrice.paddingLeft

        if (priceString.isEmpty()) {
            binding.tvPriceError.isVisible = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return false
        } else if (priceString[0] == '0') {
            binding.tvPriceError.isVisible = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return false
        } else {
            binding.tvPriceError.isGone = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return true
        }
    }

    private fun validateDescription(): Boolean {
        val validate = Validator(
            binding.etListingDescription,
            binding.tvDescriptionMessage,
            applicationContext,
            resources
        )
        return validate.validateListingDescription()
    }

    private fun validateLocation(): Boolean {
        val validate = Validator(
            binding.etListingLocation,
            binding.tvLocationError,
            applicationContext,
            resources
        )
        return validate.validateNewListingFields()
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val padding = binding.etPhoneNumber.paddingLeft

        if (phoneNumber.isEmpty() or (phoneNumber.length != 10)) {
            binding.tvPhoneNumberError.isVisible = true
            binding.etPhoneNumber.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etPhoneNumber.setPadding(padding, padding, padding, padding)
            return false
        } else {
            for (char in phoneNumber) {
                if (!char.isDigit()) {
                    return false
                }
            }
            binding.tvPhoneNumberError.isGone = true
            binding.etPhoneNumber.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            binding.etPhoneNumber.setPadding(padding, padding, padding, padding)
            return true
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val padding = binding.rlSpinner.paddingLeft

        when (pos) {
            0 -> listingCategory = 0
            1 -> listingCategory = 1
        }

        binding.rlSpinner.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
        binding.tvCategoryError.isGone = true
        binding.rlSpinner.setPadding(padding, padding, padding, padding)

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        val padding = binding.rlSpinner.paddingLeft

        binding.rlSpinner.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
        binding.tvCategoryError.isVisible = true
        binding.rlSpinner.setPadding(padding, padding, padding, padding)
    }
}