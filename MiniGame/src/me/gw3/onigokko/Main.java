package me.gw3.onigokko;

import me.gw3.onigokko.command.GameCommand;
import me.gw3.onigokko.customEntities.NMSUtil;
import me.gw3.onigokko.events.MainEvent;
import me.gw3.onigokko.game.GameMain;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;
    public static GameMain gameMain = new GameMain();

    @Override
    public void onEnable(){
        instance = this;
        NMSUtil.registerEntities();
        this.getServer().getPluginManager().registerEvents(new MainEvent(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "Onigokko");
        this.getCommand("game").setExecutor(new GameCommand());
    }

    @Override
    public void onDisable(){

    }
}
