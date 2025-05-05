package dqu.additionaladditions.mixin;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalPotions;
import net.minecraft.core.Holder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

    @Inject(method = "addVanillaMixes", at = @At("TAIL"))
    private static void registerPotions(PotionBrewing.Builder builder, CallbackInfo ci) {
        if (!Config.initialized) {
            Config.load();
        }

        if (!Config.getBool(ConfigValues.HASTE_POTIONS)) return;
        builder.addMix(Potions.SWIFTNESS, Items.AMETHYST_SHARD, Holder.direct(AdditionalPotions.HASTE_POTION));
        builder.addMix(Holder.direct(AdditionalPotions.HASTE_POTION), Items.REDSTONE, Holder.direct(AdditionalPotions.LONG_HASTE_POTION));
        builder.addMix(Holder.direct(AdditionalPotions.HASTE_POTION), Items.GLOWSTONE_DUST, Holder.direct(AdditionalPotions.STRONG_HASTE_POTION));
        builder.addMix(Potions.STRONG_SWIFTNESS, Items.AMETHYST_SHARD, Holder.direct(AdditionalPotions.STRONG_HASTE_POTION));
        builder.addMix(Potions.LONG_SWIFTNESS, Items.AMETHYST_SHARD, Holder.direct(AdditionalPotions.LONG_HASTE_POTION));
    }
}
