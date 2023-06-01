package net.labymod.addons.betterperspective.core.listener;

import net.labymod.addons.betterperspective.core.BetterPerspective;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerJoinEvent;

public class ServerJoinListener {

  private final BetterPerspective betterPerspective;

  public ServerJoinListener(BetterPerspective betterPerspective) {
    this.betterPerspective = betterPerspective;
  }

  @Subscribe
  public void onServerJoin(ServerJoinEvent event) {
    this.betterPerspective.resetWarningSent();
  }
}
