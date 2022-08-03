package com.github.pawel.hellointellijplatformplugintemplate.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.github.pawel.hellointellijplatformplugintemplate.settings.PluginSettingsState",
       storages = @Storage("PluginSettingsState.xml"))
public class PluginSettingsState implements PersistentStateComponent<PluginSettingsState> {

    public String userId = "pawel";
    public boolean ideaStatus = false;

    public static PluginSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(PluginSettingsState.class);
    }

    @Override
    public @Nullable PluginSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final PluginSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
