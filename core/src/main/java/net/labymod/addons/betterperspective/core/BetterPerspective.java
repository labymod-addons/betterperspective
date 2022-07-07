package net.labymod.addons.betterperspective.core;

import com.google.inject.Singleton;
import net.labymod.addons.betterperspective.core.listener.KeyListener;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonListener;

@Singleton
@AddonListener
public class BetterPerspective extends LabyAddon<BetterPerspectiveConfiguration> {

	@Override
	protected void enable() {
		this.registerSettingCategory();

		this.registerListener(KeyListener.class);
	}

	@Override
	protected Class<BetterPerspectiveConfiguration> configurationClass() {
		return BetterPerspectiveConfiguration.class;
	}
}
