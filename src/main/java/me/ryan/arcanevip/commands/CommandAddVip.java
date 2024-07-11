package me.ryan.arcanevip.commands;

import me.lucko.helper.Commands;
import me.ryan.arcanevip.vip.Vip;
import me.ryan.arcanevip.vip.VipRegistry;
import me.ryan.stonkscore.utils.InventoryUtil;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddVip {

    private final VipRegistry vipRegistry;

    public CommandAddVip() {
        vipRegistry = Services.loadService(VipRegistry.class);
    }

    public void load() {
        Commands.create()
                .assertPermission("arcanevip.admin", "§cVocê não possui permissão para fazer isto.")
                .assertUsage("(player) (vip) (days/eterno) (stacks)", "§cSintaxe errada utilize, {usage}")
                .handler(cmd -> {
                    CommandSender s = cmd.sender();

                    Player target;
                    try {
                        target = cmd.arg(0).parseOrFail(Player.class);
                    } catch (Exception e) {
                        s.sendMessage("§cEste jogador não existe ou está off-line.");
                        return;
                    }

                    String vipKey = cmd.arg(1).parseOrFail(String.class);

                    int days;
                    try {
                        if (cmd.arg(2).parseOrFail(String.class).equalsIgnoreCase("eterno"))
                            days = Integer.MAX_VALUE;
                        else days = cmd.arg(2).parseOrFail(Integer.class);
                    } catch (Exception e) {
                        s.sendMessage("§cA quantidade de dias deve ser apenas números ou eterno.");
                        return;
                    }

                    int stacks;
                    try {
                        stacks = Math.max(1, cmd.arg(3).parseOrFail(Integer.class));
                    } catch (Exception e) {
                        s.sendMessage("§cA quantidade de stacks deve ser apenas números.");
                        return;
                    }

                    Vip vip = vipRegistry.getByKey(vipKey);
                    if (vip == null) {
                        s.sendMessage("§cNão existe este vip §f" + vipKey);
                    } else {
                        InventoryUtil.addItemToPlayerOrDrop(target, vip.getItemStack(days, stacks));
                        if (s instanceof Player)
                            s.sendMessage("§aVip enviado com sucesso ao jogador §f" + target.getName());
                    }

                })
                .register("darvip", "givevip");
    }

}
