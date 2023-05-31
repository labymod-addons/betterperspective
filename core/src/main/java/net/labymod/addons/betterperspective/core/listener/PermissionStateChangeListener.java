/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.labymod.addons.betterperspective.core.listener;

import net.labymod.addons.betterperspective.core.BetterPerspective;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent;
import net.labymod.api.event.labymod.serverapi.PermissionStateChangeEvent.State;

public class PermissionStateChangeListener {

  private static final Component PREFIX = Component.text("[", NamedTextColor.GRAY)
      .append(Component.text("BetterPerspective", NamedTextColor.DARK_AQUA))
      .append(Component.text("] ", NamedTextColor.GRAY));

  private final BetterPerspective betterPerspective;

  public PermissionStateChangeListener(BetterPerspective betterPerspective) {
    this.betterPerspective = betterPerspective;
  }

  @Subscribe
  public void onPermissionStateChange(PermissionStateChangeEvent event) {
    if (event.state() != State.DISALLOWED) {
      return;
    }

    String permissionId = event.permission().getPermissionId();
    if (!permissionId.equals(BetterPerspective.PERMISSION)
        || !this.betterPerspective.configuration().unlockCamera().get()) {
      return;
    }

    ChatExecutor chatExecutor = this.betterPerspective.labyAPI().minecraft().chatExecutor();
    Component component = Component.empty();
    component.append(PREFIX);
    component.append(Component.translatable(
        "betterperspective.permissions." + BetterPerspective.PERMISSION.replace("_", "-")
            + ".denied",
        NamedTextColor.RED
    ));

    chatExecutor.displayClientMessage(component);
  }
}
