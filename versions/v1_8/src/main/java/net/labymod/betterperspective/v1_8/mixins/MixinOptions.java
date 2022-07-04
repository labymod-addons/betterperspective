package net.labymod.betterperspective.v1_8.mixins;

import net.labymod.addons.betterperspective.api.PerspectiveOptions;
import net.labymod.api.client.options.Perspective;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameSettings.class)
public abstract class MixinOptions implements PerspectiveOptions {

  @Shadow
  private int thirdPersonView;

  @Override
  public void setPerspective(Perspective perspective) {
    this.thirdPersonView = perspective.ordinal();
  }
}
