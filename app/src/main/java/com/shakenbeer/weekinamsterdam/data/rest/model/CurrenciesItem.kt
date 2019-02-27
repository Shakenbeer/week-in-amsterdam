package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class CurrenciesItem(

	@field:SerializedName("DecimalSeparator")
	val decimalSeparator: String? = null,

	@field:SerializedName("ThousandsSeparator")
	val thousandsSeparator: String? = null,

	@field:SerializedName("SymbolOnLeft")
	val symbolOnLeft: Boolean? = null,

	@field:SerializedName("SpaceBetweenAmountAndSymbol")
	val spaceBetweenAmountAndSymbol: Boolean? = null,

	@field:SerializedName("Symbol")
	val symbol: String? = null,

	@field:SerializedName("DecimalDigits")
	val decimalDigits: Int? = null,

	@field:SerializedName("Code")
	val code: String? = null,

	@field:SerializedName("RoundingCoefficient")
	val roundingCoefficient: Int? = null
)