package tsisyk.app.diary.service.DictaphoneService

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import tsisyk.app.diary.R
import tsisyk.app.diary.activities.DiaryActivity
import tsisyk.app.diary.model.Dictaphone.DictaphoneHolder
import tsisyk.app.diary.model.Dictaphone.PlaybackInfoListener
import tsisyk.app.diary.utilities.TimeManager


@Suppress("DEPRECATION")
class DictaphoneService : Service(), PlaybackInfoListener {
    private var mRemoteViews: RemoteViews? = null
    private var mDictaphone: DictaphoneHolder? = null
    private var mBuilderNotification: NotificationCompat.Builder? = null
    private var mWakeLock: WakeLock? = null

    private fun initializeRemoteViews() {
        mRemoteViews = RemoteViews(packageName, R.layout.custom_notification)
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_view,
                PendingIntent.getActivity(applicationContext,
                        0,
                        DiaryActivity.newIntent(applicationContext, null),
                        0
                ))
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_clear_btn,
                PendingIntent.getActivity(applicationContext,
                        0,
                        DiaryActivity.newIntent(applicationContext,
                                DiaryActivity.ACTION_DELETE),
                        0
                ))
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_save_btn,
                PendingIntent.getActivity(applicationContext,
                        0,
                        DiaryActivity
                                .newIntent(applicationContext, DiaryActivity.ACTION_SAVE),
                        0
                ))
    }

    private fun initializeBuilderNotification() {


        mBuilderNotification = NotificationCompat.Builder(applicationContext).setSmallIcon(R.drawable.ic_micro)

    }

    override fun onCreate() {
        super.onCreate()
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, javaClass.canonicalName)
        mDictaphone = DictaphoneHolder(applicationContext, NAME_DIR)
        mDictaphone!!.addPlaybackInfoListener(this)
        initializeRemoteViews()
        initializeBuilderNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action != null) {
            if (intent.action == ACTION_PAUSE) {
                mDictaphone!!.pause()
            } else if (intent.action == ACTION_RECORDING) {
                mDictaphone!!.resume()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return mDictaphone
    }

    override fun startRecording() {
        mWakeLock!!.acquire()
        mRemoteViews!!.setTextViewText(R.id.custom_notification_state_text_view,
                getString(R.string.state_recording))
        mRemoteViews!!.setImageViewResource(R.id.custom_notification_recording_pause_btn,
                R.drawable.pause_dark)
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_recording_pause_btn,
                PendingIntent.getService(applicationContext,
                        0,
                        newIntent(applicationContext, ACTION_PAUSE),
                        0
                ))
        val notification = mBuilderNotification?.setContent(mRemoteViews)?.build()
        myStartForeground(notification)
    }

    override fun stopRecording() {
        stopForeground(true)
        mWakeLock!!.release()
    }

    override fun pauseRecording() {
        mRemoteViews!!.setTextViewText(R.id.custom_notification_state_text_view,
                getString(R.string.state_pause))
        mRemoteViews!!.setImageViewResource(R.id.custom_notification_recording_pause_btn,
                R.drawable.micro_dark)
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_recording_pause_btn,
                PendingIntent.getService(applicationContext,
                        0,
                        newIntent(applicationContext, ACTION_RECORDING),
                        0
                ))
        val notification = mBuilderNotification?.setContent(mRemoteViews)?.build()
        myStartForeground(notification)
    }

    override fun resumeRecording() {
        mRemoteViews!!.setTextViewText(R.id.custom_notification_state_text_view,
                getString(R.string.state_recording))
        mRemoteViews!!.setImageViewResource(R.id.custom_notification_recording_pause_btn,
                R.drawable.pause_dark)
        mRemoteViews!!.setOnClickPendingIntent(R.id.custom_notification_recording_pause_btn,
                PendingIntent.getService(applicationContext,
                        0,
                        newIntent(applicationContext, ACTION_PAUSE),
                        0
                ))
        val notification = mBuilderNotification?.setContent(mRemoteViews)?.build()
        myStartForeground(notification)
    }

    override fun currentTimeRecording(time: Long) {
        mRemoteViews!!.setTextViewText(R.id.custom_notification_time, TimeManager.getTime(time))
        val notification = mBuilderNotification?.setContent(mRemoteViews)?.build()
        myStartForeground(notification)
    }

    override fun saveFinish() {

    }

    fun myStartForeground(notification: Notification?) {
        val channelId = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "My Background Service")
        } else {
            val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(PRIORITY_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build().also {
                        startForeground(NOTIFICATION_ID, it)
                    }
            startForeground(NOTIFICATION_ID, notification)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String? {
        val chan = NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(chan)
        return channelId
    }
    companion object {

        private val NAME_DIR = "Dictaphone"
        private val ACTION_PAUSE = "ActionStop"
        private val ACTION_RECORDING = "ActionRecording"
        private val NOTIFICATION_ID = 101

        fun newIntent(packageContext: Context, action: String?): Intent {
            val intent = Intent(packageContext, DictaphoneService::class.java)
            if (action != null) {
                intent.action = action
            }
            return intent
        }
    }
}

