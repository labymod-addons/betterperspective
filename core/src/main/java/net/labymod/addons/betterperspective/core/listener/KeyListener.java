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
