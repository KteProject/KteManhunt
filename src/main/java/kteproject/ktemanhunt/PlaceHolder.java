package kteproject.ktemanhunt;

import kteproject.ktemanhunt.Util.PlaceHoldersUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceHolder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "KteManhunt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "KteProject";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String placeholder) {
        if (player == null) {
            return null;
        }

        return PlaceHoldersUtil.processPlaceholder(player, placeholder);
    }
}
