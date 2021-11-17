package main.plugin.model;

import org.bukkit.Material;

public class TPLocation {

    private String name;
    private Material material;
    private double x;
    private double z;
    private double y;
    private String world;
    private boolean enabled;

    public TPLocation(String name, Material material, double x, double z, double y, String world, boolean enabled) {
        this.name = name;
        this.material = material;
        this.x = x;
        this.z = z;
        this.y = y;
        this.world = world;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLocation(int x, int z, int y) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "TPLocation{" +
                "name='" + name + '\'' +
                ", material=" + material +
                ", x=" + x +
                ", z=" + z +
                ", y=" + y +
                ", world='" + world + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
