package simulation;

import static java.lang.Math.abs;

import java.util.Random;

import simulation.simulation_objects.CustomsQueues;
import simulation.simulation_objects.MainQueue;
import simulation.simulation_objects.SimulatedSystem;
import simulation.simulation_objects.Truck;

public class SimulationWithoutVisualisation extends Simulation {

    public SimulationWithoutVisualisation( ) {
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
        }
        while (simulatedSystem.isNotEmpty()) {
            step();
        }
        statistics.printStatistics();
    }

    @Override
    public void log() {
        System.out.println("Started simulation for parameters:" + simDescr.asString());
    }
}
