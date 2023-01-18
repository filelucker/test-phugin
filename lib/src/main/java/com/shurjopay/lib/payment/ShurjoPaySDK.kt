package com.shurjopay.lib.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.shurjopay.lib.model.ErrorSuccess
import com.shurjopay.lib.model.RequestData
import com.shurjopay.lib.model.TestData
import com.shurjopay.lib.utils.AppResourse
import com.shurjopay.lib.utils.Constants
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_PASSWORD
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_SDK_TYPE
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_USERNAME
import com.shurjopay.lib.utils.NetworkManager.isInternetAvailable


/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class ShurjoPaySDK private constructor() {

    @RequiresApi(Build.VERSION_CODES.M)
    fun makePayment(
        context: Context,
//        sdkType: String?,
        data: TestData?,
        resultListener: PaymentResultListener?
    ) {

        listener = resultListener
        if (AppResourse().getString(CONFIG_USERNAME, context).trim().equals("") ||
            AppResourse().getString(CONFIG_PASSWORD, context).trim().equals("") ||
            AppResourse().getString(CONFIG_SDK_TYPE, context).trim().equals("")){
            listener!!.onFailed(
                ErrorSuccess(
                    ErrorSuccess.ESType.ERROR,
                    null,
                    Constants.NO_USER_CREDENTIAL
                )
            )
        }else{
            if (!isInternetAvailable(context)) {
                listener!!.onFailed(
                    ErrorSuccess(
                        ErrorSuccess.ESType.INTERNET_ERROR,
                        null,
                        Constants.NO_INTERNET_MESSAGE
                    )
                )
                return
            }
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(Constants.DATA, data)
//        intent.putExtra(Constants.SDK_TYPE, sdkType)
            context.startActivity(intent)
            }



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