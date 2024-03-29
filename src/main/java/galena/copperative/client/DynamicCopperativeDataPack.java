package galena.copperative.client;

import com.google.common.base.Preconditions;
import galena.copperative.Copperative;
import galena.copperative.config.CommonConfig;
import galena.copperative.data.CRecipes;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DynamicCopperativeDataPack extends DynServerResourcesProvider {

    public static final DynamicCopperativeDataPack INSTANCE = new DynamicCopperativeDataPack();

    private final Map<ResourceLocation, FinishedRecipe> recipes = new HashMap<>();

    private void registerOverride(FinishedRecipe recipe, ItemLike item) {
        var id = Preconditions.checkNotNull(ForgeRegistries.ITEMS.getKey(item.asItem()));
        recipes.put(id, recipe);
    }

    private DynamicCopperativeDataPack() {
        super(new DynamicDataPack(new ResourceLocation(Copperative.MOD_ID, "generated"), Pack.Position.BOTTOM, true, true));
        DynamicCopperativeResourcePack.OVERRIDDEN_NAMESPACES.forEach(dynamicPack::addNamespaces);
    }

    @Override
    public Logger getLogger() {
        return Copperative.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {
        if (recipes.isEmpty()) {
            CRecipes.registerOverrides(this::registerOverride);
        }

        CommonConfig.getOverwrittenBlocks(CommonConfig.OverrideTarget.RECIPE).forEach(this::enableBlockOverwrite);
        enableBlockOverwrite(Items.SPYGLASS);
    }

    private void enableBlockOverwrite(ItemLike item) {
        var id = Preconditions.checkNotNull(ForgeRegistries.ITEMS.getKey(item.asItem()));
        var recipe = recipes.get(id);
        if (recipe != null) dynamicPack.addRecipe(recipe);
    }

}
