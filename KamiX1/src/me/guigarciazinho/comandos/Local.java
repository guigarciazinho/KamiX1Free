package me.guigarciazinho.comandos;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.guigarciazinho.principal.Main;

public class Local implements CommandExecutor {
	private Main plugin;
	
	public Local(Main main){
		plugin = main;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("setx1")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("x1.admin")){
				if (args.length >= 1) {
					if("loc1".equalsIgnoreCase(args[0])){
						plugin.configLoc.set("Loc1." + ".Location", p.getLocation());
						p.sendMessage("[§eKamiX1§f]§2 Entrada1 definida com sucesso!");
						try {
							plugin.configLoc.save(plugin.configFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					
					}
					if("loc2".equalsIgnoreCase(args[0])){
						plugin.configLoc.set("Loc2." + ".Location", p.getLocation());
						p.sendMessage("[§eKamiX1§f]§2 Entrada2 definida com sucesso!");
						try {
							plugin.configLoc.save(plugin.configFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if("camarote".equalsIgnoreCase(args[0])){
						plugin.configLoc.set("Camarote." + ".Location", p.getLocation());
						p.sendMessage("[§eKamiX1§f]§2 Camarote definido com sucesso!");
						try {
							plugin.configLoc.save(plugin.configFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if("saida".equalsIgnoreCase(args[0])){
						plugin.configLoc.set("Saida." + ".Location", p.getLocation());
						p.sendMessage("[§eKamiX1§f]§2 Saida definida com sucesso!");
						try {
							plugin.configLoc.save(plugin.configFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}else {
					sender.sendMessage("[§eKamiX1§f]§c Você utilizou o comando de forma errada.");
					return true;
				}

				}else{
					sender.sendMessage("[§eKamiX1§f]§c Você não tem permissão para utilizar este comando.");
				}
			}else{
				sender.sendMessage("[§eKamiX1§f]§c Comando apenas para Jogadores");
				return true;
			}
		}
		return false;
	}

}
