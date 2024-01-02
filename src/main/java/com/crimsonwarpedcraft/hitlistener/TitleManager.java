package com.crimsonwarpedcraft.hitlistener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TitleManager {
    public static boolean nightTitleShown = false;

    public static void showNightTitle(Player player) {
        World world = player.getWorld();
        long time = world.getTime();

        // Verify if the title was ready to be shown
        if (time >= 13000 && time <= 23000) {


            // Show the title to the player
            player.showTitle(pvpTitle());
        }
    }


    public static void showNightTitleToAll(boolean pvpEnabled) {
        if (!nightTitleShown) {
            World world = Bukkit.getWorlds().get(0); // Obtém o primeiro mundo, você pode ajustar isso conforme necessário
            long time = world.getTime();

            // Verify if is night ( 13000 and 23000)
            if ((time >= 13000 && time <= 23000) || pvpEnabled) {


                // Show the title to all players
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showTitle(pvpTitle());
                }

                // Set the nightTitleShown to true
                nightTitleShown = true;
            }
        }
    }

    public static void showDayTitleToAll(boolean pvpEnabled) {
        if (nightTitleShown && !pvpEnabled) {
            World world = Bukkit.getWorlds().get(0); // Obtém o primeiro mundo, você pode ajustar isso conforme necessário
            long time = world.getTime();

            // Verify if is day ( 0 and 13000)
            if (time >= 0 && time <= 13000) {
                // Create a title with the text and subtitle
                Title title = Title.title(
                        Component.text("Bom Dia!"),
                        Component.text("O PvP está desabilitado!"));

                // Show the title to all players
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.showTitle(title);
                }

                // Set the nightTitleShown to false
                nightTitleShown = false;
            }
        }
    }

    public static Title pvpTitle() {

        return Title.title(
                Component.text("Cuidado!"),
                Component.text("O PvP está habilitado!"));
    }
}
