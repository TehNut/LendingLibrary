package tehnut.lib.test.block;

import net.minecraft.block.material.Material;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.Used;
import tehnut.lib.block.base.BlockBoolean;
import tehnut.lib.block.item.ItemBlockInteger;
import tehnut.lib.iface.IVariantProvider;
import tehnut.lib.test.LendingLibraryTest;

import java.util.ArrayList;
import java.util.List;

@ModBlock(name = "BlockBooleanBadTest", itemBlock = ItemBlockInteger.class)
@Used
public class BlockBooleanBadTest extends BlockBoolean implements IVariantProvider {

    public BlockBooleanBadTest() {
        super(Material.coral);

        setCreativeTab(LendingLibraryTest.tabTest);
        setUnlocalizedName("test.bad.boolean");
    }

    @Override
    public List<Pair<Integer, String>> getVariants() {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(Pair.of(0, "enabled=false"));
        ret.add(Pair.of(1, "enabled=true"));
        return ret;
    }
}