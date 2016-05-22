package tehnut.lib;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import tehnut.lib.annot.Handler;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.ModItem;
import tehnut.lib.impl.ClientHandler;
import tehnut.lib.impl.CommonHandler;
import tehnut.lib.util.helper.LogHelper;

import java.util.Set;

/**
 * Create a new instance of this in your mod
 **/
public class LendingLibrary {

    private static final LogHelper logger = new LogHelper("LendingLibrary");
    private static LogHelper modLogger;
    private static LendingLibrary instance;
    private static String MODID;

    private Set<ASMDataTable.ASMData> modBlocks;
    private Set<ASMDataTable.ASMData> modItems;
    private Set<ASMDataTable.ASMData> modHandlers;
    private CommonHandler commonHandler;
    private ClientHandler clientHandler;

    public LendingLibrary(String modid) {
        instance = this;
        MODID = modid;
        modLogger = new LogHelper(modid);
    }

    public void registerObjects(FMLPreInitializationEvent event) {
        modBlocks = event.getAsmData().getAll(ModBlock.class.getCanonicalName());
        modItems = event.getAsmData().getAll(ModItem.class.getCanonicalName());
        modHandlers = event.getAsmData().getAll(Handler.class.getCanonicalName());

        commonHandler = new CommonHandler();
        clientHandler = new ClientHandler();

        commonHandler.preInit(event);
        if (event.getSide() == Side.CLIENT)
            clientHandler.preInit(event);
    }

    public Set<ASMDataTable.ASMData> getModBlocks() {
        return modBlocks;
    }

    public Set<ASMDataTable.ASMData> getModItems() {
        return modItems;
    }

    public Set<ASMDataTable.ASMData> getModHandlers() {
        return modHandlers;
    }

    public CommonHandler getCommonHandler() {
        return commonHandler;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public static LendingLibrary getInstance() {
        return instance;
    }

    public static String getMODID() {
        return MODID;
    }

    public static LogHelper getLogger() {
        return logger;
    }

    public static LogHelper getModLogger() {
        return modLogger;
    }
}
