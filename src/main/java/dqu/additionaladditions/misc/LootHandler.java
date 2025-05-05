package dqu.additionaladditions.misc;

import dqu.additionaladditions.AdditionalAdditions;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
    }

    private record LootEntry(
            ResourceKey table,
            Supplier<Boolean> condition,
            LootPool.Builder pool
    ) {}
}
