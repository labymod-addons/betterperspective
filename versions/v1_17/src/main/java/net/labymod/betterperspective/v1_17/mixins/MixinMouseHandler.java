package net.labymod.betterperspective.v1_17.mixins;

import net.labymod.addons.betterperspective.core.BetterPerspectiveService;
import net.labymod.api.inject.LabyGuice;
import net.labymod.api.util.math.MathHelper;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {

	@Redirect(method = "turnPlayer",
			at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
	private void redirectPlayerRotation(LocalPlayer player, double x, double y) {
		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);

		if (service.isActive()) {
			service.setYaw(service.getYaw() + (float) x / 8f);
			service.setPitch((float) MathHelper.clamp(service.getPitch() + y / 8f, -90, 90));
			return;
		}

		player.turn(x, y);
	}
}
