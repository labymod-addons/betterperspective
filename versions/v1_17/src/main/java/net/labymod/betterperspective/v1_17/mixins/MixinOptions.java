package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.api.PerspectiveOptions;
import net.labymod.api.client.options.Perspective;
import net.minecraft.client.CameraType;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Options.class)
public abstract class MixinOptions implements PerspectiveOptions {

  @Shadow
  private CameraType cameraType;

  @Override
  public void setPerspective(Perspective perspective) {
    this.cameraType = CameraType.values()[perspective.ordinal()];
  }
}
