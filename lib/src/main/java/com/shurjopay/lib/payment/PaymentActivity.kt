package com.shurjopay.lib.payment

//import com.shurjopay.lib.TestConfig

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.shurjopay.lib.model.*
import com.shurjopay.lib.networking.ApiClient
import com.shurjopay.lib.networking.ApiInterface
import com.shurjopay.lib.utils.AppResourse
import com.shurjopay.lib.utils.Constants
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_PASSWORD
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_SDK_TYPE
import com.shurjopay.lib.utils.Constants.Companion.CONFIG_USERNAME
import com.shurjopay.lib.utils.IndeterminateProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shurjopay.lib.databinding.ActivityPaymentBinding
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var progressDialog: IndeterminateProgressDialog

    private lateinit var sdkType: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var data: TestData
    private var tokenResponse: Token? = null
    private var checkoutRequest: CheckoutRequest? = null
    private var checkoutResponse: CheckoutResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        app_name = resources.getString(resources.getIdentifier("app_name", DEF_TYPE,  getPackageName()))
        progressDialog = IndeterminateProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)


        sdkType = AppResourse().getString(CONFIG_SDK_TYPE, this@PaymentActivity).trim()
        username = AppResourse().getString(CONFIG_USERNAME, this@PaymentActivity).trim()
        password = AppResourse().getString(CONFIG_PASSWORD, this@PaymentActivity).trim()
//        sdkType = intent.getStringExtra(Constants.SDK_TYPE).toString()

        if (Build.VERSION.SDK_INT >= 33) {
            data = intent.getParcelableExtra(Constants.DATA, TestData::class.java)!!
        } else {
            data = intent.getParcelableExtra(Constants.DATA)!!
        }
        getToken()




    }



    private fun getToken() {

        val token = Token(
            username, password, null, null, null,
            null, null, null, null
        )
        showProgress()
        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.getToken(token)
            ?.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        hideProgress()
                        tokenResponse = response.body()
                        getExecuteUrl()
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    hideProgress()
                    ShurjoPaySDK.listener?.onFailed(
                        ErrorSuccess(
                            ErrorSuccess.ESType.HTTP_ERROR,
                            null,
                            Constants.PAYMENT_DECLINED,
                        )
                    )
                    finish()
                }
            })
    }

    private fun getExecuteUrl() {
        showProgress()
        checkoutRequest = onExecuteUrlDataBuilder(tokenResponse, data)
        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.checkout(
            "Bearer " + tokenResponse?.token,
            checkoutRequest!!
        )?.enqueue(object : Callback<CheckoutResponse> {
            override fun onResponse(
                call: Call<CheckoutResponse>,
                response: Response<CheckoutResponse>
            ) {
                hideProgress()
                if (response.isSuccessful) {
                    checkoutResponse = response.body()
                    setupWebView()
                }
            }

            override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                hideProgress()
                ShurjoPaySDK.listener?.onFailed(
                    ErrorSuccess(
                        ErrorSuccess.ESType.HTTP_ERROR,
                        null,
                        Constants.PAYMENT_DECLINED,
                    )
                )
                finish()
            }
        })
    }

    private fun setupWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl(checkoutResponse?.checkout_url.toString())
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//        Log.d(TAG, "shouldOverrideUrlLoading: url = $url")
                binding.webView.setVisibility(View.GONE);
                if (url.contains(data.cancelUrl.toString())) {
                    ShurjoPaySDK.listener?.onFailed(
                        ErrorSuccess(
                            ErrorSuccess.ESType.HTTP_ERROR,
                            null,
                            Constants.PAYMENT_CANCELLED,
                        )
                    )
                    finish()
                }
                if (url.contains(data.returnUrl.toString()) && url.contains("order_id")) {
                    verifyPayment()
                }
                return false
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }

        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                binding.progressBar.progress = newProgress
            }
        }
    }

    private fun verifyPayment() {
        showProgress()
        val transactionInfo = onVerifyPaymentDataBuilder(checkoutResponse)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.verify(
            "Bearer " + tokenResponse?.token,
            transactionInfo
        )?.enqueue(object : Callback<List<TransactionInfo>> {
            override fun onResponse(
                call: Call<List<TransactionInfo>>,
                response: Response<List<TransactionInfo>>
            ) {
                hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.get(0)?.sp_code == 1000) {
                        ShurjoPaySDK.listener?.onSuccess(
                            ErrorSuccess(
                                ErrorSuccess.ESType.SUCCESS,
                                response.body()?.get(0),
                                "Payment successfully done",
                            )
                        )

                    }
                    finish()
                }
            }

            override fun onFailure(call: Call<List<TransactionInfo>>, t: Throwable) {
                hideProgress()
                ShurjoPaySDK.listener?.onFailed(
                    ErrorSuccess(
                        ErrorSuccess.ESType.HTTP_ERROR,
                        null,
                        Constants.PLEASE_CHECK_YOUR_PAYMENT,
                    )
                )
                finish()
            }
        })
    }

    private fun showProgress() {
        progressDialog.show()
    }

    private fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        ShurjoPaySDK.listener?.onFailed(
            ErrorSuccess(
                ErrorSuccess.ESType.HTTP_ERROR,
                null,
                Constants.PAYMENT_CANCELLED_BY_USER,
            )
        )
        return super.getOnBackInvokedDispatcher()
    }

    companion object {
        private const val TAG = "PaymentActivity"
    }

    private fun onExecuteUrlDataBuilder(
        tokenResponse: Token?,
        data: TestData
    ): CheckoutRequest {
        return CheckoutRequest(
            tokenResponse?.token.toString(),
            tokenResponse?.store_id!!,
            sdkType,
            data.currency,
            data.returnUrl,
            data.cancelUrl,
            data.amount,
            data.orderId,
            data.discountAmount,
            data.discPercent,
            data.clientIp,
            data.customerName,
            data.customerPhone,
            data.customerEmail,
            data.customerAddress,
            data.customerCity,
            data.customerState,
            data.customerPostcode,
            data.customerCountry,
            data.value1,
            data.value2,
            data.value3,
            data.value4
        )
    }

    private fun onVerifyPaymentDataBuilder(checkoutResponse: CheckoutResponse?): TransactionInfo {
        return TransactionInfo(
            null,
            checkoutResponse?.sp_order_id!!,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
