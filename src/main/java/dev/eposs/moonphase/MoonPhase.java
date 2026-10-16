package dev.eposs.moonphase;

/**
 * Mod-side moon phase enum. Order matches vanilla's phase index (0 = full moon ... 7 = waxing gibbous).
 */
public enum MoonPhase {
	FULL_MOON("full-moon.png"),
	WANING_GIBBOUS("waning-gibbous-moon.png"),
	THIRD_QUARTER("last-quarter-moon.png"),
	WANING_CRESCENT("waning-crescent-moon.png"),
	NEW_MOON("new-moon.png"),
	WAXING_CRESCENT("waxing-crescent-moon.png"),
	FIRST_QUARTER("first-quarter-moon.png"),
	WAXING_GIBBOUS("waxing-gibbous-moon.png");

	private final String texture;

	MoonPhase(String texture) {
		this.texture = texture;
	}

	public static MoonPhase fromId(int id) {
		MoonPhase[] values = values();
		return values[Math.floorMod(id, values.length)];
	}

	/** Resolves a vanilla moon phase (by enum name, falling back to its ordinal-like toString) to this enum. */
	public static MoonPhase fromVanilla(Object vanillaPhase) {
		String name = String.valueOf(vanillaPhase).toUpperCase(java.util.Locale.ROOT);
		for (MoonPhase phase : values()) {
			if (phase.name().equals(name)) {
				return phase;
			}
		}
		return null;
	}

	public String getTexturePath() {
		return "textures/gui/containers/" + texture;
	}
}
