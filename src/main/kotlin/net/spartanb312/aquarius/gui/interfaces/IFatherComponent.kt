package net.spartanb312.aquarius.gui.interfaces

interface IFatherComponent : IComponent {
    var isActive: Boolean
    var children: MutableList<IChildComponent>
}