package me.gw3.onigokko.game;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import me.gw3.onigokko.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.World;

public class GameMain {


    public List<GamePlayer> players = new ArrayList<>();
    public GameCount count = new GameCount();

    private int gameTime = 600;
    private int defaultGameTime = 600;
    private BukkitRunnable gameRunnable;
    private boolean running = false;
    
    private GameEvent event = new GameEvent();
    
    public List<GamePlayer> remainPlayers = new ArrayList<>();


    public void start(){
        this.running = true;
        GameScoreBoard scoreBoard = new GameScoreBoard();
        this.setupScoreBoard(scoreBoard);
        remainPlayers.addAll(players);
        this.setOni();
        for(GamePlayer player : remainPlayers) {
        	PlayerChanger.becomeRunner(player.getBukkitPlayer());
        	player.getBukkitPlayer().teleport(new Location(player.getBukkitPlayer().getWorld(), 1066, 94, -1698));
        }
        gameRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                update(scoreBoard);
                
                if(gameTime < (defaultGameTime/3) * 2 && event.getEventStats() == EventStats.BEFORESTARTING) {
                	World world = ((CraftWorld) players.get(0).getBukkitPlayer().getWorld()).getHandle();
                	event.start(world, new Location(players.get(0).getBukkitPlayer().getWorld(), 1066, 94, -1698));                	
                	registerListener(event);
                	event.setEventStats(EventStats.INEVENT);
                }
                
                if(gameTime >= (defaultGameTime - 30)) {
                	sendMsgAll(ChatColor.AQUA + "鬼のスポーンまで" + ChatColor.WHITE + (defaultGameTime - gameTime) + ChatColor.AQUA + "秒");
                	if(gameTime == (defaultGameTime - 30)) {
                		spawnOni();
                	}
                }
                
                if(gameTime <= 10){
                    sendMsgAll("残り" + gameTime + " 秒");
                    if(gameTime == 0){
                    	sendMsgAll(ChatColor.GREEN + "逃走者" + ChatColor.GRAY + "の勝利！");
                    	unRegisterListener(event);
                        stop();
                    }
                }
                gameTime--;
            }
        };
        gameRunnable.runTaskTimer(Main.instance, 0, 20);
    }
    
    private void spawnOni() {
    	for(GamePlayer player: players) {
    		if(player.isOni()) {
    			player.getBukkitPlayer().teleport(new Location(player.getBukkitPlayer().getWorld(), 1066, 94, -1698));
    		}
    	}
    }

    public void stop(){
    	PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, 
				ChatSerializer.a("{\"text\":\"§cGame Over\"}"),100,20,20);
    	
    	PacketPlayOutTitle subTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, 
				ChatSerializer.a("{\"text\":\"§aThank you for Playing!\"}"),100,20,20);
    	
        for(GamePlayer player : players) {	
        	clearInventory(player.getBukkitPlayer());
    		((CraftPlayer) player.getBukkitPlayer()).getHandle().playerConnection.sendPacket(title);
    		((CraftPlayer) player.getBukkitPlayer()).getHandle().playerConnection.sendPacket(subTitle);
        }
        
        new BukkitRunnable() {
    		@Override
    		public void run() {
    			for(Player player : Bukkit.getOnlinePlayers()) {
    				send(player, "warp lobby");
    	        }
    		}
    	}.runTaskLater(Main.instance, 110);
    	new BukkitRunnable() {
    		@Override
    		public void run() {
    			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "reload");
    		}
    	}.runTaskLater(Main.instance, 120);
        gameRunnable.cancel();
        this.clear();
    }
    private void send(Player player, String command) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
        	out.writeUTF(player.getName());
			out.writeUTF(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
        player.sendPluginMessage(Main.instance, "Onigokko", stream.toByteArray());
    }

    private void clear(){
        Bukkit.getScoreboardManager().getMainScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        this.event.setEventStats(EventStats.BEFORESTARTING);
        gameRunnable = null;
        this.gameTime = defaultGameTime;
        remainPlayers.clear();
        players.clear();
        this.running = false;
    }

    public boolean cointainsPlayer(Player player){
        for(GamePlayer gamePlayer : players){
            if(gamePlayer.uuid.equals(player.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public void removePlayer(Player player){
        if(cointainsPlayer(player)){
            for(GamePlayer gamePlayer : players){
                if(gamePlayer.uuid.equals(player.getUniqueId())){
                    this.players.remove(gamePlayer);
                    return;
                }
            }
        }
    }
    
    private void registerListener(Listener listner) {
    	Main.instance.getServer().getPluginManager().registerEvents(listner, Main.instance);
    }
    
    private void unRegisterListener(Listener listener) {
    	EntityDeathEvent.getHandlerList().unregister(listener);
    	EntityDamageByEntityEvent.getHandlerList().unregister(listener);
    }

    private void setOni(){
        Random random = new Random();
        int i = 0;
        if(players.size() < 5){
            i = 1;
        }
        for(; i < 2; i++){
            GamePlayer player = players.get(random.nextInt(players.size()));
            player.setOni(true);
            player.sendMessage("あなたは鬼になりました。");
            PlayerChanger.becomeOni(player.getBukkitPlayer());
            player.getBukkitPlayer().teleport(new Location(player.getBukkitPlayer().getWorld(), 1057.5, 103, -1697.5));
            remainPlayers.remove(player);
        }
    }

    private void setupScoreBoard(GameScoreBoard scoreboard){
        Objective o = scoreboard.createObjective("Onigokko", DisplaySlot.SIDEBAR, "鬼ごっこ");
        scoreboard.createTeam(o, "count", "残り時間： ", 3);
        scoreboard.createTeam(o, "empty1", " ", 2);
        scoreboard.createTeam(o, "event", "次のイベント:  ", 1);
        scoreboard.createTeam(o, "empty2", " ", 0);
    }

    private void update(GameScoreBoard scoreboard){
    	scoreboard.getScoreboard().getTeam("count").setSuffix(this.gameTime + " 秒");
    	switch(this.event.getEventStats()){
        case BEFORESTARTING:
          scoreboard.getScoreboard().getTeam("event").setSuffix("討伐イベント");
          break;
        case INEVENT:
          scoreboard.getScoreboard().getTeam("event").setSuffix("");
          break;
        case FINISHED:
          scoreboard.getScoreboard().getTeam("event").setSuffix("終了");
      }
        scoreboard.sendScoreBoardAll();
    }

    public void sendMsgAll(String msg){
        for(GamePlayer player : players){
            player.sendMessage(msg);
        }
    }

    public boolean isRunning() {
        return running;
    }
    
    public void clearInventory(Player player){
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.updateInventory();
	}

    public GamePlayer getGamePlayerByPlayer(Entity target){
        for(GamePlayer player1 : players){
            if(target.getUniqueId().equals(player1.uuid)){
                return player1;
            }
        }
        return null;
    }





}
