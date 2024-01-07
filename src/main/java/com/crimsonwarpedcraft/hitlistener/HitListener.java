package com.crimsonwarpedcraft.hitlistener;

import io.papermc.lib.PaperLib;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HitListener extends JavaPlugin implements Listener {

    public EnablePvp pvp;

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);

        saveDefaultConfig();

        this.pvp = new EnablePvp();

        getCommand("pvpEnable").setExecutor(pvp);

        // Registra o listener
        getServer().getPluginManager().registerEvents(this, this);
    }



    /**
     * Verifica se o PvP é permitido durante a noite.
     *
     * @param world O mundo onde o PvP está ocorrendo.
     * @return true se o PvP é permitido, false caso contrário.
     */
    private boolean isPvPAllowed(World world) {
        long time = world.getTime();
        return (time >= 13000 && time <= 23000) || pvp.pvpEnabled; // Entre 6:30 PM e 11:30 PM
    }

    /**
     * Manipula eventos de dano causado por entidades.
     *
     * @param event O evento de dano causado por entidade.
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();


            if (!isPvPAllowed(attacker.getWorld())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Check if the event has a valid location
        if (event.getTo() != null) {
            // Get the world from the destination location
            World world = event.getTo().getWorld();

            // Check if the world is the overworld
            if (world.getEnvironment() == World.Environment.NORMAL) {
                if (!TitleManager.nightTitleShown) {
                    TitleManager.showNightTitleToAll(pvp.pvpEnabled);
                } else {
                    TitleManager.showDayTitleToAll(pvp.pvpEnabled);
                }
            }
            // If it's not the overworld, you can choose to handle it differently or do nothing
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player currentPlayer = event.getPlayer();
        TitleManager.showNightTitle(currentPlayer);
    }

}
