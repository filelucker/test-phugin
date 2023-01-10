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
    var username: String?,
    var password: String?,
    var prefix: String?,
    var currency: String?,
    var amount: Double?,
    var orderId: String?,
    var discountAmount: Double?,
    var discPercent: Double?,
    var customerName: String?,
    var customerPhone: String?,
    var customerEmail: String?,
    var customerAddress: String?,
    var customerCity: String?,
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    companion object : Parceler<RequestData> {

        override fun RequestData.write(parcel: Parcel, flags: Int) {
            parcel.writeString(username)
            parcel.writeString(password)
            parcel.writeString(prefix)
            parcel.writeString(currency)
            parcel.writeDouble(amount!!)
            parcel.writeString(orderId)
            parcel.writeValue(discountAmount)
            parcel.writeValue(discPercent)
            parcel.writeString(customerName)
            parcel.writeString(customerPhone)
            parcel.writeString(customerEmail)
            parcel.writeString(customerAddress)
            parcel.writeString(customerCity)
            parcel.writeString(customerState)
            parcel.writeString(customerPostcode)
            parcel.writeString(customerCountry)
            parcel.writeString(returnUrl)
            parcel.writeString(cancelUrl)
            parcel.writeString(clientIp)
            parcel.writeString(value1)
            parcel.writeString(value2)
            parcel.writeString(value3)
            parcel.writeString(value4)
        }

        override fun create(parcel: Parcel): RequestData {
            return RequestData(parcel)
        }
    }
}
