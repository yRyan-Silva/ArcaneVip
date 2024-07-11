package me.ryan.arcanevip.commands;

import me.lucko.helper.Commands;
import me.ryan.arcanevip.user.UserService;
import me.ryan.stonkscore.utils.Services;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class CommandRemoveVip {

    private final UserService userService;

    public CommandRemoveVip() {
        userService = Services.loadService(UserService.class);
    }

    public void load() {
        Commands.create()
                .assertPermission("arcanevip.admin", "§cVocê não possui permissão para fazer isto.")
                .assertUsage("(player)", "§cSintaxe errada utilize, {usage}")
                .handler(cmd -> {
                    CommandSender s = cmd.sender();
                    String target = cmd.arg(0).parseOrFail(String.class)
                            .toLowerCase(Locale.ROOT);

                    if (userService.containsVip(target)) {
                        userService.removeVip(target);

                        s.sendMessage("§aVip do §f" + target + " §afoi removido com sucesso.");
                    } else
                        s.sendMessage("§cEste jogador não possui nenhum vip ativo.");
                })
                .register("retirarvip", "removevip");
    }

}
