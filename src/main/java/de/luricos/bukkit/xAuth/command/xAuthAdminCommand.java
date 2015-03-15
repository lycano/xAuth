/*
 * xAuth for Bukkit
 * Copyright (C) 2012 Lycano <https://github.com/lycano/xAuth/>
 *
 * Copyright (C) 2011 CypherX <https://github.com/CypherX/xAuth/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.luricos.bukkit.xAuth.command;

import de.luricos.bukkit.xAuth.permissions.PermissionType;
import de.luricos.bukkit.xAuth.permissions.provider.CustomPlayerPermissionHandler;
import de.luricos.bukkit.xAuth.permissions.provider.PlayerPermissionHandler;
import de.luricos.bukkit.xAuth.xAuth;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author lycano
 */
public abstract class xAuthAdminCommand extends xAuthCommand {

    private boolean result = false;

    protected boolean isAllowedCommand(final CommandSender sender, final String messageNode, final String... command) {
        if (sender instanceof Player) {
            return this.isAllowedCommand((Player) sender, messageNode, command);
        }

        return (sender instanceof ConsoleCommandSender) || (sender instanceof RemoteConsoleCommandSender);
    }

    protected boolean isAllowedCommand(final Player player, final String messageNode, final String... command) {
        boolean allowed = (((new CustomPlayerPermissionHandler(player, PermissionType.USE_ADMIN_COMMANDS)).hasPermission()) && ((new PlayerPermissionHandler(player, "PlayerCommandPreProcessEvent", command)).hasPermission()));
        if (!allowed)
            xAuth.getPlugin().getMessageHandler().sendMessage(messageNode, player);

        return allowed;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return this.result;
    }
}
