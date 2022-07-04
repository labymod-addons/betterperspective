package net.labymod.betterperspective.v1_8.mixins;

import net.labymod.addons.betterperspective.api.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveAddon;
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
public class MixinEntityRenderer
{

    @Shadow
    private Minecraft mc;

    private EntityCamera camera;
    private float yaw, pitch;

    /*
    prevent the rotation of the player
    */
    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
    private void redirectPlayerRotation(EntityPlayerSP player, float x, float y)
    {
        BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();

        if (betterperspective.isActive()) {
            betterperspective.setYaw(betterperspective.getYaw() + x / 8f);
            betterperspective.setPitch(MathHelper.clamp(betterperspective.getPitch() + y / 8f, -90, 90));
            return;
        }

        player.setAngles(x, y);
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
    private Entity createCameraEntity(Minecraft instance)
    {
        BetterPerspective betterperspective = BetterPerspectiveAddon.getPerspective();
        if (!betterperspective.isActive()) {
            return this.mc.getRenderViewEntity();
        }

        return this.getCameraEntity();
    }

    private EntityCamera getCameraEntity()
    {
        Entity original = this.mc.getRenderViewEntity();
        if(this.camera == null) {
            this.camera = new EntityCamera(original.worldObj);
        }

        if(original.dimension != this.camera.dimension) {
            this.camera.travelToDimension(original.dimension);
        }

        //this.camera.setPosition(original.posX, original.posY, original.posZ);
        this.camera.setAngles(this.yaw, this.pitch);
        return this.camera;
    }

}
