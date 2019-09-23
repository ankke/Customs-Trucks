package simulation;

import simulation.simulation_objects.SimulatedSystem;

public abstract class Simulation {

    protected Statistics statistics;

    protected SimulatedSystem simulatedSystem;

    protected SimulationDescriptor simDescr = new SimulationDescriptor();

    abstract public void simulate();

    protected void step(){
        simulatedSystem.step();
        statistics.update(simulatedSystem);
    }

    protected void reset(){
        statistics.reset();
        simulatedSystem.reset();
    }

    public long getAverage(){
        return statistics.getAverage();
    }

    public void setSimulationDescriptor(SimulationDescriptor simulationDescriptor){
        simDescr = simulationDescriptor;
    }

    protected void log() {
        System.out.println("Started simulation");
    }

    public static class SimulationDescriptor {

        private int numberOfTrucks = 1;

        private int maxWeight = 1;

        public SimulationDescriptor() {
        }

        public SimulationDescriptor setNumberOfTrucks(int numberOfTrucks) {
            this.numberOfTrucks = numberOfTrucks;
            return this;
        }

        public int getNumberOfTrucks() {
            return numberOfTrucks;
        }

        public SimulationDescriptor setMaxWeight(int maxWeight) {
            this.maxWeight = maxWeight;
            return this;
        }

        public int getMaxWeight() {
            return maxWeight;
        }

        public String asString() {
            return "\n\tnumber of trucks in simulation: " + numberOfTrucks + "\n\tmaximum weight of single truck: " + maxWeight;
        }
    }
}
