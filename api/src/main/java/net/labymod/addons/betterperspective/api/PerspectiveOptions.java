package net.labymod.addons.betterperspective.api;

import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;

public interface PerspectiveOptions extends MinecraftOptions {

  void setPerspective(Perspective perspective);

}
