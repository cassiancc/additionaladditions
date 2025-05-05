package dqu.additionaladditions.registry;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.misc.CreativeAdder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;

public class AdditionalMusicDiscs {
    public static final ResourceLocation IDENTIFIER_0308 = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "0308");
    public static final ResourceLocation IDENTIFIER_1007 = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "1007");
    public static final ResourceLocation IDENTIFIER_1507 = ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "1507");

    public static final SoundEvent SOUND_EVENT_0308 = SoundEvent.createVariableRangeEvent(IDENTIFIER_0308);
    public static final SoundEvent SOUND_EVENT_1007 = SoundEvent.createVariableRangeEvent(IDENTIFIER_1007);
    public static final SoundEvent SOUND_EVENT_1507 = SoundEvent.createVariableRangeEvent(IDENTIFIER_1507);

    public static final Item MUSIC_DISC_0308 = new Item(new Item.Properties().jukeboxPlayable(key(IDENTIFIER_0308)).stacksTo(1).rarity(Rarity.RARE));
    public static final Item MUSIC_DISC_1007 = new Item(new Item.Properties().jukeboxPlayable(key(IDENTIFIER_1007)).stacksTo(1).rarity(Rarity.RARE));
    public static final Item MUSIC_DISC_1507 = new Item(new Item.Properties().jukeboxPlayable(key(IDENTIFIER_1507)).stacksTo(1).rarity(Rarity.RARE));

    private static void registerSoundEvents() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, IDENTIFIER_0308, SOUND_EVENT_0308);
        Registry.register(BuiltInRegistries.SOUND_EVENT, IDENTIFIER_1007, SOUND_EVENT_1007);
        Registry.register(BuiltInRegistries.SOUND_EVENT, IDENTIFIER_1507, SOUND_EVENT_1507);
    }

    private static ResourceKey<JukeboxSong> key(ResourceLocation identifier) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, identifier);
    }

    private static void registerDiscs() {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "music_disc_0308"), MUSIC_DISC_0308);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "music_disc_1007"), MUSIC_DISC_1007);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(AdditionalAdditions.NAMESPACE, "music_disc_1507"), MUSIC_DISC_1507);

        CreativeAdder.TOOLS_AND_UTILITIES.add(() -> Config.getBool(ConfigValues.MUSIC_DISCS), Items.MUSIC_DISC_WAIT, MUSIC_DISC_0308, MUSIC_DISC_1007, MUSIC_DISC_1507);
    }

    public static void registerAll() {
        registerSoundEvents();
        registerDiscs();
    }
}
