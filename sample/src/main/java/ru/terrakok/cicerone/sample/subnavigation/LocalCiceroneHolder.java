package ru.terrakok.cicerone.sample.subnavigation;

import com.github.terrakok.cicerone.Cicerone;
import com.github.terrakok.cicerone.Router;

import java.util.HashMap;

/**
 * Created by terrakok 27.11.16
 */
public class LocalCiceroneHolder {
    private HashMap<String, Cicerone<Router>> containers;

    public LocalCiceroneHolder() {
        containers = new HashMap<>();
    }

    public Cicerone<Router> getCicerone(String containerTag) {
        if (!containers.containsKey(containerTag)) {
            containers.put(containerTag, Cicerone.Companion.create());
        }
        return containers.get(containerTag);
    }
}
