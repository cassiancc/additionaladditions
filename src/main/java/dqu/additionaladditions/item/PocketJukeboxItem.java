package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.PocketMusicSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.*;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

public class PocketJukeboxItem extends Item {
    public PocketJukeboxItem(Properties settings) {
        super(settings);
    }

    private static String nbtGetDisc(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("musicdisc")) {
            return tag.get("musicdisc").getAsString();
        } else return null;
    }

    private static void nbtRemoveDisc(ItemStack stack) {
        String disc = nbtGetDisc(stack);
        if (disc == null) return;

        CompoundTag tag = stack.getOrCreateTag();
        tag.remove("musicdisc");
        stack.setTag(tag);
    }

    private static void nbtPutDisc(ItemStack stack, Holder<JukeboxSong> disc) {
        String currentDisc = nbtGetDisc(stack);
        if (currentDisc != null) return;

        ItemContainerContents tag = stack.getComponents().get(DataComponents.CONTAINER);
        tag =("musicdisc", disc);
        stack.setTag(tag);
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
            if (cursor.has(DataComponents.JUKEBOX_PLAYABLE)) {
                var song = JukeboxSong.fromStack(world.registryAccess(), stack).get();
                nbtPutDisc(stack, song);
                cursorStackReference.set(ItemStack.EMPTY);

                if (world.isClientSide()) {
                    if (PocketMusicSoundInstance.instance != null) {
                        PocketMusicSoundInstance.instance.cancel();
                        PocketMusicSoundInstance.instance = null;
                    }

                    PocketMusicSoundInstance.instance = new PocketMusicSoundInstance(song, player, stack, false, 0.8f);
                    PocketMusicSoundInstance.instance.play();
                }
            }
            return true;
        } else {
            ItemStack cursor = cursorStackReference.get();
            if (!cursor.isEmpty()) return false;

            String disc = nbtGetDisc(stack);
            RecordItem discItem = (RecordItem) BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(disc));
            ItemStack discStack = new ItemStack(discItem, 1);
            cursorStackReference.set(discStack);

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
        String disc = nbtGetDisc(stack);
        if (disc == null) {
            tooltip.add(MutableComponent.create(new TranslatableContents("additionaladditions.gui.pocket_jukebox.tooltip", null, new String[]{})).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        } else {
            Item discItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(disc));
            String description = discItem.getDescriptionId() + ".desc";
            tooltip.add(MutableComponent.create(new TranslatableContents(description, null, new String[]{})));
        }
    }
}
