package net.spartanb312.aquarius.module.setting

import net.spartanb312.aquarius.module.Category
import net.spartanb312.aquarius.module.Module

internal object NotificationSetting: Module(
    name = "Notification",
    alias = arrayOf("Information"),
    category = Category.Setting,
    description = "Setting for notification"
)