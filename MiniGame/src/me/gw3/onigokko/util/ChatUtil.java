package me.gw3.onigokko.util;

import me.gw3.onigokko.game.GamePlayer;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static void sendMsgAll(String msg, Player[] players){
            for(Player player : players){
                player.sendMessage(msg);
            }
    }

    public static void sendMsgAll(String msg, GamePlayer[] players){
        for(GamePlayer player : players){
            player.sendMessage(msg);
        }
    }
}
