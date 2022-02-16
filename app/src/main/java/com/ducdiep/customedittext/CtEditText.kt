package com.ducdiep.customedittext

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_edittext.view.*

class CtEditText : FrameLayout {
    private var currentLength = 0
    private var lastLength = 0
    private var maxLength = 20
    private var isPaste = false
    private var currentFilter = maxLength

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CtEditText)
        maxLength = typedArray.getInt(R.styleable.CtEditText_maxSize, 20)
        typedArray.recycle()
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.custom_edittext, this)
//        edit_text.setUpdateListener(object : MyEditText.UpdateListener {
//            override fun onCut() = Unit
//
//            override fun onCopy() = Unit
//
//            override fun onPaste() {
//                isPaste = true
//                Log.d("result", "onPaste: ")
//
//            }
//        })
        tv_warning.text = "$currentLength/$maxLength"

        edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                lastLength = s?.length!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                Log.d("result", "afterTextChanged: ")
                currentLength = s?.length!!
                if (currentLength == maxLength + 1 && lastLength < currentLength) {//case lastLength < currentLength delete when length is max + 1
                    edit_text.filters = arrayOf(InputFilter.LengthFilter(maxLength))
                    edit_text.text = s.delete(maxLength, currentLength)
                    edit_text.setSelection(edit_text.length())
                    tv_warning.setTextColor(Color.RED)
                    tv_warning.text = "Không được nhập quá $maxLength ký tự"
                    Log.d("length", "afterTextChanged: abcbbcbc")
                } else if (currentLength > maxLength) {
                    if (currentLength == currentFilter +1 && lastLength < currentLength) {
                        edit_text.filters = arrayOf(InputFilter.LengthFilter(currentFilter))
                        edit_text.text = s.delete(currentFilter, currentLength)
                        edit_text.setSelection(edit_text.length())
                    }else{
                        edit_text.filters = arrayOf(InputFilter.LengthFilter(currentLength+1))
                    }
                    currentFilter = currentLength
                    tv_warning.setTextColor(Color.RED)
                    tv_warning.text = "Không được nhập quá $maxLength ký tự"
                    Log.d("length", "afterTextChanged: currentfilter: $currentFilter, s: ${s.toString()}, edit text: ${edit_text.text}")
                } else {
                    edit_text.filters = arrayOf()
                    tv_warning.setTextColor(Color.BLUE)
                    tv_warning.text = "$currentLength/$maxLength"
                }
            }

        })
    }


}