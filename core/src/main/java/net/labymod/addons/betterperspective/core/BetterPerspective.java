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

import net.labymod.addons.betterperspective.core.generated.DefaultReferenceStorage;
import net.labymod.addons.betterperspective.core.listener.GameTickListener;
import net.labymod.addons.betterperspective.core.listener.PermissionStateChangeListener;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.screen.key.HotkeyService.Type;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.user.permission.ClientPermission;

@AddonMain
public class BetterPerspective extends LabyAddon<BetterPerspectiveConfiguration> {

  public static final String PERMISSION = "better_perspective_unlock_camera";

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new GameTickListener(this, this.labyAPI().minecraft()));
    this.registerListener(new PermissionStateChangeListener(this));

    this.labyAPI().permissionRegistry().register(PERMISSION, true);
    Laby.references().hotkeyService().register(
        "betterperspective",
        this.configuration().key(),
        () -> this.configuration().toggle().get() ? Type.TOGGLE : Type.HOLD,
        active -> {
          boolean unlockCamera;
          ClientPermission permission = Laby.labyAPI().permissionRegistry()
              .getPermission(PERMISSION);
          BetterPerspectiveConfiguration configuration = this.configuration();
          if (permission != null && !permission.isEnabled()) {
            unlockCamera = false;
          } else {
            unlockCamera = configuration.unlockCamera().get();
          }

          BetterPerspectiveService service = this.references().betterPerspectiveService();
          if (active) {
            service.activate(
                configuration.thirdPersonMode().get(),
                configuration.lockPitchRange().get(),
                unlockCamera
            );
          } else {
            service.deactivate(
                configuration.resetToPreviousPerspective().get(),
                unlockCamera
            );
          }
        }
    );
  }

  @Override
  protected Class<BetterPerspectiveConfiguration> configurationClass() {
    return BetterPerspectiveConfiguration.class;
  }

  public DefaultReferenceStorage references() {
    return this.getReferenceStorageAccessor();
  }
}
