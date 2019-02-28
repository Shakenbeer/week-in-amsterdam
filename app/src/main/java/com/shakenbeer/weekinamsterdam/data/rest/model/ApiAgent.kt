package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiAgent(

	@SerializedName("Status")
	val status: String,

	@SerializedName("Type")
	val type: String,

	@SerializedName("ImageUrl")
	val imageUrl: String,

	@SerializedName("Id")
	val id: Int,

	@SerializedName("OptimisedForMobile")
	val optimisedForMobile: Boolean,

	@SerializedName("Name")
	val name: String
)