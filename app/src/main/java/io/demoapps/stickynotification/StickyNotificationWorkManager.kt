package io.demoapps.stickynotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class StickyNotificationWorkManager(
    val context: Context,
    params: WorkerParameters,
) : Worker(context, params) {
    private var notificationCount = 0
    private var packageName = ""


    override fun doWork(): Result {
        val inputData = inputData
        getArgumentsFromInputData(inputData)
        createAndFireNotification(inputData)
        return Result.success()
    }

    /**
     * Function to create and fire notifications.
     */
    private fun createAndFireNotification(data: Data) {
        val notificationBuilder = NotificationCompat.Builder(context, "default")
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)

        val notificationManager = NotificationManagerCompat.from(context)
        setNotificationChannel(notificationManager)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                notificationBuilder.setContent(getNotification(notificationCount))
                notificationManager.notify(1, notificationBuilder.build())
                notificationCount += 1
            }
        }, 0, 1000)


    }

    /**
     * Function to set notification channel if build version is greater than oreo
     */
    private fun setNotificationChannel(notificationManager: NotificationManagerCompat) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default",
                "Default",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Default Notification"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getArgumentsFromInputData(data: Data) {
        packageName = data.getString("packageName") ?: ""
        //notificationList = data.
    }

    private fun getNotification(count: Int): RemoteViews {
        val remoteView = RemoteViews(packageName, R.layout.sticky_notification_layout)
        remoteView.setTextViewText(R.id.notificationTitle, "Notification number: $count")
        return remoteView
    }
}