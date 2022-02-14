package net.labymod.addons.betterperspective.api;

public interface BetterPerspective {

  boolean isActive();

  void setYaw(float yaw);
  void setPitch(float pitch);

  float getYaw();
  float getPitch();

}
