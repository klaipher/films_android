package com.example.films.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.example.films.R
import com.example.films.databinding.LayoutLinearInfoBinding

class LinearInfoView constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    companion object {
        private const val DEFAULT_VALUE = "Not specified"
    }

    private val binding: LayoutLinearInfoBinding =
        LayoutLinearInfoBinding.inflate(LayoutInflater.from(context), this)

    val textViewTitle: TextView
        get() = binding.title
    val textViewValue: ReadMoreTextView
        get() = binding.value

    var title: String = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var value: String = ""
        set(value) {
            field = value
            binding.value.text = value
            if (hideIfValueEmpty)
                binding.root.isVisible = value.isNotEmpty() && value != DEFAULT_VALUE
        }

    private var hideIfValueEmpty: Boolean = false


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LinearInfoView,
            0,
            0
        ).apply {
            try {
                hideIfValueEmpty =
                    getBoolean(R.styleable.LinearInfoView_hideIfValueEmpty, false)
                title =
                    getString(R.styleable.LinearInfoView_title) ?: DEFAULT_VALUE
                value =
                    getString(R.styleable.LinearInfoView_value) ?: DEFAULT_VALUE

            } finally {
                recycle()
            }
        }
    }
}