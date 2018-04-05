package com.jamesvanhallen.hotnews

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

fun View.display() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Activity.isOnline(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo

    return netInfo != null && netInfo.isConnectedOrConnecting
}

fun Activity.addFragment(f: Fragment) {
    initTransaction(f, true)
}

fun Activity.replaceFragment(f: Fragment) {
    initTransaction(f, false)
}

private fun Activity.initTransaction(f: Fragment, addToBackStack: Boolean, container: Int = R.id.main_container) {

    val fragTransaction = fragmentManager.beginTransaction()
            .replace(container, f)
    if (addToBackStack) fragTransaction.addToBackStack(f.javaClass.simpleName)

    fragTransaction.commit()
}

fun Snackbar.setWhiteText() {
    view.findViewById<TextView>(android.support.design.R.id.snackbar_text).setTextColor(Color.WHITE)
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun Activity.showSnackBar(view: View, action: () -> Unit) {
    val snackbar = Snackbar.make(view, getString(R.string.network_unavailable), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry), { action.invoke() })
    snackbar.setWhiteText()
    snackbar.show()
}