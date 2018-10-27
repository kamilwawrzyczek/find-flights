package eu.wawrzyczek.findflights.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setTextListener(listener: (String) -> Unit) {
    this.addTextChangedListener(object :TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            // empty
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // empty
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listener.invoke(p0?.toString() ?: "")
        }
    })
}