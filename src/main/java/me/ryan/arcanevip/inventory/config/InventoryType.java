package me.ryan.arcanevip.inventory.config;

import lombok.Getter;
import me.ryan.arcanevip.config.Configs;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public enum InventoryType {

    VIP;

    private final InventoryConfig inventoryConfig;

    InventoryType() {
        ConfigurationSection section = Configs.INVENTORIES.getConfig();
        String name = WordUtils.capitalizeFully(name().replace("_", " ")),
                path = "Inventory." + name + ".";

        this.inventoryConfig = new InventoryConfig(section.getString(path  + "Name").replace("&", "§"),
                section.getInt(path + "Lines"));
    }

}
