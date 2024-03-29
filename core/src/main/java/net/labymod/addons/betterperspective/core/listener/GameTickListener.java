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
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

public class GameTickListener {

	private final BetterPerspective betterPerspective;
	private final BetterPerspectiveService service;
	private final Minecraft minecraft;

	public GameTickListener(BetterPerspective betterPerspective, Minecraft minecraft) {
    this.betterPerspective = betterPerspective;
    this.service = betterPerspective.references().betterPerspectiveService();
    this.minecraft = minecraft;
  }

	@Subscribe
	public void onGameTick(GameTickEvent event) {
		if (event.phase() != Phase.POST) {
			return;
		}

		ClientPlayer clientPlayer = this.minecraft.clientPlayer();
		if (clientPlayer == null) {
			return;
		}

		if (this.service.isActive()
				&& this.minecraft.options().perspective() == Perspective.FIRST_PERSON) {
      BetterPerspectiveConfiguration configuration = this.betterPerspective.configuration();
      if (configuration.skipFirstPerson().get()) {
        this.minecraft.options().setPerspective(Perspective.THIRD_PERSON_BACK);
      } else {
        this.service.deactivate(false, configuration.unlockCamera().get());
      }
    }
	}
}
