package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class Agent(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("ImageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("OptimisedForMobile")
	val optimisedForMobile: Boolean? = null,

	@field:SerializedName("Name")
	val name: String? = null
)