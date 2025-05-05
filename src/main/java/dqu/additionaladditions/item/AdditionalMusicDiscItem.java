package dqu.additionaladditions.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;

public class AdditionalMusicDiscItem extends Item {
    public AdditionalMusicDiscItem(int comparatorOutput, ResourceKey<JukeboxSong> sound, Item.Properties settings, int length) {
        super(settings.jukeboxPlayable(sound).stacksTo(1));
    }
}
