package me.guigarciazinho.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.guigarciazinho.principal.Main;
import me.guigarciazinho.status.GameStatus;

public class X1 implements CommandExecutor {
	private Main plugin;
	private String prefixo;

	public X1(Main main) {
		this.plugin = main;
		prefixo = plugin.getConfig().getString("Prefixo").replace("&", "§");
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("x1")) {
			if (sender instanceof Player) {
				if (args.length >= 1) {
					Player p = (Player) sender;
					if ("camarote".equalsIgnoreCase(args[0])) {
						if (p.hasPermission("x1.camarote") || p.hasPermission("x1.admin")) {
							Location loc = (Location) plugin.configLoc.get("Camarote." + ".Location");
							p.teleport(loc);
						} else {
							p.sendMessage(
									prefixo + "§c Opsss... Parece que você não tem permissão para ir ao camarote");
						}
					}
					if ("encerrar".equalsIgnoreCase(args[0])) {
						if (p.hasPermission("x1.admin")) {
							if(plugin.game.getStatus() == GameStatus.IN_GAME){
							plugin.game.tempoAcabou();
							}else{
								p.sendMessage("[§eKamiX1§f]§c Não há nenhum x1 em andamento para ser encerrado");
								return true;
							}
						} else {
							p.sendMessage(prefixo + "§c Ué... Você não tem permissão para encerrar o x1.");
							return true;
						}
					}
//					if("reload".equalsIgnoreCase(args[0])){
//						if(p.hasPermission("x1.admin")){
//							if(plugin.game.getStatus() == GameStatus.IN_GAME){
//							plugin.game.tempoAcabou();
//							}
//			                plugin.reloadConfig();
//							plugin.getPluginLoader().disablePlugin(plugin);
//			                plugin.getPluginLoader().enablePlugin(plugin);
//							p.sendMessage("[§eKamiX1§f]§2 Config recarregada com sucesso!");
//							return true;
//						}
//					}

					if ("aceitar".equalsIgnoreCase(args[0])) {
						if (plugin.game.getDesafiado().contains(p.getUniqueId())) {
							plugin.game.jogoAceito(p);
							Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
								@Override
								public void run() {
									plugin.game.tempoAcabou();

								}
							}, 20 * plugin.game.getTempo("Tempo x1"));
							return true;

						}
					}

					
					
					
					
						if ("desafiar".equalsIgnoreCase(args[0])) {
							if (args.length >= 2) {
							OfflinePlayer alvo = Bukkit.getOfflinePlayer(args[1]);
							if(p.getName().equalsIgnoreCase(args[1])){
								p.sendMessage(prefixo + "§c Você não pode desafiar a si mesmo");
							}
							if (alvo.getName().equalsIgnoreCase(args[1])) {
								if (plugin.game.getStatus().equals(GameStatus.OPEN)) {
									if (alvo.isOnline()) {
										if (plugin.game.getDinheiro(alvo.getName()) >= plugin.game.getAposta()) {
											if (plugin.game.getDinheiro(p.getName()) >= plugin.game.getAposta()) {
												plugin.game.registrarJogadores(p.getUniqueId(), alvo.getUniqueId());
												plugin.game.setStatus(GameStatus.PENDING);
												Bukkit.broadcastMessage(prefixo + plugin.getConfig()
														.getString("Desafiou").replace("@desafiador", p.getName())
														.replace("@desafiado", alvo.getName()).replace("&", "§"));
												Bukkit.getPlayer(args[1]).sendMessage(prefixo + "§2 Para aceitar o x1 utilize /x1 aceitar");;
												Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,
														new Runnable() {

															@Override
															public void run() {
																plugin.game.aoArregar(p, alvo);

															}
														}, 20 * plugin.game.getTempo("Tempo pend"));

												return true;
											} else {
												p.sendMessage(prefixo + " §c Você não tem dinheiro para tirar um x1");
												return true;
											}
										} else {
											p.sendMessage(prefixo + " §a" + alvo.getName()
													+ "§c Não tem dinheiro para tirar um x1");
											return true;
										}
									}else{
										p.sendMessage(prefixo + "§e " + alvo.getName() + "§c Não se encontra online");
									}
								} else {
									if (plugin.game.getStatus() == GameStatus.IN_GAME) {
										p.sendMessage(prefixo
												+ "§c Um x1 já está em andamento. Use /x1 camarote para assistir");
									}
									if (plugin.game.getStatus() == GameStatus.PENDING) {
										p.sendMessage(prefixo
												+ "§c Algum jogador já tem uma solitacao pendente, espere a solicitacao expirar, e, caso ele aceite, espere até o x1 terminar.");
									}
								}
							}

						}else {
							sender.sendMessage(prefixo + "§c Use /x1 desafiar Jogador.");
							return true;
						}
					}else{
						sender.sendMessage(prefixo + "§c Você usou o comando em um formato inexistente.");
					}
					
					
					
				} else {
					sender.sendMessage(prefixo + "§c Parece que você utilizou o comando de forma errada.");
					return true;
				}
			} else {
				sender.sendMessage(prefixo + "§c Apena para players.");
				return true;
			}
		}
		return false;
	}

}
