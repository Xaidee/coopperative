package galena.copperative.index;

import galena.copperative.Copperative;
import galena.copperative.content.inventory.menu.SolderingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Copperative.MOD_ID);

    public static final RegistryObject<MenuType<SolderingMenu>> SOLDERING_MENU = MENU_TYPES.register("soldering_table", () -> new MenuType<>(SolderingMenu::new));
}
