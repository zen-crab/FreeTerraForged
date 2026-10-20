package etcodehome.freeterraforged.world.worldgen.cell.rivermap;

import java.util.concurrent.TimeUnit;

import etcodehome.freeterraforged.concurrent.cache.Cache;
import etcodehome.freeterraforged.concurrent.cache.map.StampedLongMap;
import etcodehome.freeterraforged.world.worldgen.util.PosUtil;

public class RiverCache implements AutoCloseable {
    protected RiverGenerator generator;
    protected Cache<Rivermap> cache;
    
    public RiverCache(RiverGenerator generator) {
        this.cache = new Cache<>(32, 5L, 1L, TimeUnit.MINUTES, StampedLongMap::new);
        this.generator = generator;
    }
    
    public Rivermap getRivers(int x, int z) {
        Rivermap rivers = this.cache.computeIfAbsentIfOpen(PosUtil.pack(x, z), id -> {
        	return this.generator.generateRivers(PosUtil.unpackLeft(id), PosUtil.unpackRight(id), id);
        });
        if (rivers != null) {
            return rivers;
        }
        // The cache is closed (or raced with closure) during worldgen teardown: generate
        // without caching rather than throwing "Cache is closed" into active samplers.
        return this.generateUncached(x, z);
    }

    protected Rivermap generateUncached(int x, int z) {
        return this.generator.generateRivers(x, z, PosUtil.pack(x, z));
    }

	@Override
	public void close() {
		this.cache.close();
	}
}
