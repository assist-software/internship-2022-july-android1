import android.content.Context
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import java.util.*

class Validator() {

    private lateinit var emailEditText: EditText
    private lateinit var emailErrorMsg: TextView
    private lateinit var passwdEditText: EditText
    private lateinit var passwdErrorMsg: TextView
    private lateinit var signUpBtn: AppCompatButton

    private fun getEmail(emailEditText: EditText) : String {
        return emailEditText.text.toString()
    }

    private fun getPasswd(passwdEditText: EditText) : String {
        return passwdEditText.text.toString()
    }

    fun validateEmail(emailEditText: EditText, emailErrorMsg: TextView) : Boolean {
        val email = getEmail(emailEditText)
        if(email.isEmpty()) {
            emailErrorMsg.setText(R.string.empty_field)
            emailErrorMsg.isVisible = true
            //emailEditText.setBackgroundColor(resources.getColor(R.color.red_500))
            return false
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailErrorMsg.setText(R.string.wrong_email)
            emailErrorMsg.isVisible = true
            //emailEditText.setBackgroundColor(resources.getColor(R.color.red_500))
            return false
        }
        else {
            emailErrorMsg.isVisible = false
            return true
        }
    }

    private fun hasDigits(string: String) : Boolean {

        for(char in string) {
            if(char.isDigit()) {
                return true
            }
        }

        return false
    }

    fun validatePasswd(passwdEditText: EditText, passwdErrorMsg: TextView) : Boolean {
        val passwd = getPasswd(passwdEditText)

        if(passwd.isEmpty()) {
            passwdErrorMsg.setText(R.string.empty_field)
            //passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            //passwdErrorMsg.setBackgroundColor(resources.getColor(R.color.red_500))
            return false
        }
        else if(passwd.length < 9){
            passwdErrorMsg.setText(R.string.insufficient_chars)
            //passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            //passwdErrorMsg.setBackgroundColor(resources.getColor(R.color.red_500))
            return false
        }
        else if(!hasDigits(passwd)) {
            passwdErrorMsg.setText(R.string.lacks_digit)
            //passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            //passwdErrorMsg.setBackgroundColor(resources.getColor(R.color.red_500))
            return false
        }
        else {
            passwdErrorMsg.setText(R.string.pwd_helper)
            //passwdErrorMsg.setTextColor(resources.getColor(R.color.gray_500))
            return true
        }
    }
}