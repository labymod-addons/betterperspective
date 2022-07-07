package net.labymod.addons.betterperspective.core;

import com.google.inject.Singleton;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;

@Singleton
@ConfigName("settings")
public class BetterPerspectiveConfiguration extends Config {

	@SwitchSetting
	private boolean enabled = true;

	@KeyBindSetting
	private Key key = null;

	@SwitchSetting
	private boolean toggle = false;

	public boolean isEnabled() {
		return this.enabled;
	}

	public Key getKey() {
		return this.key;
	}

	public boolean isToggle() {
		return this.toggle;
	}
}
