package io.demoapps.stickynotification

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var fireNotificationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariables()
        bindViews()
    }

    private fun initVariables() {
        fireNotificationButton = findViewById(R.id.fireNotificationButton)
    }

    private fun bindViews() {
        fireNotificationButton.setOnClickListener {
            val notificationList = listOf(
                StickyNotificationModel("First Notification"),
                StickyNotificationModel("Second Notification"),
                StickyNotificationModel("Third Notification"),
                StickyNotificationModel("Forth Notification"),
                StickyNotificationModel("Fifth Notification"),
                StickyNotificationModel("Sixth Notification")
            )

            val stickNotification = StickyNotificationUtil()
            stickNotification.fireNotification(this, packageName, notificationList)
        }
    }
}