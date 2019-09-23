package simulation;

import java.util.Scanner;

import simulation.simulation_objects.CustomsQueues;
import simulation.simulation_objects.MainQueue;
import simulation.simulation_objects.SimulatedSystem;
import simulation.simulation_objects.Truck;

/**
 * Class representing manual simulation and interface required in the task
 */
public class ManualSimulation {

    private SimulatedSystem simulatedSystem;
    private Statistics statistics;

    public ManualSimulation() {
        simulatedSystem = new SimulatedSystem(new MainQueue(), new CustomsQueues());
        statistics = new Statistics();
    }

    public  void simulate() {
        int id = 1;
        while (true) {
            printInstructions();
            Scanner in = new Scanner(System.in);
            switch (in.nextLine()) {
                case "1":
                    System.out.println("Enter truck's weight and press enter to confirm");
                    simulatedSystem.add(new Truck(id, in.nextInt()));
                    step();
                    id++;
                    break;
                case "2":
                    simulatedSystem.printCurrentStatus();
                    statistics.printStatistics();
                    break;
                case "3":
                    step();
                    break;
                case "4":
                    System.out.println("Enter truck's id and press enter to confirm");
                    simulatedSystem.showEstimateFor(new Truck(in.nextInt()));
                    break;
                case "5":
                    statistics.printStatistics();
                    System.exit(1);
            }
        }
    }

    protected void step(){
        simulatedSystem.step();
        statistics.update(simulatedSystem);
    }

    private static void printInstructions() {
        System.out.println("\nWrite number of one of the options shown below and press enter to confirm");
        System.out.println("1. Add new truck");
        System.out.println("2. Show simulatedSystem status");
        System.out.println("3. Step forward in time");
        System.out.println("4. Show waiting time estimate for truck");
        System.out.println("5. End the simulation and show simulation statistics\n");
    }

}
