package me.johnnywoof;

import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CheckRunnable implements Runnable{
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		long num = Value.getSpaceLeftInMB();
		
		if(num <= Value.space){
			
			if(Value.warn){
				
				for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
					
					if(p.hasPermission("spacewarning.notify")){
						
						if(!Value.silents.contains(p.getUniqueId())){
						
							p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
							p.sendMessage(ChatColor.RED + "WARNING! The space on the drive is low!");
							p.sendMessage(ChatColor.RED + "Space left on drive: " + num + " MB (" + (num / 1024) + " GB)");
							p.sendMessage(ChatColor.RED + "To hide this message, please do /spacewarning silent");
							p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
						
						}
						
					}
					
				}
				
			}
			
			Logger log = ProxyServer.getInstance().getLogger();
			
			log.warning(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
			log.warning(ChatColor.RED + "WARNING! The space on the drive is low!");
			log.warning(ChatColor.RED + "Space left on drive: " + num + " MB (" + (num / 1024) + " GB)");
			log.warning(ChatColor.RED + "To hide this message, please do /spacewarning silent");
			log.warning(ChatColor.DARK_RED + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
			
			if(Value.shutdown){
				
				for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()){
					
					p.disconnect(ChatColor.RED + "Bungeecord Proxy has shutdown due to not enough space left on the drive.");
					
				}
				
				log.severe("[SpaceWarning] Server is shutting down due to only " + num + " MB left on the drive!");
				
				ProxyServer.getInstance().stop();
				
			}
			
			log = null;
			
		}
		
	}

}
