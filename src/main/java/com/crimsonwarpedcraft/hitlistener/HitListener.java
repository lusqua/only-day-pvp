package com.crimsonwarpedcraft.hitlistener;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitListener implements Listener {

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
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

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
}
