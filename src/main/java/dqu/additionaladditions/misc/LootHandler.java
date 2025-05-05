package dqu.additionaladditions.misc;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static dqu.additionaladditions.registry.AdditionalItems.CHICKEN_NUGGET;

public class LootHandler {
    private static final List<LootEntry> entries = new ArrayList<>();

    public static void register(ResourceKey table, Supplier<Boolean> condition, LootPool.Builder pool) {
        LootEntry entry = new LootEntry(table, condition, pool);
        entries.add(entry);
    }

    public static void register(List<ResourceKey> tables, Supplier<Boolean> condition, LootPool.Builder pool) {
        for (ResourceKey table : tables) {
            register(table, condition, pool);
        }
    }

    public static void postInit() {
        AdditionalAdditions.LOGGER.info("[" + AdditionalAdditions.NAMESPACE + "] Adding " + entries.size() + " loot pools");
    }

    public static void handle(ResourceKey<LootTable> lootTableResourceKey, LootTable.Builder table, LootTableSource source, HolderLookup.Provider provider) {
        for (LootEntry entry : entries) {
            if (entry.table.equals(lootTableResourceKey)) {
                if (!entry.condition.get()) continue;

                table.withPool(entry.pool);
            }
        }
        if ((lootTableResourceKey.equals(EntityType.ZOMBIE.getDefaultLootTable()) || lootTableResourceKey.equals(EntityType.CREEPER.getDefaultLootTable())) && Config.getBool(ConfigValues.CHICKEN_NUGGET)) {
            table.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(provider, 0.025f, 0.01f))
                    .add(LootItem.lootTableItem(CHICKEN_NUGGET))
            );
        }

    }

    private record LootEntry(
            ResourceKey table,
            Supplier<Boolean> condition,
            LootPool.Builder pool
    ) {}
}
