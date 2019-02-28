package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiPlace(

	@SerializedName("ParentId")
	val parentId: Int,

	@SerializedName("Type")
	val type: String,

	@SerializedName("Id")
	val id: Int,

	@SerializedName("Code")
	val code: String,

	@SerializedName("Name")
	val name: String
)