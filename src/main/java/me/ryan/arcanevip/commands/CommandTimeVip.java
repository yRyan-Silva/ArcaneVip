package me.ryan.arcanevip.commands;

import me.lucko.helper.Commands;
import me.ryan.arcanevip.inventory.InventoryVip;

public class CommandTimeVip {

    public void load() {
        Commands.create()
                .assertPlayer()
                .handler(cmd -> new InventoryVip(cmd.sender()))
                .register("tempovip", "timevip");
    }

}
