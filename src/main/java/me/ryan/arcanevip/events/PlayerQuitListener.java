package me.ryan.arcanevip.events;

import me.lucko.helper.Events;
import me.ryan.arcanevip.user.UserService;
import me.ryan.stonkscore.events.PlayerPreQuitEvent;
import me.ryan.stonkscore.utils.Services;

public class PlayerQuitListener {

    private final UserService userService;

    public PlayerQuitListener() {
        userService = Services.loadService(UserService.class);
    }

    public void load() {
        Events.subscribe(PlayerPreQuitEvent.class)
                .handler(event -> userService.save(event.getPlayer().getName(), true));
    }

}
