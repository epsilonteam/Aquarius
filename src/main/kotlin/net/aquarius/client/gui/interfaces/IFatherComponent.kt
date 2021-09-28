package net.aquarius.client.gui.interfaces

interface IFatherComponent : IComponent {
    var isActive: Boolean
    var children: MutableList<IChildComponent>
}