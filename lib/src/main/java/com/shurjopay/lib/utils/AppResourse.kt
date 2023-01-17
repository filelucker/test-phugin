package com.shurjopay.lib.utils

import android.content.Context


/**
 * Created by @author Moniruzzaman on 17/1/23. github: filelucker
 */
class AppResourse {
    fun getString(type: String, context: Context): String {
        try {
            var string = android.content.res.Resources.getSystem().getString(
                android.content.res.Resources.getSystem().getIdentifier(
                    type,
                    Constants.DEF_TYPE, context.getPackageName()
                )
            )
            return string
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
}