package me.ryan.arcanevip.vip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ryan.arcanevip.utils.NBTTagType;
import me.ryan.stonkscore.utils.ItemBuilder;
import me.ryan.stonkscore.utils.NBTTag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
public class Vip {

    private final String key, name, permission, title1, title2;
    private final ItemStack itemStack;
    private final List<String> commandsGive, commandsRemove;

    public ItemStack getItemStack(int days, int stacks) {
        ItemStack item = new ItemBuilder(itemStack.clone())
                .setAmount(stacks)
                .replaceLore("{days}", days == Integer.MAX_VALUE ? "eterno" : String.valueOf(days))
                .build();
        NBTTag.setNBTString(item.getItemMeta(), NBTTagType.VIP_ITEM_KEY.getKey(), key);
        NBTTag.setNBTInt(item.getItemMeta(), NBTTagType.VIP_ITEM_DAYS.getKey(), days);
        return item;
    }

}
