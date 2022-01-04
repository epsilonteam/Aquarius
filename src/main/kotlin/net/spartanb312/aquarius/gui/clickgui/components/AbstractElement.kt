package net.spartanb312.aquarius.gui.clickgui.components

import net.spartanb312.aquarius.gui.interfaces.IComponent

abstract class AbstractElement : IComponent, IAnimatable {
    override var height: Int = 0
    override var width: Int = 0
    override var x: Int = 0
    override var y: Int = 0
}