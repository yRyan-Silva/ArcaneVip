package me.ryan.arcanevip;

import br.com.blecaute.inventory.InventoryHelper;
import me.ryan.arcanevip.commands.CommandAddVip;
import me.ryan.arcanevip.commands.CommandRemoveVip;
import me.ryan.arcanevip.commands.CommandTimeVip;
import me.ryan.arcanevip.config.Configs;
import me.ryan.arcanevip.events.PlayerInteractListener;
import me.ryan.arcanevip.events.PlayerQuitListener;
import me.ryan.arcanevip.task.VipTaskVerify;
import me.ryan.arcanevip.user.UserService;
import me.ryan.arcanevip.user.UserRegistry;
import me.ryan.arcanevip.vip.VipLoader;
import me.ryan.arcanevip.vip.VipRegistry;
import me.ryan.stonkscore.server.ServerPlugin;

public class ArcaneVip extends ServerPlugin {

    @Override
    public void load() {
        Configs.init();
    }

    @Override
    public void enable() {
        InventoryHelper.enable(this);

        new VipTaskVerify().runTaskTimerAsynchronously(this, 20, 20);
    }

    @Override
    public void disable() {

    }

    @Override
    public void loadCommands() {
        new CommandAddVip().load();
        new CommandRemoveVip().load();
        new CommandTimeVip().load();
    }

    @Override
    public void loadEvents() {
        new PlayerInteractListener().load();
        new PlayerQuitListener().load();
    }

    @Override
    public void loadLoaders() {
        new VipLoader().load();
    }

    @Override
    public void loadServices() {
        registerService(UserRegistry.class, new UserRegistry());

        registerService(VipRegistry.class, new VipRegistry());

        registerService(UserService.class, new UserService());
    }

    public static ArcaneVip getInstance() {
        return getPlugin(ArcaneVip.class);
    }

}
