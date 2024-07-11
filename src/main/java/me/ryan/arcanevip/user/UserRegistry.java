package me.ryan.arcanevip.user;

import com.google.gson.Gson;
import me.ryan.arcanevip.ArcaneVip;
import me.ryan.stonkscore.redis.RedisService;
import me.ryan.stonkscore.utils.Registry;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class UserRegistry extends Registry<String, User> {

    private final RedisService redisService;
    private final String redisKey;
    private final Gson gson;

    public UserRegistry() {
        this.redisService = new RedisService();
        this.redisKey = "arcane_vip";
        this.gson = new Gson();
        startAutoSave();
    }

    public User get(String player) {
        player = player.toLowerCase(Locale.ROOT);
        User user = getByKey(player);
        return user == null ? load(player) : user;
    }

    public User load(String player) {
        player = player.toLowerCase(Locale.ROOT);
        User user = gson.fromJson(redisService.hget(redisKey, player), User.class);
        if (user == null || user.getPlayer() == null || !user.getPlayer().equalsIgnoreCase(player))
            user = new User(player);
        register(user.getPlayer(), user);
        return user;
    }

    public void save(String player, boolean remove) {
        save(get(player), remove);
    }

    public void save(User user, boolean remove) {
        if (user != null) {
            if (user.isDirty()) {
                user.setDirty(false);
                redisService.hset(redisKey, user.getPlayer(), gson.toJson(user));
            }
            if (remove) unregister(user.getPlayer());
        }
    }

    public void saveAll(boolean close) {
        Map<String, String> hash = new HashMap<>(size());
        for (Map.Entry<String, User> entry : entrySet()) {
            User user = entry.getValue();
            if (!user.isDirty()) continue;
            user.setDirty(false);
            hash.put(entry.getKey(), gson.toJson(user));
        }
        if (!hash.isEmpty()) redisService.hset(redisKey, hash);
        if (close) redisService.closeConnection();
    }

    private void startAutoSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                saveAll(false);
            }
        }.runTaskTimerAsynchronously(ArcaneVip.getInstance(), 20 * 30, 20 * 30);
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        Map<String, String> map = redisService.hgetAll(redisKey);
        for (String value : map.values()) {
            User user = gson.fromJson(value, User.class);
            if (user != null && user.getPlayer() != null && !user.getPlayer().isBlank())
                users.add(user);
        }
        return users;
    }

}
