package com.shurjopay.lib.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
@Parcelize
data class RequestData(
    var username: String,
    var password: String,
    var prefix: String,
    var currency: String,
    var amount: Double,
    var orderId: String,
    var discountAmount: Double?,
    var discPercent: Double?,
    var customerName: String,
    var customerPhone: String,
    var customerEmail: String?,
    var customerAddress: String,
    var customerCity: String,
    var customerState: String?,
    var customerPostcode: String?,
    var customerCountry: String?,
    var returnUrl: String?,
    var cancelUrl: String?,
    var clientIp: String?,
    var value1: String?,
    var value2: String?,
    var value3: String?,
    var value4: String?
) : Parcelable
