package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiPlace(

	@field:SerializedName("ParentId")
	val parentId: Int? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("Name")
	val name: String? = null
)