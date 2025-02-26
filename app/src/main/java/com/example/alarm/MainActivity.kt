package com.example.alarm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alarm.alarm.IAlarmManager
import com.example.alarm.notification.NotificationEntity
import com.example.alarm.ui.theme.AlarmTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val versionCode = Build.VERSION.SDK_INT

    @Inject
    lateinit var alarmManager: IAlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        val notificationEntity = NotificationEntity(
            id = 1001,
            title = "First Notification",
            message = "First Notification is worked",
            timeStamp = 5L
        )
        val notificationEntity2 = NotificationEntity(
            id = 1002,
            title = "Second Notification",
            message = "Second Notification is worked ",
            timeStamp = 8L
        )


        setContent {
            AlarmTheme {
                val preferenceManagerViewModel = hiltViewModel<PreferenceManagerViewModel>()
                Scaffold { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (versionCode < Build.VERSION_CODES.S) {
                            ShowAlarmDialog(preferenceManagerViewModel)
                        }
                        ScheduleAlarm(
                            notificationEntity,
                            "The First Notification",
                            Pair(13, 29),
                            preferenceManagerViewModel
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        ScheduleAlarm(
                            notificationEntity2,
                            "The Second Notification",
                            Pair(13, 29),
                            preferenceManagerViewModel
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ScheduleAlarm(
        notificationEntity: NotificationEntity,
        text: String,
        time: Pair<Int, Int>,
        preferenceManagerViewModel: PreferenceManagerViewModel
    ) {
        val enabledButton by preferenceManagerViewModel.isAlarmEnabled.collectAsState()
        Text(text)
        Button(
            onClick = {
                when (versionCode) {
                    Build.VERSION_CODES.Q, Build.VERSION_CODES.R -> {
                        alarm(time.first, time.second, notificationEntity.message)
                    }

                    else -> {
                        alarmManager.scheduleAlarm(notificationEntity)
                    }
                }

            },
            enabled = enabledButton
        ) {
            Text(
                "trigger",
                color = Color.Black
            )
        }
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED -> {
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    fun alarm(hour: Int, minute: Int, message: String) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minute)
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(intent)
    }


    @Composable
    fun ShowAlarmDialog(preferenceManagerViewModel: PreferenceManagerViewModel) {
        val showDialog by preferenceManagerViewModel.isDialogShown.collectAsState()

        if (showDialog) {
            AlarmClockDialog(
                onDismiss = {
                    preferenceManagerViewModel.setDialogShown(false)
                    preferenceManagerViewModel.setAlarmEnabled(false)
                },
                onConfirm = {
                    preferenceManagerViewModel.setAlarmEnabled(true)
                    preferenceManagerViewModel.setDialogShown(false)
                }
            )
        }
    }

    @Composable
    private fun AlarmClockDialog(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = { Text(stringResource(R.string.Enable_Reminder_Feature)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.dialog_message),
                        textAlign = TextAlign.Start
                    )
                }
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(stringResource(R.string.Enable_Reminder))
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}






