package org.shouthost.essentials.utils;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

public class Worlds {
    //TODO: Purge soon?
    private final World world;

    public Worlds(World world) {
        this.world = world;
    }

    public void unloadWorld() {
        unloadWorld(world);
    }

    public void unloadWorld(World world) {
        unloadWorld(world.provider.dimensionId);
    }

    public void unloadWorld(int dim) {
        DimensionManager.unloadWorld(dim);
    }

    public void unregisterWorld() {
        unregisterWorld(world);
    }

    public void unregisterWorld(World world) {
        unregisterWorld(world.provider.dimensionId);
    }

    public void unregisterWorld(int dim) {
        DimensionManager.unregisterDimension(dim);
    }

    public Chunk getChunk(int x, int z) {
        return world.getChunkFromChunkCoords(x, z);
    }

    public String getWorldName() {
        return world.getProviderName();
    }

    public int getWorldID() {
        return world.provider.dimensionId;
    }

    public World getNativeWorld() {
        return world;
    }

    public Location getLocation() {
        return new Location(world, 0, 0, 0);
    }

    public int regionCount() {
        return 0;
    }

    public boolean isChunkLoaded(int x, int z) {
        return world.getChunkFromChunkCoords(x, z).isChunkLoaded;
    }

    public void genChunk(int x, int z) {
        world.getChunkProvider().loadChunk(x, z);
    }

    public TileEntity getTileEntity(Location loc) {
        return world.getTileEntity(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public Block getBlock(Location loc) {
        return world.getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public int getBlockMetadata(Location loc) {
        return world.getBlockMetadata(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void setBlock(Location loc, Block block) {
        world.setBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), block);
    }

    public void setBlock(Location loc, Block block, int i, int i2) {
        world.setBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), block, i, i2);
    }

    public boolean isAirBlock(Location loc) {
        return world.isAirBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void setBlockToAir(Location loc) {
        world.setBlockToAir(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public void removeTileEntity(Location loc) {
        world.removeTileEntity(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
