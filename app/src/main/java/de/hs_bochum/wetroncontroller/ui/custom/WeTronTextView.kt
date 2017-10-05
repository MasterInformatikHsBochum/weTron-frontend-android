package de.hs_bochum.wetroncontroller.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

/**
 * Created by sebastian on 03/10/17.
 */

class WeTronTextView : android.support.v7.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        val tf = Typeface.createFromAsset(context.assets, "fonts/space_age.ttf")
        setTypeface(tf, 1)
    }
}
