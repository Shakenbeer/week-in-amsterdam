package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class Carrier(

	@field:SerializedName("ImageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("DisplayCode")
	val displayCode: String? = null
)