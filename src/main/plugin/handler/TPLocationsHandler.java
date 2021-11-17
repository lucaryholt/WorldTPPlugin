package main.plugin.handler;

import main.plugin.WorldTPPlugin;
import main.plugin.model.TPLocation;

import java.util.*;

public class TPLocationsHandler {

    public static Map<String, List<TPLocation>> tpLocations;

    public static List<TPLocation> getTPLocations(String username, boolean allPublic) {
        List<TPLocation> tpLocationsList = new ArrayList<>(tpLocations.get(username));
        if (allPublic) tpLocationsList.addAll(tpLocations.get("all"));
        tpLocationsList.sort(Comparator.comparing(TPLocation::getName));
        return tpLocationsList;
    }

    public static void refreshLocations () {
        tpLocations = WorldTPPlugin.databaseHandler.getTPLocations();
    }

}
