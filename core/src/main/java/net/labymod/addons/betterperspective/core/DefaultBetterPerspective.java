package net.labymod.addons.betterperspective.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.addons.betterperspective.api.PerspectiveOptions;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.options.Perspective;

@Singleton
public class DefaultBetterPerspective implements
    net.labymod.addons.betterperspective.api.BetterPerspective {

  private final LabyAPI labyAPI;

  private Perspective previousMode;

  private boolean isActive = false;

  private float yaw, pitch;

  @Inject
  public DefaultBetterPerspective(LabyAPI labyAPI) {
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

    PerspectiveOptions options = (PerspectiveOptions) this.labyAPI.getMinecraft().getOptions();
    this.previousMode = options.getPerspective();
    options.setPerspective(Perspective.THIRD_PERSON_BACK);
  }

  public void deactivate() {
    this.isActive = false;

    PerspectiveOptions options = (PerspectiveOptions) this.labyAPI.getMinecraft().getOptions();
    options.setPerspective(this.previousMode);
  }

  @Override
  public boolean isActive() {
    return this.isActive;
  }

  @Override
  public void setYaw(float yaw) {
    this.yaw = yaw;
  }

  @Override
  public void setPitch(float pitch) {
    this.pitch = pitch;
  }

  @Override
  public float getYaw() {
    return this.yaw;
  }

  @Override
  public float getPitch() {
    return this.pitch;
  }
}
