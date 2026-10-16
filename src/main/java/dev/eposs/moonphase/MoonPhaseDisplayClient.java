package dev.eposs.moonphase;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttributes;

public class MoonPhaseDisplayClient implements ClientModInitializer {
	public static final String MOD_ID = "moonphase";
	private static final int SIZE = 16;

	@Override
	public void onInitializeClient() {
		HudElementRegistry.attachElementAfter(
				VanillaHudElements.MISC_OVERLAYS,
				Identifier.fromNamespaceAndPath(MOD_ID, "moonphase_layer_after_misc_overlays"),
				MoonPhaseDisplayClient::extract);
	}

	private static void extract(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		ClientLevel level = client.level;
		if (level == null || client.player == null) {
			return;
		}

		// Since 1.21.11 the moon phase is an environment attribute, not Level#getMoonPhase().
		var vanillaPhase = level.environmentAttributes()
				.getValue(EnvironmentAttributes.MOON_PHASE, client.player.position());
		MoonPhase moonPhase = MoonPhase.fromVanilla(vanillaPhase);
		if (moonPhase == null) {
			return;
		}

		Identifier texture = Identifier.fromNamespaceAndPath(MOD_ID, moonPhase.getTexturePath());
		graphics.blit(
				RenderPipelines.GUI_TEXTURED,
				texture,
				client.getWindow().getGuiScaledWidth() - SIZE,
				client.getWindow().getGuiScaledHeight() - SIZE,
				0.0F, 0.0F,
				SIZE, SIZE,
				SIZE, SIZE);
	}
}
