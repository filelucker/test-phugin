package com.shurjopay.lib.model

import android.os.Parcelable
//import com.shurjopay.lib.TestConfig
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 17/1/23. github: filelucker
 */
@Parcelize
data class TestData(
//    var username: String,
//    var password: String,
//    var prefix: String,
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

//{
////    constructor(name: String) : this(name, "Deere", Int.MIN_VALUE)
//
//    constructor(
////        var username: String,
////        var password: String,
////        var prefix: String,
//        currency: String,
//        amount: Double,
//        orderId: String,
//        discountAmount: Double?,
//        discPercent: Double?,
//        customerName: String,
//        customerPhone: String,
//        customerEmail: String?,
//        customerAddress: String,
//        customerCity: String,
//        customerState: String?,
//        customerPostcode: String?,
//        customerCountry: String?,
//        returnUrl: String?,
//        cancelUrl: String?,
//         clientIp: String?,
//        value1: String?,
//        value2: String?,
//        value3: String?,
//        value4: String?
//
//    ) : this(
//        c.userName,
//        c.passwooord,
//        c.prefix,
//        currency,
//        0.0,
//        "orderId: String",
//        0.0,
//        0.0,
//        customerName,
//        customerPhone,
//        customerEmail,
//        customerAddress,
//        customerCity,
//        customerState,
//        customerPostcode,
//        customerCountry,
//        returnUrl,
//        cancelUrl,
//        clientIp,
//        value1,
//        value2,
//        value3,
//        value4
//    )
//
//}
//
//var c = TestConfig()
