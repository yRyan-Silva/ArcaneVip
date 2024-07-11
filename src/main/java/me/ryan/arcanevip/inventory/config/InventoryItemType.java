package me.ryan.arcanevip.inventory.config;

import lombok.Getter;
import me.ryan.arcanevip.config.Configs;
import me.ryan.stonkscore.utils.ConfigUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public enum InventoryItemType {

    VIP__VIP, VIP__VIP_ETERNAL, VIP__VIP_NULL;

    @Getter
    private final int slot;
    private final ItemStack itemStack;

    InventoryItemType() {
        ConfigurationSection section = Configs.INVENTORIES.getConfig();
        String inv = WordUtils.capitalizeFully(name().split("__")[0].replace("_", " ")),
                name = WordUtils.capitalizeFully(name().split("__")[1].replace("_", " ")),
                path = "Inventory." + inv + ".Items." + name + ".";
        this.slot = section.getInt(path + "Slot", 0);
        this.itemStack = section.getConfigurationSection(path).contains("Name") ?
                ConfigUtils.getItemStack(section.getConfigurationSection(path)) : null;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

}
