package tehnut.lib.forge.feature;

import com.google.common.base.Strings;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.forge.feature.config.FeatureConfigManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * The core handler for Features. You will need to manually call the initialization methods at each of the life cycle
 * events.
 */
public class FeatureHandler {

    private final Configuration configuration;
    private final List<Pair<IFeature, Feature>> features;

    /**
     * Creates a new FeatureHandler. All feature related config values are placed into the {@code feature} category of
     * the given Configuration object.
     *
     * @param configuration - The configuration object to read and write feature values from
     * @param features - A list of the features to be loaded
     */
    public FeatureHandler(Configuration configuration, List<Pair<IFeature, Feature>> features) {
        this.configuration = configuration;
        this.features = features;
    }

    public void preInit(FMLPreInitializationEvent event) {
        Iterator<Pair<IFeature, Feature>> iterator = features.iterator();
        while (iterator.hasNext()) {
            Pair<IFeature, Feature> feature = iterator.next();

            Property prop = configuration.get("feature", feature.getRight().name(), feature.getRight().enabledByDefault());
            prop.setRequiresMcRestart(true);
            String comment = "Enable the " + feature.getRight().name() + " feature.";
            if (!Strings.isNullOrEmpty(feature.getRight().description()))
                comment += "\n" + feature.getRight().description();
            comment += "\n[default: " + feature.getRight().enabledByDefault() + "]";

            prop.setComment(comment);
            if (!prop.getBoolean())
                iterator.remove();
        }

        Collections.sort(features, new Comparator<Pair<IFeature, Feature>>() {
            @Override
            public int compare(Pair<IFeature, Feature> o1, Pair<IFeature, Feature> o2) {
                int priority1 = o1.getRight().priority();
                int priority2 = o2.getRight().priority();

                if (priority1 < priority2)
                    return -1;
                if (priority1 == priority2)
                    return 0;
                return 1;
            }
        });

        for (Pair<IFeature, Feature> feature : features) {
            if (feature.getRight().handleConfig())
                FeatureConfigManager.injectConfig(configuration, feature.getLeft(), feature.getRight());

            feature.getLeft().preInit(event);
        }
    }

    public void init(FMLInitializationEvent event) {
        for (Pair<IFeature, Feature> feature : features)
            feature.getLeft().init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        for (Pair<IFeature, Feature> feature : features)
            feature.getLeft().postInit(event);
    }
}