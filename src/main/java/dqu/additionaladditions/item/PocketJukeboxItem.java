package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.PocketMusicSoundInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

public class PocketJukeboxItem extends Item {
    public PocketJukeboxItem(Properties settings) {
        super(settings);
    }

    private static ItemStack nbtGetDisc(ItemStack stack) {
        var contents = stack.getComponents().get(DataComponents.BUNDLE_CONTENTS);
        if (contents != null && contents.size() == 1)
            return contents.getItemUnsafe(0);
        else return null;
    }

    private static void nbtRemoveDisc(ItemStack stack) {
        ItemStack disc = nbtGetDisc(stack);
        stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(List.of()));
    }

    private static void nbtPutDisc(ItemStack stack, ItemStack disc) {
        ItemStack currentDisc = nbtGetDisc(stack);
        if (currentDisc != null) return;

        BundleContents bundleContents = new BundleContents(List.of(disc));
        stack.set(DataComponents.BUNDLE_CONTENTS, bundleContents);
    }

    public static boolean hasDisc(ItemStack stack) {
        return (nbtGetDisc(stack) != null);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) return false;
        if (clickType != ClickAction.SECONDARY) return false;
        Level world = player.level();

        if (nbtGetDisc(stack) == null) {
            ItemStack cursor = cursorStackReference.get();
            if (cursor.getItem().components().has(DataComponents.JUKEBOX_PLAYABLE)) {
                nbtPutDisc(stack, cursor);
                cursorStackReference.set(ItemStack.EMPTY);

                if (world.isClientSide()) {
                    if (PocketMusicSoundInstance.instance != null) {
                        PocketMusicSoundInstance.instance.cancel();
                        PocketMusicSoundInstance.instance = null;
                    }

                    PocketMusicSoundInstance.instance = new PocketMusicSoundInstance(JukeboxSong.fromStack(player.level().registryAccess(), cursor).get(), player, stack, false, 0.8f);
                    PocketMusicSoundInstance.instance.play();
                }
            }
            return true;
        } else {
            ItemStack cursor = cursorStackReference.get();
            if (!cursor.isEmpty()) return false;

            ItemStack disc = nbtGetDisc(stack);
            cursorStackReference.set(disc);

            if (world.isClientSide()) {
                if (PocketMusicSoundInstance.instance != null) {
                    PocketMusicSoundInstance.instance.cancel();
                    PocketMusicSoundInstance.instance = null;
                }
            }

            nbtRemoveDisc(stack);
            return true;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        ItemStack disc = nbtGetDisc(stack);
        if (disc != null && disc.has(DataComponents.JUKEBOX_PLAYABLE)) {
            JukeboxPlayable jukeboxPlayable = disc.get(DataComponents.JUKEBOX_PLAYABLE);
            jukeboxPlayable.song().unwrap(tooltipContext.registries()).ifPresent(holder -> {
                MutableComponent mutableComponent = holder.value().description().copy();
                ComponentUtils.mergeStyles(mutableComponent, Style.EMPTY.withColor(ChatFormatting.GRAY));
                tooltip.add(mutableComponent);
            });
        } else {
            tooltip.add(Component.translatable("additionaladditions.gui.pocket_jukebox.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }
}