package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.core.BetterPerspectiveService;
import net.labymod.api.inject.LabyGuice;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class MixinCamera {

	/**
	 * Method to modify the arguments of the
	 * move position argument to make adjustment
	 * for the mode
	 */
	@ModifyArgs(method = "setup",
			at = @At(value = "INVOKE", target = "net/minecraft/client/Camera.setRotation(FF)V",
					ordinal = 0))
	private void setDirectionYawAndPitch(Args args) {
		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);
		if (service.isActive()) {
			args.setAll(service.getYaw(), service.getPitch());
		}
	}
}
