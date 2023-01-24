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

import net.labymod.addons.betterperspective.core.BetterPerspective;
import net.labymod.addons.betterperspective.core.BetterPerspectiveConfiguration;
import net.labymod.addons.betterperspective.core.BetterPerspectiveService;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;

public class KeyListener {

	private final BetterPerspective betterPerspective;
	private final BetterPerspectiveService service;

	public KeyListener(BetterPerspective betterPerspective) {
    this.betterPerspective = betterPerspective;
    this.service = betterPerspective.references().betterPerspectiveService();
  }

	@Subscribe
	public void onKey(KeyEvent event) {
		if (!this.betterPerspective.labyAPI().minecraft().isMouseLocked()) {
			return;
		}

		BetterPerspectiveConfiguration configuration = this.betterPerspective.configuration();
		if (!configuration.enabled().get() || !event.key().equals(configuration.key().get())) {
			return;
		}

		if (configuration.toggle().get()) {
			if (event.state() == KeyEvent.State.UNPRESSED) {
				if (this.service.isActive()) {
					this.service.deactivate(configuration.resetToPreviousPerspective().get());
				} else {
					this.service.activate(configuration.thirdPersonMode().get(),
							configuration.lockPitchRange().get());
				}
			}

			return;
		}

		if (event.state() == KeyEvent.State.UNPRESSED) {
			this.service.deactivate(configuration.resetToPreviousPerspective().get());
		} else if (event.state() == KeyEvent.State.PRESS) {
			this.service.activate(configuration.thirdPersonMode().get(),
					configuration.lockPitchRange().get());
		}
	}
}
