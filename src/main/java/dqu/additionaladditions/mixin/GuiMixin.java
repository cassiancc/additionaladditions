package dqu.additionaladditions.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.registry.AdditionalItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @WrapOperation(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"))
    private boolean spyglassOverlay(LocalPlayer instance, Operation<Boolean> original) {
        return AdditionalAdditions.zoom || original.call(instance);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void depthMeterMessage(CallbackInfo ci) {
        if (!Config.getBool(ConfigValues.DEPTH_METER, "enabled")) return;
        if (minecraft.player.isHolding(AdditionalItems.DEPTH_METER_ITEM)) {
            if (Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
                String level = String.valueOf((int) minecraft.player.getY());
                minecraft.player.displayClientMessage(MutableComponent.create(new TranslatableContents("depth_meter.elevation", null, new String[]{level})), true);
            }
        }
    }
}
