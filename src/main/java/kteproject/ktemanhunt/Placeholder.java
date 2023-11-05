package kteproject.ktemanhunt;

import kteproject.ktemanhunt.Placeholders.PlaceholderUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "KteManhunt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kaanflod, benlordlex, alwaysruby";
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

        String value = PlaceholderUtil.processPlaceholder(player, placeholder);
        return value;
    }
}
