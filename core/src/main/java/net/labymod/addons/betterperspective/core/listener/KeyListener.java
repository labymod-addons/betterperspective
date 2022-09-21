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
		if (!this.addon.labyAPI().minecraft().isMouseLocked()) {
			return;
		}

		BetterPerspectiveConfiguration configuration = this.addon.configuration();
		if (!configuration.enabled().get() || !event.key().equals(configuration.key().get())) {
			return;
		}

		BetterPerspectiveService service = LabyGuice.getInstance(BetterPerspectiveService.class);
		if (configuration.toggle().get()) {
			if (event.state() == KeyEvent.State.UNPRESSED) {
				service.toggle();
			}
			return;
		}

		if (event.state() == KeyEvent.State.UNPRESSED) {
			service.deactivate();
		} else if (event.state() == KeyEvent.State.PRESS) {
			service.activate();
		}
	}
}
