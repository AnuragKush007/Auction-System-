package sdm.engine.CutomSDMClasses;

import java.util.*;

public class StoreOwner extends User{

    private Map<String, Zone> ownedZones; //key=zoneName, value=zone
    private Map<String, Set<Feedback>> zoneNameToFeedbacks; //key=zone name

    public StoreOwner(String name){
        super(name);
        this.ownedZones = new HashMap<>();

        this.zoneNameToFeedbacks = new HashMap<>();
    }

    public Map<String, Zone> getOwnedZones() {
        return ownedZones;
    }

    public void addOwnedZone(Zone zone){
        this.ownedZones.put(zone.getName(), zone);
    }

    public Map<String, Set<Feedback>> getZoneNameToFeedbacks() {
        return zoneNameToFeedbacks;
    }

    public Set<Feedback> getFeedbacksByZoneName(String zoneName){
        return this.zoneNameToFeedbacks.get(zoneName);
    }

    public void addFeedback(String zoneName, String storeName, String customerName, Date date, int score, String Text){
        Feedback newFeedback = new Feedback(customerName, storeName, date, score, Text);
        if(this.zoneNameToFeedbacks.get(zoneName) == null){
            this.zoneNameToFeedbacks.put(zoneName, new HashSet<>());
        }
        this.zoneNameToFeedbacks.get(zoneName).add(newFeedback);
    }
}
