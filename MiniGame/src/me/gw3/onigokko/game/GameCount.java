package me.gw3.onigokko.game;

import me.gw3.onigokko.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class GameCount {

    private BukkitRunnable countRunnable;
    private int count = 30;
    private boolean isStarting;

    private int minPlayer = 2;
    private int maxPlayer = 10;

    private void startCount(List<GamePlayer> gamePlayers){
        GameScoreBoard scoreBoard = new GameScoreBoard();
        this.setupScoreBoard(scoreBoard);
        this.isStarting = true;
        countRunnable = new BukkitRunnable(){
            @Override
            public void run(){
                if(!canStart()){
                    startCancel();
           
                }
                count--;
                update(scoreBoard);
                if(count <= 10) {
                	PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
            				ChatSerializer.a("{\"text\":\"§c" + count + "\"}"),100,20,20);
                	for(GamePlayer player : gamePlayers) {
                		((CraftPlayer) player.getBukkitPlayer()).getHandle().playerConnection.sendPacket(title);
                	}
                	Main.gameMain.sendMsgAll(ChatColor.GREEN + "The Game Start In " + count + "s!");
                }
                if(count == 0){
                    Main.gameMain.start();
                    scoreBoard.clear(DisplaySlot.SIDEBAR);
                    countRunnable.cancel();
                    count = 30;
                    isStarting = false;
                }
            }
        };
        countRunnable.runTaskTimer(Main.instance, 0, 20);
    }

    private void startCancel(){
    	this.isStarting = false;
        countRunnable.cancel();
        this.count = 30;
        Main.gameMain.sendMsgAll("canceled!");
    }

    public void onJoin(Player player){
        if(Main.gameMain.cointainsPlayer(player)){
            player.sendMessage(ChatColor.RED + "You are already in the game!");
            return;
        }
        Main.gameMain.players.add(new GamePlayer(player.getUniqueId()));
        Main.gameMain.sendMsgAll(player.getName() + " joined ("+ Main.gameMain.players.size() + "/" +this.maxPlayer + ")");
        if(this.canStart() && !this.isStarting){
            this.startCount(Main.gameMain.players);
        }
    }

    public void onLeave(Player player){
        if(!Main.gameMain.cointainsPlayer(player)){
            player.sendMessage(ChatColor.RED + "You aren't in the game!");
            return;
        }else {
            Main.gameMain.sendMsgAll(player.getName() + " left the game.");
            Main.gameMain.removePlayer(player);
        }
    }

    private void setupScoreBoard(GameScoreBoard scoreboard){
        Objective o = scoreboard.createObjective("Onigokko", DisplaySlot.SIDEBAR, "鬼ごっこ");
        scoreboard.createTeam(o, "count", "スタートまで： ", 6);
    }

    private void update(GameScoreBoard scoreboard){
        scoreboard.getScoreboard().getTeam("count").setSuffix(Main.gameMain.count.getCount() + " 秒");
        scoreboard.sendScoreBoardAll();
    }

    private boolean canStart(){
        if(Main.gameMain.players.size() >= minPlayer){
            return true;
        }
        return false;
    }

    public int getCount(){
        return count;
    }
}
