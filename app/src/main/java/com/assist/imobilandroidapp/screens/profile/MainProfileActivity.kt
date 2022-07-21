package com.assist.imobilandroidapp.screens.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityMainProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class MainProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainProfileBinding
    private var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initLogoutDialog()
        initDatePicker()
        initSingleLineEdit(
            binding.etEmailInput,
            binding.btnEmailSave,
            binding.tvEditEmail,
            binding.tvEmailPreview,
            binding.vLineEmail,
            binding.tvCancelEmail
        )
        initSingleLineEdit(
            binding.etPhoneInput,
            binding.btnPhoneSave,
            binding.tvEditPhone,
            binding.tvPhonePreview,
            binding.vLinePhone,
            binding.tvCancelPhone
        )
        initSingleLineEdit(
            binding.etAddressInput,
            binding.btnAddressSave,
            binding.tvEditAddress,
            binding.tvAddressPreview,
            binding.vLineAddress,
            binding.tvCancelAddress
        )
        initFullNameEdit(
            binding.clParentFullName,
            binding.etFirstNameInput,
            binding.etLastNameInput,
            binding.btnFullNameSave,
            binding.tvEditFullName,
            binding.tvCancelFullName,
            binding.tvFullNamePreview,
            binding.vLineFullName
        )
        initGenderEdit(
            binding.tvEditGender,
            binding.tvCancelGender,
            binding.tvGenderPreview,
            binding.rbMaleOption,
            binding.rbFemaleOption,
            binding.rgGenderOptions,
            binding.btnGenderSave,
            binding.vLineGender,
        )
    }

    private fun initGenderEdit(
        tvEditGender: TextView,
        tvCancelGender: TextView,
        tvGenderPreview: TextView,
        rbMaleOption: RadioButton,
        rbFemaleOption: RadioButton,
        rgGenderOptions: RadioGroup,
        btnGenderSave: Button,
        vLineGender: View
    ) {
        tvEditGender.setOnClickListener {
            if (tvGenderPreview.text.toString() == getString(R.string.gender_male)) {
                rbMaleOption.isChecked = true
            }
            if (tvGenderPreview.text.toString() == getString(R.string.gender_female)) {
                rbFemaleOption.isChecked = true
            }
            tvEditGender.isGone = true
            tvCancelGender.isVisible = true
            tvGenderPreview.isGone = true
            rgGenderOptions.isVisible = true
            btnGenderSave.isVisible = true
            vLineGender.isGone = true
        }
        tvCancelGender.setOnClickListener {
            tvEditGender.isVisible = true
            tvCancelGender.isGone = true
            tvGenderPreview.isVisible = true
            rgGenderOptions.isGone = true
            btnGenderSave.isGone = true
            vLineGender.isVisible = true
        }
        btnGenderSave.setOnClickListener {
            if (rbMaleOption.isChecked) {
                tvGenderPreview.text = getString(R.string.gender_male)
            }
            if (rbFemaleOption.isChecked) {
                tvGenderPreview.text = getString(R.string.gender_female)
            }
            tvEditGender.isVisible = true
            tvCancelGender.isGone = true
            tvGenderPreview.isVisible = true
            rgGenderOptions.isGone = true
            btnGenderSave.isGone = true
            vLineGender.isVisible = true
        }

    }

    private fun initFullNameEdit(
        clParentFullName: ConstraintLayout,
        etFirstNameInput: EditText,
        etLastNameInput: EditText,
        btnFullNameSave: Button,
        tvEditFullName: TextView,
        tvCancelFullName: TextView,
        tvFullNamePreview: TextView,
        vLineFullName: View
    ) {
        tvEditFullName.setOnClickListener {
            etLastNameInput.setText(
                tvFullNamePreview.text.toString().substring(
                    tvFullNamePreview.text.toString().lastIndexOf(getString(R.string.space)) + 1
                )
            )
            etFirstNameInput.setText(
                tvFullNamePreview.text.toString().substring(
                    0,
                    tvFullNamePreview.text.toString().lastIndexOf(getString(R.string.space))
                )
            )
            clParentFullName.isVisible = true
            tvEditFullName.isGone = true
            tvCancelFullName.isVisible = true
            vLineFullName.isGone = true
        }
        tvCancelFullName.setOnClickListener {
            clParentFullName.isGone = true
            tvEditFullName.isVisible = true
            tvCancelFullName.isGone = true
            vLineFullName.isVisible = true
        }
        btnFullNameSave.setOnClickListener {

            val fullNamePlaceholder =
                etFirstNameInput.text.toString() + getString(R.string.space) + etLastNameInput.text.toString()
            tvFullNamePreview.text =
                fullNamePlaceholder
            clParentFullName.isGone = true
            tvEditFullName.isVisible = true
            tvCancelFullName.isGone = true
            vLineFullName.isVisible = true
        }
    }

    private fun initSingleLineEdit(
        etInput: EditText,
        btnSave: Button,
        tvEdit: TextView,
        tvPreview: TextView,
        vLine: View,
        tvCancel: TextView
    ) {
        tvEdit.setOnClickListener {
            etInput.setText(tvPreview.text.toString())
            setupEditSingleLine(tvEdit, tvCancel, btnSave, etInput, vLine, tvPreview)
        }
        tvCancel.setOnClickListener {
            setupCancelSingleLine(tvEdit, tvCancel, btnSave, etInput, vLine, tvPreview)
        }
        btnSave.setOnClickListener {
            tvPreview.text = etInput.text.toString()
            setupCancelSingleLine(tvEdit, tvCancel, btnSave, etInput, vLine, tvPreview)
        }
    }

    private fun setupCancelSingleLine(
        tvEdit: TextView,
        tvCancel: TextView,
        btnSave: Button,
        etInput: EditText,
        vLine: View,
        tvPreview: TextView
    ) {
        tvEdit.isVisible = true
        tvCancel.isGone = true
        btnSave.isGone = true
        etInput.isGone = true
        vLine.isVisible = true
        tvPreview.isVisible = true
    }

    private fun setupEditSingleLine(
        tvEdit: TextView,
        tvCancel: TextView,
        btnSave: Button,
        etInput: EditText,
        vLine: View,
        tvPreview: TextView
    ) {
        tvEdit.isGone = true
        tvCancel.isVisible = true
        btnSave.isVisible = true
        etInput.isVisible = true
        vLine.isGone = true
        tvPreview.isGone = true
    }

    private fun initDatePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.tvBirthDatePreview.text = SimpleDateFormat(
                    getString(R.string.date_format),
                    Locale.UK
                ).format(calendar.time)

            }
        calendar.set(
            binding.tvBirthDatePreview.text.toString()
                .substring(
                    binding.tvBirthDatePreview.text.toString()
                        .lastIndexOf(getString(R.string.point)) + 1
                ).toInt(),
            binding.tvBirthDatePreview.text.toString()
                .substring(
                    binding.tvBirthDatePreview.text.toString()
                        .indexOf(getString(R.string.point)) + 1,
                    binding.tvBirthDatePreview.text.toString()
                        .lastIndexOf(getString(R.string.point))
                ).toInt() - 1,
            binding.tvBirthDatePreview.text.toString()
                .substring(
                    0,
                    binding.tvBirthDatePreview.text.toString().indexOf(getString(R.string.point))
                ).toInt()
        )
        binding.tvEditBirthDate.setOnClickListener {
            DatePickerDialog(
                this@MainProfileActivity, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun initLogoutDialog() {
        binding.llMenuLogout.setOnClickListener {
            val dialog = LogoutDialogFragment()
            dialog.show(supportFragmentManager, "")
        }
    }
}