package com.jamesvanhallen.hotnews

import okhttp3.ResponseBody

open class RepositoryException: RuntimeException()

class UnknownFailureException: RepositoryException()

class ResponseException(override val message:String, val errorBody: ResponseBody?) : RepositoryException()