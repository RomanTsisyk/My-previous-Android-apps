package tsisyk.app.diary.views

import tsisyk.app.diary.R
import android.content.Context
import androidx.appcompat.widget.AppCompatImageButton
import android.util.AttributeSet

class CustomImageButton : AppCompatImageButton {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val attributes = context
                .obtainStyledAttributes(attrs, R.styleable.CustomImageButton)
        isEnabled = attributes.getBoolean(R.styleable.CustomImageButton_enabled, true)
        attributes.recycle()
    }
}
