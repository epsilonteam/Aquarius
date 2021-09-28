package net.aquarius.client.util

import net.aquarius.client.Aquarius
import net.aquarius.client.common.interfaces.Helper
import net.minecraft.util.text.TextComponentString

object ChatUtil : Helper {

    private const val DELETE_ID = 94423

    const val SECTION_SIGN = '§'

    fun sendNoSpamMessage(message: String, messageID: Int) {
        sendNoSpamRawChatMessage(
            SECTION_SIGN + "7[" + SECTION_SIGN + "9" + Aquarius.MOD_NAME + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message,
            messageID
        )
    }

    fun sendNoSpamMessage(message: String) {
        sendNoSpamRawChatMessage(SECTION_SIGN + "7[" + SECTION_SIGN + "9" + Aquarius.MOD_NAME + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message)
    }

    fun sendNoSpamMessage(messages: Array<String>) {
        sendNoSpamMessage("")
        for (s in messages) sendNoSpamRawChatMessage(s)
    }

    fun sendNoSpamWarningMessage(message: String) {
        sendNoSpamRawChatMessage(SECTION_SIGN + "7[" + SECTION_SIGN + "e" + "Warning" + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message)
    }

    fun sendNoSpamWarningMessage(message: String, messageID: Int) {
        sendNoSpamRawChatMessage(
            SECTION_SIGN + "7[" + SECTION_SIGN + "e" + "Warning" + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message,
            messageID
        )
    }

    fun sendNoSpamErrorMessage(message: String) {
        sendNoSpamRawChatMessage(SECTION_SIGN + "7[" + SECTION_SIGN + "4" + SECTION_SIGN + "lERROR" + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message)
    }

    fun sendNoSpamErrorMessage(message: String, messageID: Int) {
        sendNoSpamRawChatMessage(
            SECTION_SIGN + "7[" + SECTION_SIGN + "4" + SECTION_SIGN + "lERROR" + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message,
            messageID
        )
    }

    fun sendNoSpamRawChatMessage(message: String) {
        sendSpamlessMessage(message)
    }

    fun sendNoSpamRawChatMessage(message: String, messageID: Int) {
        sendSpamlessMessage(messageID, message)
    }

    fun printRawChatMessage(message: String) {
        mc.addScheduledTask {
            if (mc.player != null && mc.world != null) {
                mc.ingameGUI.chatGUI.printChatMessage(TextComponentString(message))
            }
        }
    }

    fun printChatMessage(message: String) {
        printRawChatMessage(SECTION_SIGN + "7[" + SECTION_SIGN + "9" + Aquarius.MOD_NAME + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message)
    }

    fun printErrorChatMessage(message: String) {
        printRawChatMessage(SECTION_SIGN + "7[" + SECTION_SIGN + "4ERROR" + SECTION_SIGN + "7] " + SECTION_SIGN + "r" + message)
    }

    fun sendSpamlessMessage(message: String) {
        mc.addScheduledTask {
            if (mc.player != null && mc.world != null) {
                mc.ingameGUI.chatGUI
                    .printChatMessageWithOptionalDeletion(TextComponentString(message), DELETE_ID)
            }
        }
    }

    fun sendSpamlessMessage(messageID: Int, message: String) {
        mc.addScheduledTask {
            if (mc.player != null && mc.world != null) {
                mc.ingameGUI.chatGUI
                    .printChatMessageWithOptionalDeletion(TextComponentString(message), messageID)
            }
        }
    }
}