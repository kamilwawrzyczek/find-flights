package eu.wawrzyczek.findflights.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun View.visibility(visibility: Boolean) {
    this.visibility = if(visibility) View.VISIBLE else View.GONE
}
