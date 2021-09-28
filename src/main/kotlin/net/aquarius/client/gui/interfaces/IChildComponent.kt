package net.aquarius.client.gui.interfaces

interface IChildComponent : IComponent {
    var father: IFatherComponent
}