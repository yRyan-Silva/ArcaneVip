package me.ryan.arcanevip.vip;

import me.ryan.arcanevip.config.Configs;
import me.ryan.stonkscore.utils.ConfigUtils;
import me.ryan.stonkscore.utils.Loader;
import me.ryan.stonkscore.utils.Registry;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class VipLoader extends ConfigUtils implements Loader<Vip> {

    @Override
    public Vip adapt(String key, ConfigurationSection section) {
        String name = section.getString("Name").replace("&", "§"),
                permission = section.getString("Permission", null),
                title1 = section.getString("Title.Line1").replace("&", "§"),
                title2 = section.getString("Title.Line2").replace("&", "§");
        ItemStack itemStack = getItemStack(section.getConfigurationSection("Item Stack"));
        List<String> commandsGive = section.getStringList("Commands Give"),
                commandsRemove = section.getStringList("Commands Remove");
        return new Vip(key, name, permission, title1, title2, itemStack, commandsGive, commandsRemove);
    }

    @Override
    public Registry<String, Vip> getRegistry() {
        return Services.loadService(VipRegistry.class);
    }

    @Override
    public ConfigurationSection getSection() {
        return Configs.VIPS.getConfig().getConfigurationSection("Vips");
    }

}
