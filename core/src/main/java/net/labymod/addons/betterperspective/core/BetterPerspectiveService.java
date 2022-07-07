package net.labymod.addons.betterperspective.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;

@Singleton
public class BetterPerspectiveService {

	private final LabyAPI labyAPI;
	private Perspective previousMode;
	private boolean active = false;
	private float yaw;
	private float pitch;

	@Inject
	private BetterPerspectiveService(LabyAPI labyAPI) {
		this.labyAPI = labyAPI;
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

		MinecraftOptions options = this.labyAPI.getMinecraft().getOptions();
		this.previousMode = options.getPerspective();
		options.setPerspective(Perspective.THIRD_PERSON_BACK);
	}

	public void deactivate() {
		this.active = false;

		MinecraftOptions options = this.labyAPI.getMinecraft().getOptions();
		options.setPerspective(this.previousMode);
	}

	public boolean isActive() {
		return this.active;
	}

	public float getYaw() {
		return this.yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}
