package com.example.storyapp.utils.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class PasswordTextView : AppCompatEditText {

    private lateinit var visibleButtonImage: Drawable
    private lateinit var inVisibleButtonImage: Drawable
    private lateinit var editTextBackground: Drawable
    private lateinit var cursorTextBackground: Drawable
    private var isVisible: Boolean = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inVisibleButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable
        visibleButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_visibility) as Drawable
        editTextBackground = ContextCompat.getDrawable(context, R.drawable.bg_line_out_black) as Drawable
        cursorTextBackground = ContextCompat.getDrawable(context, R.drawable.cursor_editext_black) as Drawable
        transformationMethod = PasswordTransformationMethod.getInstance()
        setOnClickListener {
            setStatusVisible(isVisible)
        }
        showVisibleOffButton()
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if(p0.length < 6) {
                    error = context.getString(R.string.label_min_input_password)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setStatusVisible(statusVisible: Boolean) {
        isVisible = if(statusVisible) {
            showVisibleOffButton()
            transformationMethod = PasswordTransformationMethod.getInstance()
            false
        } else {
            showVisibleButton()
            transformationMethod = null
            true
        }
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun showVisibleButton() {
        setButtonDrawables(endOfTheText = visibleButtonImage)
    }

    private fun showVisibleOffButton() {
        setButtonDrawables(endOfTheText = inVisibleButtonImage)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        background = editTextBackground
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textCursorDrawable = cursorTextBackground
        }
    }
}