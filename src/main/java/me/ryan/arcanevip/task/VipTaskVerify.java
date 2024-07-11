package me.ryan.arcanevip.task;

import me.ryan.arcanevip.user.User;
import me.ryan.arcanevip.user.UserService;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.scheduler.BukkitRunnable;

public class VipTaskVerify extends BukkitRunnable {

    private final UserService userService;

    public VipTaskVerify() {
        userService = Services.loadService(UserService.class);
    }

    @Override
    public void run() {
        for (User user : userService.getAllUser())
            if (user.containsVip() && user.expiredVip()) {
                userService.removeVip(user.getPlayer());
                userService.save(user, false);
            }
    }

}
