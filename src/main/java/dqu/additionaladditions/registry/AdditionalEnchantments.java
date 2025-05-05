package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class AdditionalEnchantments {
    public static final ResourceKey<Enchantment> ENCHANTMENT_PRECISION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "precision"));
    public static final ResourceKey<Enchantment> ENCHANTMENT_SPEED = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "speed"));

    public static void registerAll() {
    }
}