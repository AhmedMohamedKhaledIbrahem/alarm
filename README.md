# Local Notification Workaround with AlarmManager, BroadcastReceiver & Alarm Clock
This project demonstrates how to create a local notification system that mimics the behavior of Firebase Cloud Messaging (FCM) — without requiring an internet connection or a backend service.
The solution uses:
- `AlarmManager` to schedule tasks
- `BroadcastReceiver` to handle alarm triggers
- A custom approach to wake the app even when it's in the background or killed

##  Problem on Android 10 & 11
When the user swipes the app from recent apps (force kills it), scheduled AlarmManager tasks may no longer trigger — especially under battery optimization.

## Solution Overview
### 1. Regular AlarmManager + BroadcastReceiver (Works partially)
- Works while the app is in the background or foreground
- Fails on Android 10 & 11 when the app is removed from recents
- Requires user to disable battery optimization manually
- Risk: Google Play may reject the app if this permission isn't justified clearly

### 2. Alarm Clock Api `setAlarmClock()`
- Bypasses battery restrictions by using the system’s native alarm clock
- Works reliably on Android 10 and above, even if the app is killed
- Triggers a system-level alarm UI
- Requires asking the user for confirmation before enabling
- Better chance of Play Store approval (less suspicious than battery optimization prompts)

## Watch the Full Explanation on LinkedIn 
[link](https://www.linkedin.com/posts/ahmed-mohamed-elfeky_%D8%A7%D9%84%D8%B3%D9%84%D8%A7%D9%85-%D8%B9%D9%84%D9%8A%D9%83%D9%85-%D9%85%D9%86-%D8%A7%D8%B3%D8%A8%D9%88%D8%B9%D9%8A%D9%86-%D9%86%D8%B2%D9%84%D8%AA-%D8%A8%D9%88%D8%B3%D8%AA-%D8%A7%D8%B2%D8%A7%D9%8A-%D8%AA%D8%B9%D9%85%D9%84-activity-7300486160378740736-JiuD?utm_source=share&utm_medium=member_desktop&rcm=ACoAAExgZu0BaG6qaHMuTjGcC6I1cJW27e5mMgw)


