package com.shurjopay.lib.networking


import com.shurjopay.lib.model.CheckoutRequest
import com.shurjopay.lib.model.CheckoutResponse
import com.shurjopay.lib.model.Token
import com.shurjopay.lib.model.TransactionInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface ApiInterface {

  //////////////////// POST ///////////////////

  @POST("get_token")
  fun getToken(
    @Body token: Token
  ): Call<Token>

  @POST("secret-pay")
  fun checkout(
    @Header("Authorization") token: String,
    @Body checkoutRequest: CheckoutRequest
  ): Call<CheckoutResponse>

  @POST("verification")
  fun verify(
    @Header("Authorization") token: String,
    @Body transactionInfo: TransactionInfo
  ): Call<List<TransactionInfo>>
}