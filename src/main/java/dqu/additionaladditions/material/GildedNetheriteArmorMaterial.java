package dqu.additionaladditions.material;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;

public class GildedNetheriteArmorMaterial {


    private static final int[] BASE_DURABILITY = {481, 555, 592, 407};
    private static final int[] PROTECTION_VALUES = {3, 6, 8, 3};
    public static final String NAME = "gilded_netherite";

    public static final Holder<ArmorMaterial> GILDED_NETHERITE = ModMaterials.register(
            NAME,
            Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
                        enumMap.put(ArmorItem.Type.BOOTS, 3);
                        enumMap.put(ArmorItem.Type.LEGGINGS, 6);
                        enumMap.put(ArmorItem.Type.CHESTPLATE, 8);
                        enumMap.put(ArmorItem.Type.HELMET, 3);
                        enumMap.put(ArmorItem.Type.BODY, 11);
                    }
            ), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5F, 0.1F, () -> Ingredient.of(Items.NETHERITE_INGOT));
}
