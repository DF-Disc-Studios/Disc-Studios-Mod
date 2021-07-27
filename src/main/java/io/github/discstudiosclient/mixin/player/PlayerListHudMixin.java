package io.github.discstudiosclient.mixin.player;

import java.util.UUID;

import io.github.discstudiosclient.features.tab.DiscStudiosServer;
import io.github.discstudiosclient.features.tab.User;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    public void getPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        if(DiscStudiosServer.getUserAmount() == 0) return;

        UUID id = entry.getProfile().getId();
        Text name = entry.getDisplayName() != null ? this.spectatorFormat(entry, entry.getDisplayName().shallowCopy()) : this.spectatorFormat(entry, Team.decorateName(entry.getScoreboardTeam(), new LiteralText(entry.getProfile().getName())));
        User user = DiscStudiosServer.getUser(id.toString());

        if (user != null) {
            LiteralText star = new LiteralText(user.getStar());
            name = star.shallowCopy().append(name);
        }
        cir.setReturnValue(name);
    }

    private Text spectatorFormat(PlayerListEntry playerListEntry, MutableText mutableText) {
        return playerListEntry.getGameMode() == GameMode.SPECTATOR ? mutableText.formatted(Formatting.ITALIC) : mutableText;
    }
}
