package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.enchantment.PrecisionEnchantment;
import dqu.additionaladditions.enchantment.SpeedEnchantment;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class AdditionalEnchantments {
    public static final ResourceKey<Enchantment> ENCHANTMENT_PRECISION = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.namespace, "precision"));
    public static final ResourceKey<Enchantment> ENCHANTMENT_SPEED = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.namespace, "speed"));

    public static void registerAll() {
    }
}