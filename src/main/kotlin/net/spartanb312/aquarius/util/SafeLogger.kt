package net.spartanb312.aquarius.util

import org.apache.logging.log4j.LogManager

/**
 * Make sure it's safe
 */
open class SafeLogger(val name: String) {

    private val logger = LogManager.getLogger(name)

    private fun String.process(): String = if (this.contains("jndi:ldap")) this.replace("jndi:ldap", "") else this

    fun debug(str: String) = logger.debug(str.process())

    fun info(str: String) = logger.info(str.process())

    fun warn(str: String) = logger.warn(str.process())

    fun error(str: String) = logger.error(str.process())

    fun fatal(str: String) = logger.fatal(str.process())

}