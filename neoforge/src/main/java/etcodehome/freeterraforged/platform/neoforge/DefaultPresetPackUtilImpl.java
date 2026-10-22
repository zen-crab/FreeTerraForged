package etcodehome.freeterraforged.platform.neoforge;

import etcodehome.freeterraforged.data.worldgen.preset.FTFWorldPresets;

public final class DefaultPresetPackUtilImpl {
	private DefaultPresetPackUtilImpl() {
	}

	public static String defaultPresetPackId() {
		// The built-in pack is registered through AddPackFindersEvent.addPackFinders with
		// the bare ResourceLocation, and NeoForge stores it in the PackRepository under
		// String.valueOf(packId) ("freeterraforged:ftf_default_preset"). The "mod/<modid>"
		// prefix belongs to NeoForge's root mod resource pack, which is a different pack
		// and does not serve the generated ftf_default_preset folder as datapack data.
		return FTFWorldPresets.DEFAULT_PRESET_PACK_ID.toString();
	}
}
