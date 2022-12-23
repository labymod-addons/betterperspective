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
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;

import javax.inject.Inject;

public class ScreenOpenListener {

	private final BetterPerspective betterPerspective;
	private final BetterPerspectiveService service;

	@Inject
	private ScreenOpenListener(BetterPerspective betterPerspective,
	                           BetterPerspectiveService service) {
		this.betterPerspective = betterPerspective;
		this.service = service;
	}

	@Subscribe
	public void onScreenOpen(ScreenOpenEvent event) {
		BetterPerspectiveConfiguration configuration = this.betterPerspective.configuration();
		if (configuration.toggle().get()) {
			return;
		}

		this.service.deactivate(configuration.resetToPreviousPerspective().get());
	}
}
