package net.aquarius.client.launch

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@IFMLLoadingPlugin.Name("SpartanFMLLoader")
@IFMLLoadingPlugin.MCVersion("1.12.2")
class FMLCoreMod : IFMLLoadingPlugin {

    companion object{
        val log: Logger = LogManager.getLogger("Spartan Loader")
    }

    init {
        log.info("Loading Spartan ModLauncher")
        InitManager.load()
        log.info("Loading Spartan MixinLoader")
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