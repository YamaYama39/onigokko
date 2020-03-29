package me.gw3.onigokko.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    public UUID uuid;
    private boolean oni;

    public GamePlayer(UUID uuid){
        this.uuid = uuid;
    }

    public void becomeOni(boolean frag){
        this.oni = frag;
    }

    public boolean isOni(){
        return this.oni;
    }

    public void setOni(boolean frag){
        this.oni = frag;
    }

    public Player getBukkitPlayer(){
        return Bukkit.getPlayer(this.uuid);
    }

    public void sendMessage(String msg){
        this.getBukkitPlayer().sendMessage(msg);
    }

}
