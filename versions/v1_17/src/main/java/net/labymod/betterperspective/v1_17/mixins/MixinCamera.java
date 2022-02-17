package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.api.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveAddon;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class MixinCamera
{

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
    private void setDirectionYawAndPitch(Args args)
    {
        BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();
        if (betterperspective.isActive()) {
            args.setAll(betterperspective.getYaw(), betterperspective.getPitch());
        }
    }

}
