package alo.android.multitests.tool

import android.content.Context
import android.widget.Toast
import timber.log.Timber

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun initLogger() {
    Timber.plant(object : Timber.DebugTree() {
        private val TAG = "MyLog"
        private val regex = Regex("^.*\\.")

        override fun createStackElementTag(element: StackTraceElement): String? {
            return "$TAG#" +
                    "(${element.fileName}:${element.lineNumber})#" +
                    "${element.className.replace(regex, "")}.${element.methodName}"
        }
    })
}
