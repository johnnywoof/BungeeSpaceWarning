package me.johnnywoof;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import com.google.common.io.ByteStreams;

public class SpaceWarning extends Plugin{
	
	public void onEnable(){
		
		try {
			
			this.reload();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.getProxy().getPluginManager().registerCommand(this, new SpaceWarningCommand(this));
		
	}
	
	public void onDisable(){
		
		this.getProxy().getScheduler().cancel(this);
		
		this.getLogger().info("[SpaceWarning] Disabled!");
		
	}
	
	public void reload() throws IOException{
		
		this.getProxy().getScheduler().cancel(this);
		
		if(!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		
		if(!new File(this.getDataFolder() + File.separator + "config.yml").exists()){
		
			this.saveDefaultConfig(this.getDataFolder());
		
		}
		
		Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.getConfig());
		
		Value.shutdown = config.getBoolean("shutdown");
		Value.warn = config.getBoolean("warn-players");
		Value.space = config.getLong("space-left");
		
		this.getProxy().getScheduler().schedule(this, new CheckRunnable(), 10, (config.getInt("check-interval")), TimeUnit.SECONDS);
		
		config = null;
		
		this.getLogger().info("[SpaceWarning] Space detection: " + Value.space + " MB");
		this.getLogger().info("[SpaceWarning] Shutdown server: " + Value.shutdown);
		this.getLogger().info("[SpaceWarning] Warn players with permission: " + Value.warn);
		this.getLogger().info("[SpaceWarning] Loaded and ready.");
		
	}
	
	private void saveDefaultConfig(File datafolder){
		
		if (!datafolder.exists()) {
            datafolder.mkdir();
        }
        File configFile = new File(datafolder, "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = this.getClass().getResourceAsStream("/config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
		
	}
	
	private File getConfig(){
		
		return new File(this.getDataFolder() + File.separator + "config.yml");
		
	}
	
}
