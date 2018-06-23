package io.npatarino.tozen.design.extensions

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.widget.Toast

inline fun <reified T : Activity> Activity.startAnimatedActivity(activityClass: Class<T>) {
    val options: ActivityOptions
    if (!isLowerThanLollipop()) {
        options = ActivityOptions.makeSceneTransitionAnimation(this)
        val intent = Intent(this, activityClass)
        startActivity(intent, options.toBundle())
    } else {
        startActivity(intent)
    }
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
