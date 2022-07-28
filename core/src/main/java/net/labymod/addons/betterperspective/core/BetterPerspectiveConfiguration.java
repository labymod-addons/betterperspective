package net.labymod.addons.betterperspective.core;

import com.google.inject.Singleton;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@Singleton
@ConfigName("settings")
public class BetterPerspectiveConfiguration extends AddonConfig {

	@SwitchSetting
	private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

	@KeyBindSetting
	private final ConfigProperty<Key> key = new ConfigProperty<>(Key.NONE);

	@SwitchSetting
	private final ConfigProperty<Boolean> toggle = new ConfigProperty<>(false);

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
}
