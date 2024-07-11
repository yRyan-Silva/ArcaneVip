package me.ryan.arcanevip.inventory;

import br.com.blecaute.inventory.InventoryBuilder;
import br.com.blecaute.inventory.type.InventoryItem;
import me.ryan.arcanevip.inventory.config.InventoryConfig;
import me.ryan.arcanevip.inventory.config.InventoryItemType;
import me.ryan.arcanevip.inventory.config.InventoryType;
import me.ryan.arcanevip.user.User;
import me.ryan.arcanevip.user.UserService;
import me.ryan.arcanevip.vip.Vip;
import me.ryan.arcanevip.vip.VipRegistry;
import me.ryan.stonkscore.utils.ItemBuilder;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.entity.Player;

public class InventoryVip {

    private static final UserService userService;
    private static final VipRegistry vipRegistry;

    static {
        userService = Services.loadService(UserService.class);
        vipRegistry = Services.loadService(VipRegistry.class);
    }

    public InventoryVip(Player p) {
        InventoryConfig invConfig = InventoryType.VIP.getInventoryConfig();
        InventoryBuilder<InventoryItem> inv = InventoryBuilder.of(invConfig.getTitle(), invConfig.getLines());

        InventoryItemType vipItem = InventoryItemType.VIP__VIP,
                vipEternal = InventoryItemType.VIP__VIP_ETERNAL,
                vipNull = InventoryItemType.VIP__VIP_NULL;

        User user = userService.get(p.getName());
        if (user.containsVip()) {
            Vip vip = vipRegistry.getByKey(user.getVipKey());

            if (user.isEternal())
                inv.withItem(vipEternal.getSlot(), new ItemBuilder(vipEternal.getItemStack())
                        .setSkullOwner(p.getName())
                        .replaceName("{name}", vip.getName())
                        .build());
            else
                inv.withItem(vipItem.getSlot(), new ItemBuilder(vipItem.getItemStack())
                        .setSkullOwner(p.getName())
                                .replaceName("{name}", vip.getName())
                                .replaceLore("{expiration}", user.getVipTime())
                        .build());
        } else
            inv.withItem(vipNull.getSlot(), new ItemBuilder(vipNull.getItemStack())
                    .setSkullOwner(p.getName()).build());

        inv.open(p);
    }

}
