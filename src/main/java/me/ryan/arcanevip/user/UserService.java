package me.ryan.arcanevip.user;

import me.ryan.arcanevip.vip.Vip;
import me.ryan.arcanevip.vip.VipRegistry;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class UserService {

    private final UserRegistry userRegistry;
    private final VipRegistry vipRegistry;

    public UserService() {
        userRegistry = Services.loadService(UserRegistry.class);
        vipRegistry = Services.loadService(VipRegistry.class);
    }

    public void save(User user, boolean remove) {
        userRegistry.save(user, remove);
    }

    public void save(String player, boolean remove) {
        userRegistry.save(player, remove);
    }

    public boolean containsVip(String player) {
        return userRegistry.get(player).containsVip();
    }

    public String getVipKey(String player) {
        return userRegistry.get(player).getVipKey();
    }

    public boolean addVip(String player, Vip vip, int days) {
        User user = userRegistry.get(player);
        if ((user.containsVip() && !vip.getKey().equalsIgnoreCase(user.getVipKey())) ||
                user.isEternal()) return false;

        if (vip.getKey().equalsIgnoreCase(user.getVipKey())) {
            if (days == Integer.MAX_VALUE) user.setEternal(true);
            else user.setVipExpiration(user.getVipExpiration().plusDays(days));
        } else {
            for (String cmd : vip.getCommandsGive())
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        cmd.replace("{player}", player));

            if (vip.getTitle1() != null && vip.getTitle2() != null &&
                    !vip.getTitle1().isBlank() && !vip.getTitle2().isBlank())
                Bukkit.getOnlinePlayers().forEach(p -> p.sendTitle(vip.getTitle1().replace("{player}", player),
                        vip.getTitle2().replace("{player}", player)));

            user.setVipKey(vip.getKey());
            if (days == Integer.MAX_VALUE) user.setEternal(true);
            else user.setVipExpiration(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(days));
        }

        return true;
    }

    public void removeVip(String player) {
        User user = userRegistry.get(player);
        removeVip(user);
    }

    public void removeVip(User user) {
        if (!user.containsVip()) return;
        Vip vip = vipRegistry.getByKey(user.getVipKey());
        if (vip != null)
            for (String cmd : vip.getCommandsRemove())
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        cmd.replace("{player}", user.getPlayer()));
        user.removeVip();
    }

    public List<User> getAllUser() {
        return userRegistry.getAllUser();
    }

    public User get(String player) {
        return userRegistry.get(player);
    }

}
