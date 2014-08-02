package me.johnnywoof;

import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SpaceWarningCommand extends Command{
	
	private SpaceWarning sw;
	
	public SpaceWarningCommand(SpaceWarning sw) {
        super("bungeespacewarning", "bungeespacewarning.usage", "bsw");
        
        this.sw = sw;
        
    }
 
	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
      
		if(args.length <= 0){
			
			if(sender.hasPermission("bungeespacewarning.check")){
			
				if(System.getProperty("os.name") != null){
					sender.sendMessage(ChatColor.GREEN + "OS: " + System.getProperty("os.name").toString().toLowerCase());
				}else{
					sender.sendMessage(ChatColor.GREEN + "OS: null");
				}
				sender.sendMessage(ChatColor.GREEN + "System Architecture: " + System.getProperty("os.arch").toString().toLowerCase());
				sender.sendMessage(ChatColor.GREEN + "System Version: " + System.getProperty("os.version").toString().toLowerCase());
				sender.sendMessage(ChatColor.GREEN + "System Username: " + System.getProperty("user.name").toString().toLowerCase());
				sender.sendMessage(ChatColor.GREEN + "Java Version: " + System.getProperty("java.version").toString().toLowerCase());
				sender.sendMessage(ChatColor.GREEN + "Available Processors: " + Runtime.getRuntime().availableProcessors());
				sender.sendMessage(ChatColor.GREEN + "Total Ram: " + (Runtime.getRuntime().maxMemory() / 1048576) + "MB");
				sender.sendMessage(ChatColor.GREEN + "Version: " + sw.getProxy().getVersion().toString());
				long num = Value.getSpaceLeftInMB();
				sender.sendMessage(ChatColor.GREEN + "Disk Space Left: " + (num) + "MB (" + (num / 1024) + "GB)");

			}else{
				
				sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
				
			}
			
		}else{
			
			if(args[0].equalsIgnoreCase("silent")){
				
				if(sender.hasPermission("bungeespacewarning.silent")){
				
					boolean silent = Value.silents.contains(sender.getName());
					
					if(silent){
						
						Value.silents.remove(sender.getName());
						sender.sendMessage(ChatColor.RED + "Alerts are no longer hidden for you.");
						
					}else{
						
						Value.silents.add(sender.getName());
						sender.sendMessage(ChatColor.RED + "Alerts are now hidden for you.");
						
					}
				
				}else{
					
					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
					
				}
				
			}else if(args[0].equalsIgnoreCase("reload")){
				
				if(sender.hasPermission("bungeespacewarning.reload")){
					
					try {
						
						sw.reload();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					sender.sendMessage(ChatColor.GREEN + "Configuration has been reloaded.");
					
				}else{
					
					sender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
					
				}
				
			}
			
		}
        
    }
	
}
