package com.shurjopay.plugin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.shurjopay.lib.model.ErrorSuccess
import com.shurjopay.lib.model.RequestData
import com.shurjopay.lib.model.TestData
import com.shurjopay.lib.payment.PaymentResultListener
import com.shurjopay.lib.payment.ShurjoPaySDK
import com.shurjopay.lib.utils.Constants
import com.shurjopay.lib.utils.Constants.Companion.SDK_TYPE_SANDBOX
import shurjopay.android.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Toast.makeText(getApplicationContext(),app_name,Toast.LENGTH_SHORT).show()


//        TestConfig(
//            "sp_sandbox",
//            "pyyk97hu&6u6",
//            "sp",
//            "127.0.0.1",
//            SDK_TYPE_SANDBOX
//        )

//        TestConfig().userName=""



        pay()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun pay() {
        val data = TestData(
//            "sp_sandbox",
//            "pyyk97hu&6u6",
//            "NOK",
            "BDT",
            10.0,
            "NOK" + Random().nextInt(1000000),
            null,
            null,
            " binding.nameLayout.editText?.text.toString()",
            " binding.phoneLayout.editText?.text.toString()",
            null,
            "binding.addressLayout.editText?.text.toString()",
            "binding.cityLayout.editText?.text.toString()",
            null,
            null,
            null,
            "https://www.sandbox.shurjopayment.com/return_url",
            "https://www.sandbox.shurjopayment.com/cancel_url",
            "127.0.0.1",
            "value-of-1",
            "value-of-2",
            "value-of-3",
            "value-of-4"
        )



//        val data = RequestData(
//            "sp_sandbox",
//            "pyyk97hu&6u6",
//            "NOK",
//            "BDT",
//            10.0,
//            "NOK" + Random().nextInt(1000000),
//            null,
//            null,
//            " binding.nameLayout.editText?.text.toString()",
//            " binding.phoneLayout.editText?.text.toString()",
//            null,
//            "binding.addressLayout.editText?.text.toString()",
//            "binding.cityLayout.editText?.text.toString()",
//            null,
//            null,
//            null,
//            "https://www.sandbox.shurjopayment.com/return_url",
//            "https://www.sandbox.shurjopayment.com/cancel_url",
//            "127.0.0.1",
//            "value-of-1",
//            "value-of-2",
//            "value-of-3",
//            "value-of-4"
//        )

        ShurjoPaySDK.instance?.makePayment(
            this,
//            Constants.SDK_TYPE_SANDBOX,
            data,
            object : PaymentResultListener {
                override fun onSuccess(errorSuccess: ErrorSuccess) {
                    Log.d(TAG, "onSuccess: debugMessage = ${errorSuccess.debugMessage}")
                }

                override fun onFailed(errorSuccess: ErrorSuccess) {
                    Log.d(TAG, "onFailed: debugMessage = ${errorSuccess.debugMessage}")
                }

                override fun onBackButtonListener(errorSuccess: ErrorSuccess): Boolean {
                    return true
                }
            })
    }
}