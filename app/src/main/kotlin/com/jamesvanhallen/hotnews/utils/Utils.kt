package com.jamesvanhallen.hotnews.utils

import android.content.Context
import com.jamesvanhallen.hotnews.R
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.IOException
import java.security.cert.CertificateException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun httpErrorHandler(context: Context, throwable: Throwable): String {
    Timber.e(throwable)
    return when (throwable) {
        is IOException -> context.getString(R.string.server_error)
        else -> throwable.message ?: context.getString(R.string.unknown_error)
    }
}

/**
 * Method for test purpose and prevention video ExoPlayer ssl exception
 */
fun setTrustAllCertsSslSocketFactory(builder: OkHttpClient.Builder) {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
        }
    })

    try {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    } catch (e: Exception) {
        Timber.e(e)
    }

}