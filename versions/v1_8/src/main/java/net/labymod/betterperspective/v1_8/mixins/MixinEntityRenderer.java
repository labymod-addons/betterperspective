package net.labymod.betterperspective.v1_8.mixins;

import net.labymod.addons.betterperspective.core.BetterPerspectiveService;
import net.labymod.api.inject.LabyGuice;
import net.labymod.api.util.math.MathHelper;
import net.labymod.betterperspective.v1_8.entity.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

	@Shadow
	private Minecraft mc;

	private EntityCamera camera;
	private float yaw, pitch;

	/*
	prevent the rotation of the player
	*/
	@Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
	private void redirectPlayerRotation(EntityPlayerSP player, float x, float y) {
		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);

		if (service.isActive()) {
			service.setYaw(service.getYaw() + x / 8f);
			service.setPitch(MathHelper.clamp(service.getPitch() + y / 8f, -90, 90));
			return;
		}

		player.setAngles(x, y);
	}

	@Redirect(method = "orientCamera", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()"
					+ "Lnet/minecraft/entity/Entity;"))
	private Entity createCameraEntity(Minecraft instance) {
		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);
		if (!service.isActive()) {
			return this.mc.getRenderViewEntity();
		}

		return this.getCameraEntity();
	}

	private EntityCamera getCameraEntity() {
		Entity original = this.mc.getRenderViewEntity();
		if (this.camera == null) {
			this.camera = new EntityCamera(original.worldObj);
		}

		if (original.dimension != this.camera.dimension) {
			this.camera.travelToDimension(original.dimension);
		}

		this.camera.setPosition(original.posX, original.posY, original.posZ);
		this.camera.setAngles(this.yaw, this.pitch);
		return this.camera;
	}
}
