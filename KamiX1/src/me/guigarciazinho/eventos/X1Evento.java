package me.guigarciazinho.eventos;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.guigarciazinho.principal.Main;

public class X1Evento implements Listener{
	private Main plugin;
	
	public X1Evento(Main main){
	plugin = main;	
	}
	
	@EventHandler
	private void jogadorMorre(PlayerDeathEvent e){
		Player p = (Player) e.getEntity();
		Player killer = (Player) e.getEntity().getKiller();
		if(p.getUniqueId().equals(plugin.game.getDesafiado().get(0))){
			plugin.game.aoVencer(killer, p);
			
		}
		if(p.getUniqueId().equals(plugin.game.getDesafiador().get(0))){
			plugin.game.aoVencer(killer, p);
		}
	}
	
	@EventHandler
	private void aoEuEntrar(PlayerJoinEvent e){
		if(e.getPlayer().getName().equalsIgnoreCase("guigarciazinho")){
			e.getPlayer().sendMessage("§c============================");
			e.getPlayer().sendMessage("[§eKamiX1§f]§2 Servidor utilizando §eKamiX1");
			e.getPlayer().sendMessage("§c============================");
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission("x1.admin")){
					p.sendMessage("§c----------- ATENÇÃO ----------");
					p.sendMessage("§2 O DESENVOLVEDOR DO PLUGIN §eKamiX1§e,§a guigarciazinho§e, ENTROU NO SERVIDOR");
					p.sendMessage("§c----------- ATENÇÃO ----------");
					p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1);
					p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1, 1);
				}
			}
			
		}
		
	}

}
