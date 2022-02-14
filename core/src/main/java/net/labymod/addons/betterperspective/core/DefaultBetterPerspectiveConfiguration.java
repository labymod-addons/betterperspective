package net.labymod.addons.betterperspective.core;

import com.google.inject.Singleton;
import net.labymod.addons.betterperspective.api.BetterPerspectiveConfiguration;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget.KeyBindSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.impl.AddonConfig;

@Singleton
@ConfigName("settings")
public class DefaultBetterPerspectiveConfiguration extends AddonConfig implements
    BetterPerspectiveConfiguration {

  @SwitchSetting
  private boolean isEnabled = true;

  @KeyBindSetting
  private Key key = Key.CIRCUMFLEX;

  @SwitchSetting
  private boolean isToggle = false;

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }

  @Override
  public Key getKey() {
    return this.key;
  }

  @Override
  public boolean isToggle() {
    return this.isToggle;
  }
}
