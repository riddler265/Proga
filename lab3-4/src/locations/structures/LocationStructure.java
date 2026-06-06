package locations.structures;

import java.util.ArrayList;
import java.util.List;

//enums
import enums.Purity;
import locations.Country;
import locations.locations.Location;

public class LocationStructure extends Location {
    protected List<Location> locations = new ArrayList<>();

    public LocationStructure(String name, String description, Purity purity, Country parentLocation) {
        super(name, description, purity, parentLocation);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location... locations) {
        for (Location location : locations) {
            this.locations.add(location);
            location.setParentLocation(this);
        }
    }  
}