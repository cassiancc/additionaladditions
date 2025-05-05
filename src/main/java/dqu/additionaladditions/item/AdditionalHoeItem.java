package dqu.additionaladditions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dqu.additionaladditions.behaviour.BehaviourManager;
import dqu.additionaladditions.behaviour.BehaviourValues;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class AdditionalHoeItem extends HoeItem {
    private Multimap<Attribute, AttributeModifier> modifiers = null;
    private int previousLoads = BehaviourManager.loads;
    private final float attackSpeed;

    public AdditionalHoeItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        super(material, settings);
        this.attackSpeed = attackSpeed;
    }

    private void rebuildModifiers() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE.value(), new AttributeModifier(BASE_ATTACK_DAMAGE_ID, getDamage(), AttributeModifier.Operation.ADD_VALUE));
        builder.put(Attributes.ATTACK_SPEED.value(), new AttributeModifier(BASE_ATTACK_SPEED_ID, getAttackSpeed(), AttributeModifier.Operation.ADD_VALUE));
        this.modifiers = builder.build();
    }

    private void rebuildModifiersIfNeeded() {
        if (modifiers == null) {
            rebuildModifiers();
            return;
        }
        if (previousLoads != BehaviourManager.loads) {
            previousLoads = BehaviourManager.loads;
            rebuildModifiers();
        }
    }

//    @Override
//    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
//        rebuildModifiersIfNeeded();
//        return equipmentSlot == EquipmentSlot.MAINHAND ? this.modifiers : super.getDefaultAttributeModifiers();
//    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_HOE) ? getMiningSpeed() : super.getDestroySpeed(itemStack, blockState);
    }

    public float getDamage() {
        String path = getTier().toString().toLowerCase(Locale.ROOT) + "/hoe";
        Float damage = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_DAMAGE);
        return (damage == null) ? super.components().get(DataComponents.DAMAGE) : damage;
    }

    public float getAttackSpeed() {
        String path = getTier().toString().toLowerCase(Locale.ROOT) + "/hoe";
        Float speed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.ATTACK_SPEED);
        return (speed == null) ? attackSpeed : speed;
    }

    public float getMiningSpeed() {
        String path = getTier().toString().toLowerCase(Locale.ROOT) + "/hoe";
        Float speed = BehaviourManager.INSTANCE.getBehaviourValue(path, BehaviourValues.MINING_SPEED);
        return (speed == null) ? this.attackSpeed : speed;
    }
}
