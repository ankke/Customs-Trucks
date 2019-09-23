package simulation;

import simulation.simulation_objects.SimulatedSystem;

public class Statistics {
    /**
     * sum od time units of all finished trucks in current simulation
     */
    private long sumOfWaitingTime = 0;
    /**
     * number of finished trucks in current simulation
     */
    private long numberOfFinishedTrucks = 0;

    public long getAverage(){
        return sumOfWaitingTime / numberOfFinishedTrucks;
    }

    public void printStatistics() {
        if (numberOfFinishedTrucks == 0) {
            System.out.println("\nStatistics unavailable");
        } else {
            System.out.println("\nAverage waiting time: " + sumOfWaitingTime / numberOfFinishedTrucks + "\n");
        }
    }

    /**
     * update performed after each step
     * @param simulatedSystem
     */

    public void update(SimulatedSystem simulatedSystem) {
        sumOfWaitingTime += simulatedSystem.customsQueues.getTimeWaited();
        numberOfFinishedTrucks += simulatedSystem.customsQueues.getFinishedTrucks();
    }

    public void reset() {
        sumOfWaitingTime = 0;
        numberOfFinishedTrucks = 0;
    }
}
