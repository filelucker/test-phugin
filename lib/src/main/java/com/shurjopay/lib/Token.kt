package com.shurjopay.lib

/**
 * Token data class
 *
 * @author  Moniruzzaman
 * @since   2021-08-07
 */
data class Token(
    var username: String,
    var password: String,
    var token: String?,
    var store_id: Int?,
    var execute_url: String?,
    var token_type: String?,
    var sp_code: Int?,
    var massage: String?,
    var expires_in: String?
)