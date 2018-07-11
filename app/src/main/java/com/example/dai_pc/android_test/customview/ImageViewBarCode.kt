package com.example.dai_pc.android_test.customview

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

class ImageViewBarCode(context: Context) :AppCompatImageView(context){
    constructor(context:Context, attrs: AttributeSet) : this(context) {
    }
    constructor(context:Context , attrs : AttributeSet , defStyleAttr:Int) : this(context, attrs){

    }
}