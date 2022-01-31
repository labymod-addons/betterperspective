package net.labymod.addons.betterperspective.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;

@Singleton
public class BetterPerspective {

  private final LabyAPI labyAPI;

  private Perspective previousMode;

  private boolean isActive = false;

  @Inject
  public BetterPerspective(LabyAPI labyAPI) {
    this.labyAPI = labyAPI;
  }

  public void toggle() {
    if (isActive) {
      deactivate();
    } else {
      activate();
    }
  }

  public void activate() {
    this.isActive = true;

    MinecraftOptions options = this.labyAPI.getMinecraft().getOptions();

    this.previousMode = options.getPerspective();
  }

  public void deactivate() {
    this.isActive = false;
  }

}
