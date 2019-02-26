package com.shakenbeer.weekinamsterdam.data.remote

import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError

class SkyscannerServerError(val serverError: ServerError) : Exception()
