package me.gw3.onigokko.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.gw3.onigokko.Main;
import me.gw3.onigokko.game.PlayerChanger;
import net.md_5.bungee.api.ChatColor;

public class MainEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(Main.gameMain.isRunning() && !Main.gameMain.getGamePlayerByPlayer(player).isOni()) {
        	Main.gameMain.remainPlayers.remove(Main.gameMain.getGamePlayerByPlayer(player));
            Main.gameMain.sendMsgAll(ChatColor.RED + player.getName() + "Ç™ïﬂÇ‹ÇËÇ‹ÇµÇΩÅI");
            if(Main.gameMain.remainPlayers.size() == 0){
                Main.gameMain.sendMsgAll(ChatColor.GREEN + "ãSÇÃèüóò!!");
                Main.gameMain.stop();
                return;
            }
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	if(Main.gameMain.isRunning() && Main.gameMain.getGamePlayerByPlayer(event.getPlayer()) != null){
    		if(!Main.gameMain.getGamePlayerByPlayer(event.getPlayer()).isOni()) {
    			Main.gameMain.getGamePlayerByPlayer(event.getPlayer()).setOni(true);
    			PlayerChanger.becomeOni(event.getPlayer());
        	}else {
        		PlayerChanger.becomeOni(event.getPlayer());
        	}
    		new BukkitRunnable() {
    			@Override
    			public void run() {
    				event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 36.5, 67.0, -70.5));
    			}
    		}.runTaskLater(Main.instance, 20);
    	}
    }
    
    @EventHandler
    public void onItemDrop(ItemSpawnEvent event) {
    	event.getEntity().remove();
    }
    
    @EventHandler
    public void onPlayerThrow(EntityDamageByEntityEvent event) {
    	Entity attacker = event.getDamager();
        Entity target = event.getEntity();
        if(attacker instanceof Snowball && target instanceof Player) {
        	Snowball ball = (Snowball)attacker;
        	if(ball.getShooter() instanceof Player && Main.gameMain.cointainsPlayer((Player)ball.getShooter())) {
        		if(Main.gameMain.getGamePlayerByPlayer(target) != null && Main.gameMain.getGamePlayerByPlayer(target).isOni()) {
        			target.teleport(target.getLocation().add(0, 0.5, 0));
                    target.setVelocity(new Vector(Math.cos(Math.toRadians(attacker.getLocation().getYaw() + 90)), 0.05, 
                    		Math.sin(Math.toRadians(Math.abs(attacker.getLocation().getYaw()) + 90))));
        		}
        	}
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	event.setJoinMessage("");
    	if(Main.gameMain.isRunning()) {
    		return;
    	}
        Main.gameMain.count.onJoin(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLogOut(PlayerQuitEvent event){
        if(Main.gameMain.cointainsPlayer(event.getPlayer())){
            Main.gameMain.removePlayer(event.getPlayer());
            return;
        }
    }
}
