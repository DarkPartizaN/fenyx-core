package com.fenyx.api;

import java.util.HashMap;

/**
 *
 * @author KiQDominaN
 */
public class LaunchParams {

    private final HashMap<String, Object> params;

    public LaunchParams() {
        params = new HashMap<>();
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void addParam(String name, int value) {
        params.put(name, value);
    }

    public void addParam(String name, long value) {
        params.put(name, value);
    }

    public void addParam(String name, float value) {
        params.put(name, value);
    }

    public void addParam(String name, double value) {
        params.put(name, value);
    }
    
    public void addParam(String name, boolean value) {
        params.put(name, value);
    }

    public String getString(String name) {
        return (String) params.get(name);
    }

    public int getInt(String name) {
        return (int) params.get(name);
    }

    public long getLong(String name) {
        return (long) params.get(name);
    }

    public float getFloat(String name) {
        return (float) params.get(name);
    }

    public double getDouble(String name) {
        return (double) params.get(name);
    }

    public boolean getBool(String name) {
        return (boolean) params.get(name);
    }
}
