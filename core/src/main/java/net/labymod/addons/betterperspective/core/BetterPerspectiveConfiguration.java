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
import net.labymod.addons.betterperspective.core.misc.BetterPerspectivePerspective;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;

@Singleton
@ConfigName("settings")
public class BetterPerspectiveConfiguration extends AddonConfig {

	@SwitchSetting
	private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

	@KeyBindSetting
	private final ConfigProperty<Key> key = new ConfigProperty<>(Key.NONE);

	@SwitchSetting
	private final ConfigProperty<Boolean> toggle = new ConfigProperty<>(false);

	@SettingSection("behavior")
  @DropdownWidget.DropdownSetting
  private final ConfigProperty<BetterPerspectivePerspective> thirdPersonMode =
      new ConfigProperty<>(BetterPerspectivePerspective.THIRD_PERSON_BACK);

	@SwitchSetting
	private final ConfigProperty<Boolean> lockPitchRange = new ConfigProperty<>(true);

	@SwitchSetting
	private final ConfigProperty<Boolean> skipFirstPerson = new ConfigProperty<>(false);

	@SwitchSetting
	private final ConfigProperty<Boolean> resetToPreviousPerspective = new ConfigProperty<>(true);

	@Override
	public ConfigProperty<Boolean> enabled() {
		return this.enabled;
	}

	public ConfigProperty<Key> key() {
		return this.key;
	}

	public ConfigProperty<Boolean> toggle() {
		return this.toggle;
	}

	public ConfigProperty<BetterPerspectivePerspective> thirdPersonMode() {
		return this.thirdPersonMode;
	}

	public ConfigProperty<Boolean> lockPitchRange() {
		return this.lockPitchRange;
	}

	public ConfigProperty<Boolean> skipFirstPerson() {
		return this.skipFirstPerson;
	}

	public ConfigProperty<Boolean> resetToPreviousPerspective() {
		return this.resetToPreviousPerspective;
	}
}
