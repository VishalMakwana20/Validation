import android.util.Patterns
import com.sarvadhi.validation.R
import java.util.regex.Pattern

/**
 * Validates user input. Usage:
 *
 * Validator.prepare()
 *  .checkEmail(email)
 *  .checkPassword(password)
 *  .checkAddress2(address2, false) // passing false indicates the field is optional
 *  .finish({
 *    // success
 *  },{
 *    view.showInputErrors(it)
 *  })
 *
 */
object Validator {
    enum class InputError(val stringId: Int) {
        EMAIL_WRONG(R.string.input_email_wrong),

        PASSWORD_LENGTH(R.string.input_password_length),
        PASSWORD_UPPERCASE(R.string.input_password_uppercase),
        PASSWORD_LOWERCASE(R.string.input_password_lowercase),
        PASSWORD_NUMBER(R.string.input_password_number),

        PHONE_LENGTH(R.string.input_phone_length),
        POSTAL_LENGTH(R.string.input_postal_length),
        USERNAME_LENGTH(R.string.username_length_invalid),
        EMPTY_EMAIL(R.string.email_empty),
        EMPTY_ZIP(R.string.zip_empty),
        EMPTY_USERNAME(R.string.username_empty),
        EMPTY_PASSWORD(R.string.pass_empty),
        EMPTY_PHONE(R.string.phone_empty)

    }

    private var workingErrorList: MutableList<InputError>? = null

    private val regexEmail = Patterns.EMAIL_ADDRESS

    private var MIN_LENGTH = 5
    private var MAX_LENGTH = 10

    /**
     * Validation starts here. Always call prepare() after calling retrieveErrorList().
     * Otherwise, a NullPointerException will be thrown.
     */
    fun prepare(): Validator {
        workingErrorList = mutableListOf()
        return this
    }

    private fun checkEmpty(str: String?): Boolean {
        if (str == null)
            return true
        else if (str.trim().isEmpty())
            return true
        return false
    }

    fun checkEmail(str: String?): Validator {
        if (checkEmpty(str)) workingErrorList!!.add(InputError.EMPTY_EMAIL)
        else if (!regexEmail.matcher(str!!.trim())
                .matches()
        ) workingErrorList!!.add(InputError.EMAIL_WRONG)
        return this
    }

    private val regexPasswordNumber = Pattern.compile(".*[0-9].*")
    private val regexPasswordUppercase = Pattern.compile(".*[A-Z].*")
    private val regexPasswordLowercase = Pattern.compile(".*[a-z].*")

    fun checkPassWord(str: String?): Validator {
        if (checkEmpty(str)) {
            workingErrorList!!.add(InputError.EMPTY_PASSWORD)
            return this
        }

        if (str!!.trim().length < 6) workingErrorList!!.add(InputError.PASSWORD_LENGTH)
        if (!regexPasswordNumber.matcher(str)
                .matches()
        ) workingErrorList!!.add(InputError.PASSWORD_NUMBER)
        if (!regexPasswordUppercase.matcher(str.trim())
                .matches()
        ) workingErrorList!!.add(InputError.PASSWORD_UPPERCASE)
        if (!regexPasswordLowercase.matcher(str.trim())
                .matches()
        ) workingErrorList!!.add(InputError.PASSWORD_LOWERCASE)
        return this
    }


    fun checkUserName(str: String?): Validator {
        if (checkEmpty(str)) workingErrorList!!.add(InputError.EMPTY_USERNAME)
        else if (str!!.trim().length > MAX_LENGTH || str.trim().length < MIN_LENGTH)
            workingErrorList!!.add(InputError.USERNAME_LENGTH)
        return this
    }

    fun checkPhoneNumber(str: String?): Validator {
        if (str.isNullOrEmpty()) workingErrorList!!.add(InputError.EMPTY_PHONE)
        else if (str.trim().length < 8) workingErrorList!!.add(InputError.PHONE_LENGTH)
        return this
    }


    fun checkZipCode(str: String?): Validator {
        if (checkEmpty(str)) workingErrorList!!.add(InputError.EMPTY_ZIP)
        else if (str!!.trim().length < 4 || str.trim().length > 10) workingErrorList!!.add(
            InputError.POSTAL_LENGTH
        )
        return this
    }

    fun updateMinMaxLength(minLength: Int, maxLength: Int): Validator {
        MIN_LENGTH = minLength
        MAX_LENGTH = maxLength
        return this
    }

    private fun retrieveErrorList(): List<InputError> {
        val errorList: MutableList<InputError> = workingErrorList ?: mutableListOf()
        workingErrorList = null
        return errorList
    }

    fun finish(onSuccess: () -> Unit, onError: (List<InputError>) -> Unit) {
        val errors = retrieveErrorList()
        if (errors.isEmpty()) onSuccess()
        else onError(errors)
    }


}
