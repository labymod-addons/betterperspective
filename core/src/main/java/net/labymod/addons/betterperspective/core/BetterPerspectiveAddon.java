package net.labymod.addons.betterperspective.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.settings.SettingsRegistry;
import net.labymod.api.configuration.settings.gui.SettingCategoryRegistry;
import net.labymod.api.event.Priority;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;
import net.labymod.api.event.client.lifecycle.GameInitializeEvent;
import net.labymod.api.event.client.lifecycle.GameInitializeEvent.Lifecycle;
import net.labymod.api.models.addon.annotation.AddonMain;

@Singleton
@AddonMain
public class BetterPerspectiveAddon {

  private final LabyAPI labyAPI;
  private final SettingCategoryRegistry categoryRegistry;

  private final BetterPerspective perspective;

  private DefaultBetterPerspectiveConfiguration configuration;

  @Inject
  public BetterPerspectiveAddon(
      LabyAPI labyAPI,
      SettingCategoryRegistry categoryRegistry,
      BetterPerspective perspective) {
    this.labyAPI = labyAPI;
    this.categoryRegistry = categoryRegistry;
    this.perspective = perspective;
  }

  @Subscribe(Priority.LATEST)
  public void onGameInitialize(GameInitializeEvent event) {
    if (event.getLifecycle() != Lifecycle.POST_STARTUP) {
      return;
    }

    //register config
    try {
      this.configuration = this.labyAPI.getConfigurationLoader()
          .load(DefaultBetterPerspectiveConfiguration.class);

      // Create registry
      SettingsRegistry registry = this.configuration.initializeRegistry();

      // Register configuration categories
      this.categoryRegistry.register(registry.getId(), registry);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Subscribe
  private void onKey(KeyEvent event) {

    boolean isDisabled = !this.configuration.isEnabled();
    boolean isNotTheKey = !event.getKey().equals(this.configuration.getKey());
    if (isDisabled || isNotTheKey) {
      return;
    }

    if (this.configuration.isToggle()) {
      if (event.getState().equals(State.UNPRESSED)) {
        this.perspective.toggle();
      }
      return;
    }

    if (event.getState().equals(State.UNPRESSED)) {
      this.perspective.deactivate();
    } else if (event.getState().equals(State.PRESS)) {
      this.perspective.activate();
    }
  }
}
