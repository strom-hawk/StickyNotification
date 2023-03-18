package io.demoapps.stickynotification

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {
    private lateinit var workManagerInstance: WorkManager
    private lateinit var fireNotificationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariables()
        bindViews()
    }

    private fun initVariables() {
        workManagerInstance = WorkManager.getInstance(applicationContext)
        fireNotificationButton = findViewById(R.id.fireNotificationButton)
    }

    private fun bindViews() {
        fireNotificationButton.setOnClickListener {

            val data = Data.Builder()
            data.putString("packageName", packageName)

            val notificationWork = OneTimeWorkRequest
                .Builder(StickyNotificationWorkManager::class.java )
                .setInputData(data.build())
                //.setInitialDelay(5, TimeUnit.SECONDS)
                .build()
            workManagerInstance.enqueue(notificationWork)
        }
    }
}