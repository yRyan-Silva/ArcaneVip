package me.ryan.arcanevip.user;

import lombok.Data;
import me.ryan.stonkscore.utils.TimersApi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
public class User {

    private final String player;
    private String vipKey;
    private LocalDateTime vipExpiration;
    private boolean eternal;

    private transient boolean dirty;

    public User(String player) {
        this.player = player;
        this.dirty = true;
    }

    public void setVipKey(String vipKey) {
        this.vipKey = vipKey;
        this.dirty = true;
    }

    public void setVipExpiration(LocalDateTime vipExpiration) {
        this.vipExpiration = vipExpiration;
        this.dirty = true;
    }

    public void setEternal(boolean eternal) {
        this.eternal = eternal;
        this.dirty = true;
    }

    public boolean containsVip() {
        return vipKey != null;
    }

    public String getVipTime() {
        if (vipExpiration == null) return eternal ? "eterno" : "";
        LocalDateTime localNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return TimersApi.convertMillis(ChronoUnit.MILLIS.between(localNow, vipExpiration));
    }

    public void removeVip() {
        this.vipKey = null;
        this.eternal = false;
        this.vipExpiration = null;
        this.dirty = true;
    }

    public boolean expiredVip() {
        if (eternal) return false;
        if (vipExpiration == null) return true;

        LocalDateTime localNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Date dateNow = Date.from(localNow.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());

        if (dateNow.compareTo(Date.from(vipExpiration.atZone(ZoneId.of("America/Sao_Paulo")).toInstant())) >= 1)
            return true;
        else if (localNow.getDayOfMonth() == vipExpiration.getDayOfMonth() && localNow.getMonth().equals(vipExpiration.getMonth())
                && localNow.getYear() == vipExpiration.getYear() && localNow.getHour() >= vipExpiration.getHour()) {
            if (localNow.getHour() == vipExpiration.getHour()) {
                return localNow.getMinute() >= vipExpiration.getMinute() &&
                        localNow.getSecond() >= vipExpiration.getSecond();
            } else
                return true;
        } else
            return false;
    }

}
