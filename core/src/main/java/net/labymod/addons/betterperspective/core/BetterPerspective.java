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
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.screen.key.HotkeyService.Type;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class BetterPerspective extends LabyAddon<BetterPerspectiveConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new GameTickListener(this, this.labyAPI().minecraft()));

    Laby.references().hotkeyService().register(
        "betterperspective",
        this.configuration().key(),
        () -> this.configuration().toggle().get() ? Type.TOGGLE : Type.HOLD,
        active -> {
          BetterPerspectiveService service = this.references().betterPerspectiveService();
          BetterPerspectiveConfiguration configuration = this.configuration();
          if (active) {
            service.activate(
                configuration.thirdPersonMode().get(),
                configuration.lockPitchRange().get(),
                configuration.unlockCamera().get()
            );
          } else {
            service.deactivate(
                configuration.resetToPreviousPerspective().get(),
                configuration.unlockCamera().get()
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
