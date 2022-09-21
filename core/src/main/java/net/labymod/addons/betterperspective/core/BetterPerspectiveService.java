/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
