package com.example.dennismoyseyev.hangman

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat

/**
 * Helper class for showing and canceling hang man
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
class HangManNotification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(id, notification)
    }

    companion object {
        /**
         * The unique identifier for this type of notification.
         */
        private val NOTIFICATION_TAG = "HangMan"
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
        /**
         * Shows the notification, or updates a previously shown notification of
         * this type, with the given parameters.
         *
         *
         * TODO: Customize this method's arguments to present relevant content in
         * the notification.
         *
         *
         * TODO: Customize the contents of this method to tweak the behavior and
         * presentation of hang man notifications. Make
         * sure to follow the
         * [
 * Notification design guidelines](https://developer.android.com/design/patterns/notifications.html) when doing so.
         *
         * @see .cancel
         */
        fun notify(context: Context,
                   exampleString: String, number: Int) {
            val res = context.resources

            // This image is used as the notification's large icon (thumbnail).
            // TODO: Remove this if your notification has no relevant thumbnail.
            val picture = BitmapFactory.decodeResource(res, R.drawable.example_picture)


            val title = res.getString(
                    R.string.hang_man_notification_title_template, exampleString)
            val text = res.getString(
                    R.string.hang_man_notification_placeholder_text_template, exampleString)

            val builder = NotificationCompat.Builder(context)

                    // Set appropriate defaults for the notification light, sound,
                    // and vibration.
                    .setDefaults(Notification.DEFAULT_ALL)

                    // Set required fields, including the small icon, the
                    // notification title, and text.
                    .setSmallIcon(R.drawable.ic_stat_hang_man)
                    .setContentTitle(title)
                    .setContentText(text)

                    // All fields below this line are optional.

                    // Use a default priority (recognized on devices running Android
                    // 4.1 or later)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    // Provide a large icon, shown with the notification in the
                    // notification drawer on devices running Android 3.0 or later.
                    .setLargeIcon(picture)

                    // Set ticker text (preview) information for this notification.
                    .setTicker(exampleString)

                    // Show a number. This is useful when stacking notifications of
                    // a single type.
                    .setNumber(number)

                    // If this notification relates to a past or upcoming event, you
                    // should set the relevant time information using the setWhen
                    // method below. If this call is omitted, the notification's
                    // timestamp will by set to the time at which it was shown.
                    // TODO: Call setWhen if this notification relates to a past or
                    // upcoming event. The sole argument to this method should be
                    // the notification timestamp in milliseconds.
                    //.setWhen(...)

                    // Set the pending intent to be initiated when the user touches
                    // the notification.
                    .setContentIntent(
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                                    PendingIntent.FLAG_UPDATE_CURRENT))

                    // Show expanded text content on devices running Android 4.1 or
                    // later.
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(text)
                            .setBigContentTitle(title)
                            .setSummaryText("Dummy summary text"))

                    // Example additional actions for this notification. These will
                    // only show on devices running Android 4.1 or later, so you
                    // should ensure that the activity in this notification's
                    // content intent provides access to the same actions in
                    // another way.
                    .addAction(
                            R.drawable.ic_action_stat_share,
                            res.getString(R.string.action_share),
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    Intent.createChooser(Intent(Intent.ACTION_SEND)
                                            .setType("text/plain")
                                            .putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"),
                                    PendingIntent.FLAG_UPDATE_CURRENT))
                    .addAction(
                            R.drawable.ic_action_stat_reply,
                            res.getString(R.string.action_reply),
                            null)

                    // Automatically dismiss the notification when it is touched.
                    .setAutoCancel(true)

            notify(context, builder.build())
        }

        @TargetApi(Build.VERSION_CODES.ECLAIR)
        private fun notify(context: Context, notification: Notification) {
            val nm = context
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                nm.notify(NOTIFICATION_TAG, 0, notification)
            } else {
                nm.notify(NOTIFICATION_TAG.hashCode(), notification)
            }
        }


    }
}
