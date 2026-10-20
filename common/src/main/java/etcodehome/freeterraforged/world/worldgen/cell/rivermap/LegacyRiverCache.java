package etcodehome.freeterraforged.world.worldgen.cell.rivermap;

import etcodehome.freeterraforged.world.worldgen.noise.NoiseUtil;

public class LegacyRiverCache extends RiverCache {
	
    public LegacyRiverCache(RiverGenerator generator) {
        super(generator);
    }
    
    @Override
    public Rivermap getRivers(int x, int z) {
        long id = NoiseUtil.seed(x, z);
        Rivermap rivers = this.cache.computeIfAbsentIfOpen(id, key -> this.generator.generateRivers(x, z, key));
        if (rivers != null) {
            return rivers;
        }
        // The cache is closed (or raced with closure) during worldgen teardown: generate
        // without caching rather than throwing "Cache is closed" into active samplers.
        return this.generator.generateRivers(x, z, id);
    }
}
