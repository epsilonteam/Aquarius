package net.aquarius.client.gui.clickgui.components

import net.aquarius.client.gui.interfaces.IComponent

abstract class AbstractElement : IComponent, IAnimatable {
    override var height: Int = 0
    override var width: Int = 0
    override var x: Int = 0
    override var y: Int = 0
}