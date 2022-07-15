package com.assist.imobilandroidapp.validator

import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R

class Validator(editText: EditText, errorMsg: TextView, context: Context, resource: Resources) {

    private var editText: EditText
    private var errorMsg: TextView
    private var context: Context
    private var resource: Resources

    init {
        this.editText = editText
        this.errorMsg = errorMsg
        this.context = context
        this.resource = resource
    }

    private fun getText(): String {
        return editText.text.toString()
    }

    private fun getPadding(): Int {
        return editText.paddingLeft
    }

    private fun editMessageTextViewsEmail(msg: Int, bool: Boolean, border: Int, padding: Int) {
        errorMsg.setText(msg)
        errorMsg.isVisible = bool
        editText.background = ContextCompat.getDrawable(context, border)
        editText.setPadding(padding, padding, padding, padding)
    }

    private fun editMessageTextViewsPassword(msg: Int, color: Int, border: Int, padding: Int) {
        errorMsg.setText(msg)
        errorMsg.setTextColor(resource.getColor(color))
        editText.background = ContextCompat.getDrawable(context, border)
        editText.setPadding(padding, padding, padding, padding)
    }

    private fun hasDigits(string: String): Boolean {
        for (char in string) {
            if (char.isDigit()) {
                return true
            }
        }
        return false
    }

    fun validateEmail(): Boolean {
        val email = getText()
        val padding = getPadding()
        editText.setPadding(padding, padding, padding, padding)

        when {
            email.isEmpty() -> {
                editMessageTextViewsEmail(
                    R.string.empty_field,
                    true, R.drawable.input_border_red, padding
                )
                return false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                editMessageTextViewsEmail(
                    R.string.wrong_email, true, R.drawable.input_border_red, padding
                )
                return false
            }

            else -> {
                editMessageTextViewsEmail(
                    R.string.wrong_email, false, R.drawable.input_border_normal, padding
                )
                return true
            }
        }
    }

    fun validatePassword(): Boolean {
        val passwd = getText()
        val padding = getPadding()
        editText.setPadding(padding, padding, padding, padding)

        when {
            passwd.isEmpty() -> {
                editMessageTextViewsPassword(
                    R.string.empty_field, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            passwd.length < 8 -> {
                editMessageTextViewsPassword(
                    R.string.insufficient_chars,
                    R.color.red_500,
                    R.drawable.input_border_red,
                    padding
                )
                return false
            }

            !hasDigits(passwd) -> {
                editMessageTextViewsPassword(
                    R.string.lacks_digit, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            else -> {
                editMessageTextViewsPassword(
                    R.string.pwd_helper, R.color.gray_500, R.drawable.input_border_normal, padding
                )
                return true
            }
        }
    }

    fun validateConfirmPassword(newPassword: String): Boolean {
        val confirmPassword = getText()
        val padding = getPadding()

        if (newPassword.isEmpty()) {
            return false;
        } else {
            when {
                !confirmPassword.equals(newPassword) -> {
                    editMessageTextViewsPassword(
                        R.string.no_match_passwd,
                        R.color.red_500,
                        R.drawable.input_border_red,
                        padding
                    )
                    errorMsg.isVisible = true
                    return false;
                }

                confirmPassword.isEmpty() -> {
                    editMessageTextViewsPassword(
                        R.string.empty_field,
                        R.color.red_500,
                        R.drawable.input_border_red,
                        padding
                    )
                    errorMsg.isVisible = true
                    return false;
                }

                else -> {
                    errorMsg.isGone = true
                    editText.background =
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.input_border_normal
                        )
                    editText.setPadding(
                        padding,
                        padding,
                        padding,
                        padding
                    )
                    return true
                }
            }
        }
    }

    fun validateLogInPassword(): Boolean {
        val passwd = getText()
        val padding = getPadding()
        editText.setPadding(padding, padding, padding, padding)

        when {
            passwd.isEmpty() -> {
                editMessageTextViewsPassword(
                    R.string.empty_field, R.color.red_500, R.drawable.input_border_red, padding
                )
                errorMsg.isVisible = true
                return false
            }

            passwd.length < 8 -> {
                editMessageTextViewsPassword(
                    R.string.insufficient_chars,
                    R.color.red_500,
                    R.drawable.input_border_red,
                    padding
                )
                errorMsg.isVisible = true
                return false
            }

            !hasDigits(passwd) -> {
                editMessageTextViewsPassword(
                    R.string.lacks_digit, R.color.red_500, R.drawable.input_border_red, padding
                )
                errorMsg.isVisible = true
                return false
            }

            else -> {
                editMessageTextViewsPassword(
                    R.string.pwd_helper, R.color.gray_500, R.drawable.input_border_normal, padding
                )
                errorMsg.isVisible = false
                return true
            }
        }
    }
}