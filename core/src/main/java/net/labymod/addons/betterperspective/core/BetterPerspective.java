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

import javax.inject.Singleton;
import net.labymod.addons.betterperspective.core.generated.DefaultReferenceStorage;
import net.labymod.addons.betterperspective.core.listener.GameTickListener;
import net.labymod.addons.betterperspective.core.listener.KeyListener;
import net.labymod.addons.betterperspective.core.listener.ScreenOpenListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@Singleton
@AddonMain
public class BetterPerspective extends LabyAddon<BetterPerspectiveConfiguration> {

	@Override
	protected void enable() {
    this.registerSettingCategory();

    this.registerListener(new GameTickListener(this, this.labyAPI().minecraft()));
    this.registerListener(new KeyListener(this));
    this.registerListener(new ScreenOpenListener(this));
  }

  @Override
  protected Class<BetterPerspectiveConfiguration> configurationClass() {
    return BetterPerspectiveConfiguration.class;
  }

  public DefaultReferenceStorage references() {
    return this.getReferenceStorageAccessor();
  }
}
