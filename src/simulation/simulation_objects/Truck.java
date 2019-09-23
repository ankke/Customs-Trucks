package simulation.simulation_objects;

public class Truck extends QueueElement {
    private final int truckId;
    private final int weight;

    public Truck(int truckId, int weight) {
        super(weight);
        this.truckId = truckId;
        this.weight = weight;
    }

    public Truck(int truckId){
        this.truckId = truckId;
        this.weight = 0;
    }

    @Override
    public String asString(){
        return "Truck id " + truckId + " weight: " + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Truck)) return false;
        Truck truck = (Truck) o;
        return truckId == truck.truckId;
    }
}
