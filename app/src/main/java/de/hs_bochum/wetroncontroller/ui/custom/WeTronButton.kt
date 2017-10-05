package de.hs_bochum.wetroncontroller.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button

/**
 * Created by sebastian on 04/10/17.
 */

class WeTronButton : android.support.v7.widget.AppCompatButton {

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
