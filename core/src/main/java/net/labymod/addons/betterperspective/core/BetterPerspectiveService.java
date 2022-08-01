package net.labymod.addons.betterperspective.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;

@Singleton
public class BetterPerspectiveService {

    private final LabyAPI labyAPI;
    private final CameraLockController cameraLockController;

    private Perspective previousMode;
    private boolean active = false;

    @Inject
    private BetterPerspectiveService(LabyAPI labyAPI, CameraLockController cameraLockController) {
        this.labyAPI = labyAPI;
        this.cameraLockController = cameraLockController;
    }

    public void toggle() {
        if (this.active) {
            this.deactivate();
        } else {
            this.activate();
        }
    }

    public void activate() {
        this.active = true;

        MinecraftOptions options = this.labyAPI.minecraft().options();
        this.previousMode = options.perspective();
        options.setPerspective(Perspective.THIRD_PERSON_BACK);

        this.cameraLockController.lock(CameraLockController.LockType.HEAD);
    }

    public void deactivate() {
        this.active = false;

        MinecraftOptions options = this.labyAPI.minecraft().options();
        options.setPerspective(this.previousMode);

        this.cameraLockController.unlock();
    }

    public boolean isActive() {
        return this.active;
    }
}
