package me.ryan.arcanevip.events;

import me.lucko.helper.Events;
import me.ryan.arcanevip.user.UserService;
import me.ryan.arcanevip.utils.NBTTagType;
import me.ryan.arcanevip.vip.Vip;
import me.ryan.arcanevip.vip.VipRegistry;
import me.ryan.stonkscore.utils.NBTTag;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener {

    private final UserService userService;
    private final VipRegistry vipRegistry;

    public PlayerInteractListener() {
        userService = Services.loadService(UserService.class);
        vipRegistry = Services.loadService(VipRegistry.class);
    }

    public void load() {
        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR ||
                        event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .handler(event -> {
                    Player p = event.getPlayer();
                    ItemStack item = event.getItem();

                    String vipKey;
                    if (item != null && item.hasItemMeta() &&
                            (vipKey = NBTTag.getNBTString(item.getItemMeta(), NBTTagType.VIP_ITEM_KEY.getKey())) != null) {
                        event.setCancelled(true);
                        int days = NBTTag.getNBTInt(item.getItemMeta(), NBTTagType.VIP_ITEM_DAYS.getKey());
                        if (p.isSneaking() && days != Integer.MAX_VALUE) days *= item.getAmount();

                        Vip vip = vipRegistry.getByKey(vipKey);
                        if (vip == null) {
                            p.sendMessage("§cOcorreu um erro ao tentar ativar este vip, por favor contatar um staff.");
                            return;
                        }

                        if (vip.getPermission() != null && !vip.getPermission().isBlank() && !p.hasPermission(vip.getPermission())) {
                            p.sendMessage("§cVocê não possui permissão para ativar este vip.");
                            return;
                        }

                        if (userService.addVip(p.getName(), vip, days)) {
                            if ((p.isSneaking() && days != Integer.MAX_VALUE) || item.getAmount() <= 1)
                                p.setItemInHand(null);
                            else item.setAmount(item.getAmount() - 1);

                            p.sendMessage("§aVocê ativou §f" + (days == Integer.MAX_VALUE ? "eterno§a" : days + " §adias") + " de vip com sucesso.");
                        } else
                            p.sendMessage("§cVocê já possui outro vip ativo ou seu vip é eterno.");
                    }

                });
    }

}
