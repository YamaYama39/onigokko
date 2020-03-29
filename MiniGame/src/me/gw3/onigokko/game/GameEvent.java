package me.gw3.onigokko.game;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.gw3.onigokko.Main;
import me.gw3.onigokko.customEntities.CustomSkeleton;
import me.gw3.onigokko.customEntities.CustomZombie;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.World;

public class GameEvent implements Listener{
	
	private CustomZombie zombie;
	private CustomSkeleton skeleton;
	
	private BukkitRunnable eventRunnable;
	
	private EventStats stats = EventStats.BEFORESTARTING;
	
	private int time = 200;
	
	public GameEvent() {
		
	}
	
	public void start(World world, Location loc) {
		Main.gameMain.sendMsgAll(ChatColor.RED + "�����C�x���g���n�܂�܂����I");
		Main.gameMain.sendMsgAll(ChatColor.RED + "���Ԑ���" + time + " �b");
		Main.gameMain.sendMsgAll(ChatColor.GREEN + "�����҂͎��ԓ��Ƀ����X�^�[�𓢔����Ă��������I");
		spawnMonster(world, loc);
		Main.gameMain.sendMsgAll(ChatColor.LIGHT_PURPLE + "X:34.0 Y:65.0, Z:60.0�Ƀ]���r�A�X�P���g�����X�|�[�����܂����B");
		eventRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				if(time <= 10) {
					Main.gameMain.sendMsgAll("�C�x���g�I���܂Ŏc�� " + time + " �b");
					if(time == 1) {
						this.cancel();
						Main.gameMain.sendMsgAll(ChatColor.RED + "�C�x���g�I��!");
						Main.gameMain.sendMsgAll(ChatColor.RED + "�~�b�V�������s!");
						Main.gameMain.sendMsgAll(ChatColor.GREEN + "�S�ɃX�s�[�h�A�W�����v�u�[�X�g���t�^����܂����I");
						zombie.dead = true;
						skeleton.dead = true;
						giveRewardToOni();
						end();
					}
				}
				time--;
			}
		};
		
		eventRunnable.runTaskTimer(Main.instance, 0, 20);
	}
	
	public void end() {
		this.time = 200;
		stats = EventStats.FINISHED;
		this.zombie.getWorld().removeEntity(zombie);
		this.skeleton.getWorld().removeEntity(skeleton);
		eventRunnable.cancel();
		this.unRegisterListener(this);
	}
	
	public void setEventStats(EventStats stats) {
		this.stats = stats;
	}
	
	public EventStats getEventStats() {
		return this.stats;
	}
	
	
	
	public void spawnMonster(World world, Location loc) {
		zombie = new CustomZombie(world);
		skeleton = new CustomSkeleton(world);
		zombie.spawn(loc);
		skeleton.spawn(loc);
	}
	
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent event) {
		net.minecraft.server.v1_8_R3.Entity entity = ((CraftEntity)event.getEntity()).getHandle();
		if(zombie != null && skeleton != null) {
			if(entity.getId() == zombie.getId()) {
				Main.gameMain.sendMsgAll(ChatColor.GREEN + "�]���r�����j����܂����I");
			}
			
			if(entity.getId() == skeleton.getId()) {
				Main.gameMain.sendMsgAll(ChatColor.GREEN + "�X�P���g�������j����܂����I");
			}
		}
		
		if(!zombie.isAlive() && !skeleton.isAlive()) {
			Main.gameMain.sendMsgAll(ChatColor.GREEN + "�~�b�V�������B������܂����I");
			this.giveRewardToRunner();
			this.end();
		}
	
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		net.minecraft.server.v1_8_R3.Entity attacker = ((CraftEntity)event.getDamager()).getHandle();
		net.minecraft.server.v1_8_R3.Entity target = ((CraftEntity)event.getEntity()).getHandle();
		if(event.getDamager() instanceof Player) {
			if(target.getId() == zombie.getId() || target.getId() == skeleton.getId()) {
				if(Main.gameMain.getGamePlayerByPlayer((Player) event.getDamager()).isOni()) {
					event.setCancelled(true);
				}
			}
		}
		
		if(event.getEntity() instanceof Player) {
			if(attacker.getId() == zombie.getId() || attacker.getId() == skeleton.getId()) {
				if(Main.gameMain.getGamePlayerByPlayer((Player) event.getEntity()).isOni()) {
					event.setCancelled(true);
				}
			}
		}
		
	}
	
	private void unRegisterListener(Listener listener) {
    	EntityDeathEvent.getHandlerList().unregister(listener);
    	EntityDamageByEntityEvent.getHandlerList().unregister(listener);
    }
	
	private void giveRewardToRunner() {
		for(GamePlayer player : Main.gameMain.remainPlayers) {
			player.getBukkitPlayer().getInventory().addItem(CustomSnowBall.create());
		}
	}
	
    private void giveRewardToOni() {
    	for(GamePlayer player : Main.gameMain.players) {
    		if(player.isOni()) {
    			player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 3));
    			player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2000, 3));
    		}
    	}
	}
    
    public BukkitRunnable getEventRunnable() {
    	return eventRunnable;
    }

}
