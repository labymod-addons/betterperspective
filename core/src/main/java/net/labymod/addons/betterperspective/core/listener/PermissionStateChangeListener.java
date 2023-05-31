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
