package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiCurrency(

	@SerializedName("DecimalSeparator")
	val decimalSeparator: String,

	@SerializedName("ThousandsSeparator")
	val thousandsSeparator: String,

	@SerializedName("SymbolOnLeft")
	val symbolOnLeft: Boolean,

	@SerializedName("SpaceBetweenAmountAndSymbol")
	val spaceBetweenAmountAndSymbol: Boolean,

	@SerializedName("Symbol")
	val symbol: String,

	@SerializedName("DecimalDigits")
	val decimalDigits: Int,

	@SerializedName("Code")
	val code: String,

	@SerializedName("RoundingCoefficient")
	val roundingCoefficient: Int
)