package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.api.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveAddon;
import net.labymod.api.util.math.MathHelper;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public class MixinMouseHandler
{

  @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
  private void redirectPlayerRotation(LocalPlayer player, double x, double y) {
    BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();

    if(betterperspective.isActive()) {
      betterperspective.setYaw(betterperspective.getYaw() + (float) x / 8f);
      betterperspective.setPitch((float) MathHelper.clamp(betterperspective.getPitch() + y / 8f, -90, 90));
      return;
    }

    player.turn(x, y);
  }

}
