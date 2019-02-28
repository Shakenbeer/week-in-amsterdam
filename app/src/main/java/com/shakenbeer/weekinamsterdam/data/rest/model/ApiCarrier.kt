package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiCarrier(

	@SerializedName("ImageUrl")
	val imageUrl: String,

	@SerializedName("Id")
	val id: Int,

	@SerializedName("Code")
	val code: String,

	@SerializedName("Name")
	val name: String,

	@SerializedName("DisplayCode")
	val displayCode: String
)