package dqu.additionaladditions.mixin.behaviour;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import dqu.additionaladditions.material.GildedNetheriteArmorMaterial;
import dqu.additionaladditions.material.RoseGoldArmorMaterial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public abstract class ItemMixin {
//    @Inject(method = "durability", at = @At("RETURN"), cancellable = true)
//    private void modifyItemDurability(CallbackInfoReturnable<Integer> cir) {
//
//        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey((Item) (Object) this);
//        if (resourceLocation.getNamespace().equals(AdditionalAdditions.NAMESPACE)) {
//            String path = resourceLocation.getPath();
//            if (path.startsWith(GildedNetheriteArmorMaterial.NAME)) {
//                String name = GildedNetheriteArmorMaterial.NAME + "/" + path.substring(GildedNetheriteArmorMaterial.NAME.length() + 1);
//                Integer durability = BehaviourManager.INSTANCE.getBehaviourValue(name, BehaviourValues.DURABILITY);
//                if (durability != null) cir.setReturnValue(durability);
//            } else if (path.startsWith(RoseGoldArmorMaterial.NAME)) {
//                String name = RoseGoldArmorMaterial.NAME + "/" + path.substring(RoseGoldArmorMaterial.NAME.length() + 1);
//                Integer durability = BehaviourManager.INSTANCE.getBehaviourValue(name, BehaviourValues.DURABILITY);
//                if (durability != null) cir.setReturnValue(durability);
//            }
//        }
//    }
}
