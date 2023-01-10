package com.shurjopay.lib.payment

import com.shurjopay.lib.model.ErrorSuccess


/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface PaymentResultListener {
    fun onSuccess(errorSuccess: ErrorSuccess)
    fun onFailed(errorSuccess: ErrorSuccess)
    fun onBackButtonListener(errorSuccess: ErrorSuccess): Boolean
}