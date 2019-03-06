package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ServerError(
	@SerializedName("ValidationErrors")
	val validationErrors: List<ValidationError?>? = null
)