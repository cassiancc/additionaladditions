package dqu.additionaladditions.item;

import dqu.additionaladditions.AdditionalAdditions;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class AdditionalSmithingTemplate extends SmithingTemplateItem {
    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;

    private AdditionalSmithingTemplate(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons);
    }

    public static SmithingTemplateItem create(String id, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        var appliesTo = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "smithing_template."+id+".applies_to"))).withStyle(DESCRIPTION_FORMAT);
        var upgradeDescription = Component.translatable(Util.makeDescriptionId("upgrade", ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, id))).withStyle(TITLE_FORMAT);
        var ingredients = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "smithing_template."+id+".ingredients"))).withStyle(DESCRIPTION_FORMAT);
        var baseSlotDescription = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "smithing_template."+id+".base_slot_description")));
        var additionsSlotDescription = Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "smithing_template."+id+".additions_slot_description")));

        return new AdditionalSmithingTemplate(
                appliesTo,
                ingredients,
                upgradeDescription,
                baseSlotDescription,
                additionsSlotDescription,
                baseSlotEmptyIcons,
                additionalSlotEmptyIcons
        );
    }

    public static List<ResourceLocation> iconsEquipment() {
        return List.of(
                SmithingIcon.HELMET.location(),
                SmithingIcon.CHESTPLATE.location(),
                SmithingIcon.LEGGINGS.location(),
                SmithingIcon.BOOTS.location(),
                SmithingIcon.HOE.location(),
                SmithingIcon.AXE.location(),
                SmithingIcon.PICKAXE.location(),
                SmithingIcon.SWORD.location(),
                SmithingIcon.SHOVEL.location()
        );
    }

    public enum SmithingIcon {
        HELMET(ResourceLocation.withDefaultNamespace("item/empty_armor_slot_helmet")),
        CHESTPLATE(ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate")),
        LEGGINGS(ResourceLocation.withDefaultNamespace("item/empty_armor_slot_leggings")),
        BOOTS(ResourceLocation.withDefaultNamespace("item/empty_armor_slot_boots")),
        HOE(ResourceLocation.withDefaultNamespace("item/empty_slot_hoe")),
        AXE(ResourceLocation.withDefaultNamespace("item/empty_slot_axe")),
        SWORD(ResourceLocation.withDefaultNamespace("item/empty_slot_sword")),
        SHOVEL(ResourceLocation.withDefaultNamespace("item/empty_slot_shovel")),
        PICKAXE(ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe")),
        INGOT(ResourceLocation.withDefaultNamespace("item/empty_slot_ingot")),
        REDSTONE_DUST(ResourceLocation.parse("item/empty_slot_redstone_dust")),
        QUARTZ(ResourceLocation.withDefaultNamespace("item/empty_slot_quartz")),
        EMERALD(ResourceLocation.withDefaultNamespace("item/empty_slot_emerald")),
        DIAMOND(ResourceLocation.withDefaultNamespace("item/empty_slot_diamond")),
        LAPIS_LAZULI(ResourceLocation.withDefaultNamespace("item/empty_slot_lapis_lazuli")),
        AMETHYST_SHARD(ResourceLocation.withDefaultNamespace("item/empty_slot_amethyst_shard")),
        RING(ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "item/empty_slot_ring")),
        ALLOY(ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "item/empty_slot_alloy"));

        final ResourceLocation resourceLocation;

        SmithingIcon(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public ResourceLocation location() {
            return resourceLocation;
        }
    }
}
