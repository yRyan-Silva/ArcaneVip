package me.ryan.arcanevip.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NBTTagType {

    VIP_ITEM_KEY("ArcaneVip-VipItemKey"), VIP_ITEM_DAYS("ArcaneVip-VipItemDays");

    private final String key;

}