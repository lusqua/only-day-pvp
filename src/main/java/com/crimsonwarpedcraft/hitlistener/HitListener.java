package com.crimsonwarpedcraft.hitlistener;

import io.papermc.lib.PaperLib;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HitListener extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);

        saveDefaultConfig();

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
        return time >= 13000 && time <= 23000; // Entre 6:30 PM e 11:30 PM
    }

    /**
     * Manipula eventos de dano causado por entidades.
     *
     * @param event O evento de dano causado por entidade.
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Player attacker = (Player) event.getDamager();

        attacker.sendMessage("Bateu");

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            Player victim = (Player) event.getEntity();

            victim.sendMessage("tomasse");

            if (isPvPAllowed(attacker.getWorld())) {
                // PvP é permitido
                attacker.sendMessage("PvP é permitido à noite!");
            } else {
                // PvP não é permitido
                event.setCancelled(true);
                attacker.sendMessage("PvP não é permitido durante o dia!");
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        /*
         * We get the player and make a variable to make it easier to access it when we
         * need to use it.
         */
        Player p = event.getPlayer();
        p.sendMessage("quebrou");
    }

}
