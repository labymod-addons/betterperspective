package net.labymod.addons.betterperspective.core.listener;

import com.google.inject.Inject;
import net.labymod.addons.betterperspective.core.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveConfiguration;
import net.labymod.addons.betterperspective.core.BetterPerspectiveService;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.inject.LabyGuice;

public class KeyListener {

	private final BetterPerspective addon;

	@Inject
	private KeyListener(BetterPerspective addon) {
		this.addon = addon;
	}

	@Subscribe
	public void onKey(KeyEvent event) {
		if (!this.addon.labyAPI().getMinecraft().isMouseLocked()) {
			return;
		}

		BetterPerspectiveConfiguration configuration = this.addon.configuration();
		if (!configuration.isEnabled() || !event.getKey().equals(configuration.getKey())) {
			return;
		}

		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);
		if (configuration.isToggle()) {
			if (event.getState().equals(KeyEvent.State.UNPRESSED)) {
				service.toggle();
			}

			return;
		}

		if (event.getState().equals(KeyEvent.State.UNPRESSED)) {
			service.deactivate();
		} else if (event.getState().equals(KeyEvent.State.PRESS)) {
			service.activate();
		}
	}
}
