package me.gw3.onigokko.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        /*if(args.length == 0){
            return true;
        }
        if(sender instanceof Player){
            if(args[0].equalsIgnoreCase("join")){
            	if(Main.gameMain.isRunning()) {
            		return true;
            	}
                Main.gameMain.count.onJoin((Player) sender);
                
            }else if(args[0].equalsIgnoreCase("leave")){
                Main.gameMain.count.onLeave((Player) sender);
            }
        }*/
        return true;
    }
}
