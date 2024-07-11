package me.ryan.arcanevip.config;

import lombok.Getter;
import lombok.Setter;
import me.ryan.arcanevip.ArcaneVip;
import me.ryan.stonkscore.utils.RawConfig;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

@Getter
public enum Configs {

    VIPS, INVENTORIES;

    @Setter
    private RawConfig rawConfig;

    public FileConfiguration getConfig() {
        return this.rawConfig.getConfig();
    }

    public static void init() {
        for (Configs configs : values()) {
            configs.setRawConfig(new RawConfig(ArcaneVip.getInstance(),
                    configs.name().toLowerCase(Locale.ROOT) + ".yml"));
            configs.getRawConfig().saveDefaultConfig();
        }
    }

    public void reloadConfig() {
        this.rawConfig.reloadConfig();
    }

    public void saveConfig() {
        this.rawConfig.saveConfig();
    }

}
