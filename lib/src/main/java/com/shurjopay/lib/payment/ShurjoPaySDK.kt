package com.shurjopay.lib.payment

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.shurjopay.lib.model.ErrorSuccess
import com.shurjopay.lib.model.RequestData
import com.shurjopay.lib.utils.Constants
import com.shurjopay.lib.utils.NetworkManager.isInternetAvailable


/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class ShurjoPaySDK private constructor() {

    @RequiresApi(Build.VERSION_CODES.M)
    fun makePayment(
        activity: Activity, sdkType: String?, data: RequestData?,
        resultListener: PaymentResultListener?
    ) {
        listener = resultListener
        if (!isInternetAvailable(activity)) {
            listener!!.onFailed(
                ErrorSuccess(
                    ErrorSuccess.ESType.INTERNET_ERROR,
                    null,
                    Constants.NO_INTERNET_MESSAGE
                )
            )
            return
        }
        val intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(Constants.DATA, data)
        intent.putExtra(Constants.SDK_TYPE, sdkType)
        activity.startActivity(intent)
    }

    companion object {
        private var mInstance: ShurjoPaySDK? = ShurjoPaySDK()
        var listener: PaymentResultListener? = null
        val instance: ShurjoPaySDK?
            get() {
                if (mInstance == null) {
                    mInstance = ShurjoPaySDK()
                }
                return mInstance
            }
    }
}