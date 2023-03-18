package io.demoapps.stickynotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class StickyNotificationUtil {
    private var _packageName = ""
    private var notificationCount = 0


    fun fireNotification(
        context: Context,
        packageName: String,
        notificationList: List<StickyNotificationModel>
    ) {
        _packageName = packageName
        val notificationBuilder = NotificationCompat.Builder(context, "default")
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)

        val notificationManager = NotificationManagerCompat.from(context)
        setNotificationChannel(notificationManager)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                notificationBuilder.setContent(getNotification(notificationList[notificationCount]))
                notificationManager.notify(1, notificationBuilder.build())
                notificationCount = (notificationCount + 1) % notificationList.size
            }
        }, 0, 1000)
    }

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

    private fun getNotification(notification: StickyNotificationModel): RemoteViews {
        val remoteView = RemoteViews(_packageName, R.layout.sticky_notification_layout)
        remoteView.setTextViewText(R.id.notificationTitle, notification.title)
        return remoteView
    }

}