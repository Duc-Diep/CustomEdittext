package com.ducdiep.customedittext

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class MyEditText : androidx.appcompat.widget.AppCompatEditText {
    var listener: UpdateListener? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setUpdateListener(listener: UpdateListener?) {
        this.listener = listener
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            R.id.cut -> if (listener != null) listener!!.onCut()
            R.id.copy -> if (listener != null) listener!!.onCopy()
            R.id.paste -> if (listener != null) listener!!.onPaste()
        }
        return consumed
    }

    interface UpdateListener {
        fun onCut()
        fun onCopy()
        fun onPaste()
    }
}