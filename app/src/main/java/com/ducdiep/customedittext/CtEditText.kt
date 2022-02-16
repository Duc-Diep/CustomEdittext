package com.ducdiep.customedittext

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_edittext.view.*

class CtEditText : FrameLayout {
    private var currentLength = 0
    private var maxSize = 20

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CtEditText)
        maxSize = typedArray.getInt(R.styleable.CtEditText_maxSize, 20)
        typedArray.recycle()
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.custom_edittext, this)
        tv_warning.text = "$currentLength/$maxSize"
        edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                var currentLength = s?.length
                if (currentLength!! > maxSize) {
                    tv_warning.setTextColor(Color.RED)
                    tv_warning.text = "Không được nhập quá $maxSize ký tự"
                } else {
                    tv_warning.setTextColor(Color.BLUE)
                    tv_warning.text = "$currentLength/$maxSize"
                }
            }

        })
    }


}