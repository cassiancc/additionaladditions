package dqu.additionaladditions.misc;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;

@Environment(EnvType.CLIENT)
public class PocketMusicSoundInstance extends AbstractTickableSoundInstance {
    private final Player playerEntity;
    private final ItemStack stack;
    public static PocketMusicSoundInstance instance;

    public PocketMusicSoundInstance(Holder<JukeboxSong> soundEvent, Player playerEntity, ItemStack stack, boolean repeat, float volume) {
        super(soundEvent.value().soundEvent().value(), SoundSource.RECORDS, RandomSource.create());
        this.playerEntity = playerEntity;
        this.stack = stack;
        this.looping = repeat;
        this.volume = volume;
        this.x = this.playerEntity.getX();
        this.y = this.playerEntity.getY();
        this.z = this.playerEntity.getZ();
    }

    @Override
    public void tick() {
        ItemStack cursor = this.playerEntity.containerMenu.getCarried();
        boolean hasDisc = ItemStack.matches(cursor, stack) || this.playerEntity.getInventory().contains(stack);

        if (this.playerEntity.isDeadOrDying() || !hasDisc || !Config.getBool(ConfigValues.POCKET_JUKEBOX)) {
            this.cancel();
        } else {
            this.x = this.playerEntity.getX();
            this.y = this.playerEntity.getY();
            this.z = this.playerEntity.getZ();
        }
    }

    public void cancel() {
        this.stop();
    }

    public void play() {
        Minecraft.getInstance().getSoundManager().play(this);
    }
 }
