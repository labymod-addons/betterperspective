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

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.addons.betterperspective.core.misc.BetterPerspectivePerspective;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.reference.annotation.Referenceable;

@Singleton
@Referenceable
public class BetterPerspectiveService {

	private final LabyAPI labyAPI;
	private final CameraLockController cameraLockController;

	private Perspective previousMode;
	private boolean active = false;

  @Inject
  public BetterPerspectiveService() {
    this.labyAPI = Laby.labyAPI();
    this.cameraLockController = Laby.references().cameraLockController();
  }

	public void activate(BetterPerspectivePerspective perspective, boolean lockPitchRange) {
		if (this.active) {
			return;
		}

		this.active = true;
		MinecraftOptions options = this.labyAPI.minecraft().options();
		this.previousMode = options.perspective();
		if (this.previousMode == Perspective.FIRST_PERSON) {
			options.setPerspective(perspective.perspective());
		}

		this.cameraLockController.lock(CameraLockController.LockType.HEAD);
		this.cameraLockController.setUnlimitedPitch(!lockPitchRange);
	}

	public void deactivate() {
		this.deactivate(true);
	}

	public void deactivate(boolean resetPerspective) {
		if (!this.active) {
			return;
		}

		this.labyAPI.minecraft().options().setPerspective(
				resetPerspective ? this.previousMode : Perspective.FIRST_PERSON);

		this.active = false;
		this.cameraLockController.unlock();
	}

	public boolean isActive() {
		return this.active;
	}
}
