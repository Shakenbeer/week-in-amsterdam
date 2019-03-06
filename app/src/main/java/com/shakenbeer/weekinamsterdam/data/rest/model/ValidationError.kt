package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ValidationError(

	@SerializedName("ParameterValue")
	val parameterValue: String? = null,

	@SerializedName("Message")
	val message: String? = null,

	@SerializedName("ParameterName")
	val parameterName: String? = null
)