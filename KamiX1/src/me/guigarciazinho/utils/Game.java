package me.guigarciazinho.utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.guigarciazinho.principal.Main;
import me.guigarciazinho.status.GameStatus;
import net.milkbowl.vault.economy.Economy;

public class Game {
	private Main plugin = null;
	private GameStatus gameStatus = null;
	private ArrayList<UUID> desafiador;
	private ArrayList<UUID> desafiado;
	private Economy econ = null;
	private String prefixo = null;
	
	public Game(Main main){
		plugin = main;	
		prefixo = plugin.getConfig().getString("Prefixo").replace("&", "§");
		desafiador = new ArrayList<>();
		desafiado = new ArrayList<>();
		setStatus(GameStatus.OPEN);
		setupEconomy();
		
	}
	
	public void setStatus(GameStatus status){
		this.gameStatus = status;
	}
	
	public GameStatus getStatus(){
		return gameStatus;
	}
	
	public void cancelGame(){
		
		
	}
	public void registrarJogadores(UUID p, UUID alvo){
		desafiador.add(p);
		desafiado.add(alvo);
		
	}
	
	public ArrayList<UUID> getDesafiado(){
		return desafiado;
	}
	
	public ArrayList<UUID> getDesafiador(){
		return desafiador;
	}
	
	public void desregistrarJogadores(){
		desafiador.clear();
		desafiado.clear();
	}
	
	
	@SuppressWarnings("deprecation")
	public double getDinheiro(String nome){
		
		return econ.getBalance(nome);
	}
	
	public String getConfigString(String string){
		return plugin.getConfig().getString(string).replace("&", "§");
	}
	
	@SuppressWarnings("static-access")
	public void jogoAceito(Player p){
		Bukkit.getScheduler().cancelTasks(plugin);
		setStatus(GameStatus.IN_GAME);
		Bukkit.broadcastMessage(prefixo + plugin.game.getConfigString("Aceitou").replace("@desafiado", p.getName()));
		Location loc = (Location) plugin.configLoc.get("Loc1." + ".Location"); 
		Bukkit.getPlayer(desafiador.get(0)).teleport(loc);
		Location loc2 = (Location) plugin.configLoc.get("Loc2." + ".Location");
		Bukkit.getPlayer(desafiado.get(0)).teleport(loc2);
		Bukkit.broadcastMessage(prefixo + "§e Para assistir o x1 utilize /x1 camarote");
	}
	
	@SuppressWarnings("static-access")
	public void tempoAcabou(){
		Bukkit.getScheduler().cancelTasks(plugin);
		Location loc = (Location) plugin.configLoc.get("Saida." + ".Location");
		Bukkit.getPlayer(desafiado.get(0)).teleport(loc);
		Bukkit.getPlayer(desafiador.get(0)).teleport(loc);
		desregistrarJogadores();
		setStatus(GameStatus.OPEN);
		Bukkit.broadcastMessage(prefixo + getConfigString("Tempo acabou"));
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public void aoVencer(Player vencedor, Player perdedor){
		Location loc = (Location) plugin.configLoc.get("Saida." + ".Location");
		Bukkit.getScheduler().cancelTasks(plugin);
		vencedor.sendMessage(prefixo + "§e Você tem §2" + plugin.getConfig().getInt("Tempo de coleta") + "§e segundos para pegar os itens do chão.");
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				desregistrarJogadores();
				vencedor.teleport(loc);

			}
		}, 20 * plugin.getConfig().getInt("Tempo de coleta"));
		econ.withdrawPlayer(perdedor.getName(), getAposta());
		econ.depositPlayer(vencedor.getName(), getAposta());
		setStatus(GameStatus.OPEN);
		Bukkit.broadcastMessage(prefixo + getConfigString("Venceu").replace("@vencedor", vencedor.getName()).replace("@perdedor", perdedor.getName()));
	}
	
	public void aoArregar(Player p, OfflinePlayer alvo){
		plugin.game.desregistrarJogadores();
		Bukkit.broadcastMessage(prefixo + getConfigString("Arregou").replace("@desafiador", p.getName()).replace("@desafiado", alvo.getName()));
		plugin.game.setStatus(GameStatus.OPEN);
	}
	
	public double getAposta(){
		return plugin.getConfig().getDouble("Dinheiro");
	}
	
	public int getTempo(String string){
		return plugin.getConfig().getInt(string);
		
	}
	
	
	
	private boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}


}
