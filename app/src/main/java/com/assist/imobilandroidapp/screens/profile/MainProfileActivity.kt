package com.assist.imobilandroidapp.screens.profile
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ModifiUserData
import com.assist.imobilandroidapp.apiinterface.models.SpecificUser
import com.assist.imobilandroidapp.databinding.ActivityMainProfileBinding
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
class MainProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainProfileBinding
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var specificUser: SpecificUser
    private var profileImg = ""
    private var role = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupGetUserById()
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
        initFullNameEdit()
        initGenderEdit()
        initEditPhotoButton()
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        onMessagesBtnClick()
        onMessagesMenuBtnClick()
    }
    private fun onMessagesMenuBtnClick() {
        binding.llMenuMessages.setOnClickListener {
            intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
    }
    private fun onMessagesBtnClick() {
        binding.fabSendMessage.setOnClickListener {
            intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initEditPhotoButton() {
        binding.ivEditPhoto.setOnClickListener() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            profileImg = java.util.Base64.getEncoder().encodeToString(bytes)
            Glide.with(applicationContext).load(uri).circleCrop()
                .error(R.drawable.photo_replacement_1).into(binding.ivBigProfilePicture)
        }
    }
    private fun setupGetUserById() {
        val id = SharedPrefManager.getInstance().fetchUserId()
        val token = SharedPrefManager.getInstance().fetchToken()
        RetrofitClient.instance.getUserById(id, token)
            .enqueue(object :
                Callback<SpecificUser> {
                override fun onResponse(
                    call: Call<SpecificUser>,
                    response: Response<SpecificUser>
                ) {
                    if (response.code() == 200) {
                        specificUser = response.body()!!
                        binding.tvEmailPreview.text = specificUser.email
                        binding.tvFullNamePreview.text = specificUser.fullName
                        binding.tvPhonePreview.text = specificUser.phone
                        binding.tvAddressPreview.text = specificUser.address
                        role = specificUser.role.toString()
                        specificUser.photo?.let {
                            SharedPrefManager.getInstance().saveImageProfilePic(
                                it
                            )
                        }
                        Glide.with(applicationContext).load(specificUser.photo).circleCrop().override(128, 128)
                            .error(R.drawable.profile_icon).into(binding.ivBigProfilePicture)
                        if (specificUser.gender == 0) {
                            binding.tvGenderPreview.text = getString(R.string.gender_male)
                        } else {
                            binding.tvGenderPreview.text = getString(R.string.gender_female)
                        }
                        formatBirthDateForTV(specificUser.dateOfBirth)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.error_code) + response.code().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: Call<SpecificUser>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
    private fun formatBirthDateForTV(dateOfBirth: String?) {
        if (dateOfBirth != null) {
            val formattedBirthDate = dateOfBirth.substring(
                dateOfBirth.lastIndexOf("-") + 1,
                dateOfBirth.lastIndexOf("-") + 3
            ) + "." + dateOfBirth.substring(
                dateOfBirth.indexOf("-") + 1,
                dateOfBirth.indexOf("-") + 3
            ) + "." + dateOfBirth.substring(
                0, 4
            )
            binding.tvBirthDatePreview.text = formattedBirthDate
        } else {
            binding.tvBirthDatePreview.text = ""
        }
    }
    private fun setupModifiUserData() {
        var gender = 0
        if (binding.tvGenderPreview.text.toString() == getString(R.string.gender_female)) {
            gender = 1
        }
        RetrofitClient.instance.putModifiUserData(
            ModifiUserData(
                SharedPrefManager.getInstance().fetchUserId(),
                binding.tvFullNamePreview.text.toString(),
                binding.tvEmailPreview.text.toString(),
                binding.tvPhonePreview.text.toString(),
                roleCheck(),
                gender,
                profileImg,
                formatBirthDateForDataBase(binding.tvBirthDatePreview.text.toString()),
                binding.tvAddressPreview.text.toString(),
                true
            ), SharedPrefManager.getInstance().fetchToken()
        ).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.success),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_code) + response.code().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.failed),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
    private fun roleCheck(): Int {
        if (role == "User") {
            return 2
        } else if (role == "Validator") {
            return 1
        } else {
            return 0
        }
    }
    private fun formatBirthDateForDataBase(birthDate: String): String? {
        return birthDate.substring(
            birthDate.lastIndexOf(".") + 1,
            birthDate.lastIndexOf(".") + 5
        ) + "-" + birthDate.substring(
            birthDate.indexOf(".") + 1,
            birthDate.indexOf(".") + 3
        ) + "-" + birthDate.substring(
            0,
            birthDate.indexOf(".")
        )
    }
    private fun initGenderEdit() {
        binding.tvEditGender.setOnClickListener {
            if (binding.tvGenderPreview.text.toString() == getString(R.string.gender_male)) {
                binding.rbMaleOption.isChecked = true
            }
            if (binding.tvGenderPreview.text.toString() == getString(R.string.gender_female)) {
                binding.rbFemaleOption.isChecked = true
            }
            binding.tvEditGender.isGone = true
            binding.tvCancelGender.isVisible = true
            binding.tvGenderPreview.isGone = true
            binding.rgGenderOptions.isVisible = true
            binding.btnGenderSave.isVisible = true
            binding.vLineGender.isGone = true
        }
        binding.tvCancelGender.setOnClickListener {
            binding.tvEditGender.isVisible = true
            binding.tvCancelGender.isGone = true
            binding.tvGenderPreview.isVisible = true
            binding.rgGenderOptions.isGone = true
            binding.btnGenderSave.isGone = true
            binding.vLineGender.isVisible = true
        }
        binding.btnGenderSave.setOnClickListener {
            if (binding.rbMaleOption.isChecked) {
                binding.tvGenderPreview.text = getString(R.string.gender_male)
            }
            if (binding.rbFemaleOption.isChecked) {
                binding.tvGenderPreview.text = getString(R.string.gender_female)
            }
            binding.tvEditGender.isVisible = true
            binding.tvCancelGender.isGone = true
            binding.tvGenderPreview.isVisible = true
            binding.rgGenderOptions.isGone = true
            binding.btnGenderSave.isGone = true
            binding.vLineGender.isVisible = true
            setupModifiUserData()
        }
    }
    private fun initFullNameEdit() {
        binding.tvEditFullName.setOnClickListener {
            if (binding.tvFullNamePreview.text != "") {
                binding.etLastNameInput.setText(
                    binding.tvFullNamePreview.text.toString().substring(
                        binding.tvFullNamePreview.text.toString()
                            .lastIndexOf(getString(R.string.space)) + 1
                    )
                )
                binding.etFirstNameInput.setText(
                    binding.tvFullNamePreview.text.toString().substring(
                        0,
                        binding.tvFullNamePreview.text.toString()
                            .lastIndexOf(getString(R.string.space))
                    )
                )
            } else {
                binding.etLastNameInput.setText("")
                binding.etFirstNameInput.setText("")
            }
            binding.clParentFullName.isVisible = true
            binding.tvEditFullName.isGone = true
            binding.tvCancelFullName.isVisible = true
            binding.vLineFullName.isGone = true
        }
        binding.tvCancelFullName.setOnClickListener {
            binding.clParentFullName.isGone = true
            binding.tvEditFullName.isVisible = true
            binding.tvCancelFullName.isGone = true
            binding.vLineFullName.isVisible = true
        }
        binding.btnFullNameSave.setOnClickListener {
            val fullNamePlaceholder =
                binding.etFirstNameInput.text.toString() + getString(R.string.space) + binding.etLastNameInput.text.toString()
            binding.tvFullNamePreview.text =
                fullNamePlaceholder
            binding.clParentFullName.isGone = true
            binding.tvEditFullName.isVisible = true
            binding.tvCancelFullName.isGone = true
            binding.vLineFullName.isVisible = true
            setupModifiUserData()
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
            setupModifiUserData()
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
            setupModifiUserData()
        }
    }
    private fun initLogoutDialog() {
        binding.llMenuLogout.setOnClickListener {
            val dialog = LogoutDialogFragment()
            dialog.show(supportFragmentManager, "")
        }
    }
}