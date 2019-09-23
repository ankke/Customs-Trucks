package simulation;

import simulation.simulation_objects.CustomsQueues;
import simulation.simulation_objects.MainQueue;
import simulation.simulation_objects.SimulatedSystem;
import simulation.simulation_objects.Truck;

import java.util.Random;

import static java.lang.Math.abs;

public class SimulationWithVisualisation extends Simulation {

    public SimulationWithVisualisation() {
        simulatedSystem = new SimulatedSystem(new MainQueue(), new CustomsQueues());
        statistics = new Statistics();
    }

    public void simulate() {
        reset();
        log();
        Random r = new Random();
        int steps = 0;
        while (steps++ < simDescr.getNumberOfTrucks()) {
            simulatedSystem.add(new Truck(steps, abs(r.nextInt()) % simDescr.getMaxWeight() + 1));
            step();
            System.out.println(
                    "\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            simulatedSystem.printCurrentStatus();
        }

        while (simulatedSystem.isNotEmpty()) {
            step();
            System.out.println(
                    "\n---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            simulatedSystem.printCurrentStatus();
        }
        statistics.printStatistics();
    }

    @Override
    public void log() {
        System.out.println("Started simulation with visualisation for parameters:" + simDescr.asString());
        System.out.println("Each row represents one step in time");
    }
}
