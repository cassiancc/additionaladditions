package dqu.additionaladditions;

import dqu.additionaladditions.item.PocketJukeboxItem;
import dqu.additionaladditions.registry.AdditionalBlocks;
import dqu.additionaladditions.registry.AdditionalEntities;
import dqu.additionaladditions.registry.AdditionalItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

@Environment(EnvType.CLIENT)
public class AdditionalAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.COPPER_PATINA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.ROPE_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AdditionalBlocks.GLOW_STICK_BLOCK, RenderType.cutout());

        EntityRendererRegistry.register(AdditionalEntities.GLOW_STICK_ENTITY_ENTITY_TYPE, ThrownItemRenderer::new);

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.getUseItem() != itemStack ? 0.0F : (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, ResourceLocation.withDefaultNamespace("pulling"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.0F;
            return livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, ResourceLocation.withDefaultNamespace("charged"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            return CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.CROSSBOW_WITH_SPYGLASS, ResourceLocation.withDefaultNamespace("firework"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            var contents = itemStack.get(DataComponents.CHARGED_PROJECTILES);
            if (contents == null) return 0.0F;
            return contents.contains(Items.FIREWORK_ROCKET)? 1.0F : 0.0F;
        });

        FabricModelPredicateProviderRegistry.register(AdditionalItems.POCKET_JUKEBOX_ITEM, ResourceLocation.withDefaultNamespace("disc"), ((itemStack, clientWorld, livingEntity, worldSeed) -> {
            return PocketJukeboxItem.hasDisc(itemStack) ? 1.0F : 0.0F;
        }));

        FabricModelPredicateProviderRegistry.register(AdditionalItems.DEPTH_METER_ITEM, ResourceLocation.withDefaultNamespace("angle"), (itemStack, clientWorld, livingEntity, worldSeed) -> {
            if (livingEntity == null) return 0.3125F;
            Level world = livingEntity.level();
            if (world == null) return 0.3125F;

            float sea = world.getSeaLevel();
            float height = livingEntity.getBlockY();
            float top = world.getMaxBuildHeight();
            float bottom = world.getMinBuildHeight();

            if (height > top) return 0;
            if (height < bottom) return 1;

            if (height >= sea) {
                double val = (height / (2 * (sea - top))) + 0.25 - ((sea + top) / (4 * (sea - top)));
                return (float) val;
            } else {
                double val = (height / (2 * (bottom - sea))) + 0.75 - ((bottom + sea) / (4 * (bottom - sea)));
                return (float) val;
            }
        });
    }
}
