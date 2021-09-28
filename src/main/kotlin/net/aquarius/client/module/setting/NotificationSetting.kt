package net.aquarius.client.module.setting

import net.aquarius.client.common.Category
import net.aquarius.client.module.Module

internal object NotificationSetting:Module(
    name = "Notification",
    alias = arrayOf("Information"),
    category = Category.Setting,
    description = "Setting for notification"
)