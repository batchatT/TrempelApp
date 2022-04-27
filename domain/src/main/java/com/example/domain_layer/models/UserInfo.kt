package com.example.domain_layer.models

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: Name,
    @SerializedName("address")
    val address: Address
) {
    val fullName: String
        get() = "${name.firstName} ${name.lastName}"
    val fullAddress: String
        get() = "${address.city}, ${address.street}, ${address.number}"
}

data class Name(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String
)

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("street")
    val street: String,
    @SerializedName("number")
    val number: String
)
