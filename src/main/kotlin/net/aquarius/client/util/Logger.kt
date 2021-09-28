package net.aquarius.client.util

import net.aquarius.client.Aquarius.MOD_NAME
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("NOTHING_TO_INLINE")
object Logger {
    @JvmStatic
    val logger: Logger = LogManager.getLogger(MOD_NAME)

    @JvmStatic
    inline fun info(string: String) {
        logger.info(string)
    }

    @JvmStatic
    inline fun warn(string: String) {
        logger.warn(string)
    }

    @JvmStatic
    inline fun error(string: String) {
        logger.error(string)
    }

    @JvmStatic
    inline fun fatal(string: String) {
        logger.fatal(string)
    }
}