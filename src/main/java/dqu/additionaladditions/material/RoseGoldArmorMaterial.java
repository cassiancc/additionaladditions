package dqu.additionaladditions.material;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.Locale;

public class RoseGoldArmorMaterial {
    private static final int[] BASE_DURABILITY = {264, 384, 360, 312};
    private static final int[] PROTECTION_VALUES = {2, 6, 7, 2};
    public static final String NAME = "rose_gold";

    public static final Holder<ArmorMaterial> ROSE_GOLD = ModMaterials.register(
            NAME,
            Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
                        enumMap.put(ArmorItem.Type.BOOTS, 2);
                        enumMap.put(ArmorItem.Type.LEGGINGS, 6);
                        enumMap.put(ArmorItem.Type.CHESTPLATE, 7);
                        enumMap.put(ArmorItem.Type.HELMET, 2);
                        enumMap.put(ArmorItem.Type.BODY, 10);
                    }
            ), 17, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0F, () -> Ingredient.of(Items.COPPER_INGOT));
}
