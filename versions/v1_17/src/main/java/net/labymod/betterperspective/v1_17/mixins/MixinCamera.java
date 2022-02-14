package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.api.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveAddon;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class MixinCamera {

  @Shadow
  private float xRot;

  @Shadow
  private float yRot;

  /**
   * Injector changing the yaw and pitch when moving the position
   * to make safe adjustments
   */
  @Inject(method = "setup", at = @At(
      value = "INVOKE",
      target = "net/minecraft/client/Camera.move(DDD)V",
      ordinal = 0
  ))
  private void movePositionAdjustments(BlockGetter param0, Entity param1, boolean param2,
      boolean param3, float param4, CallbackInfo ci) {
    BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();
    if (betterperspective.isActive()) {
      this.xRot = betterperspective.getPitch();
      this.yRot = betterperspective.getYaw();
    }
  }

  /**
   * Method to modify the arguments of the
   * move position argument to make adjustment
   * for the mode
   */
  @ModifyArgs(method = "setup", at = @At(
      value = "INVOKE",
      target = "net/minecraft/client/Camera.setRotation(FF)V",
      ordinal = 0
  ))
  private void setDirectionYawAndPitch(Args args) {
    BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();
    if (betterperspective.isActive()) {
      args.setAll(betterperspective.getYaw(), betterperspective.getPitch());
    }
  }

}
