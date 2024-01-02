package com.crimsonwarpedcraft.hitlistener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EnablePvp implements CommandExecutor {

    public boolean pvpEnabled = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        this.pvpEnabled = !this.pvpEnabled;

        return true;
    }
}
