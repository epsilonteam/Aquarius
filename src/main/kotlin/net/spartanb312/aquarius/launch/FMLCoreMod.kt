package net.spartanb312.aquarius.launch

import net.spartanb312.aquarius.util.Logger
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin

@IFMLLoadingPlugin.Name("AquariusFMLLoader")
@IFMLLoadingPlugin.MCVersion("1.12.2")
class FMLCoreMod : IFMLLoadingPlugin {

    init {
        Logger.info("Loading  ModLauncher")
        InitializationManager.onTweak()
        MixinLoader.load()
    }

    override fun getASMTransformerClass(): Array<String> {
        return arrayOf()
    }

    override fun getModContainerClass(): String? {
        return null
    }

    override fun getSetupClass(): String? {
        return null
    }

    override fun injectData(data: Map<String, Any>) {}

    override fun getAccessTransformerClass(): String? {
        return null
    }

}