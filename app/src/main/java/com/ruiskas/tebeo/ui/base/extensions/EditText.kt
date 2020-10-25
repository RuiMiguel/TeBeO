package com.ruiskas.tebeo.ui.base.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun TextInputLayout.validate(validator: (String) -> Boolean, message: String) {
    this.editText?.let { editText ->
        if (validator(editText.text.toString())) {
            error = null
            isErrorEnabled = false
        } else {
            error = message
            isErrorEnabled = true
        }
    }
}