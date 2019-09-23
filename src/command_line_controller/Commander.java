package command_line_controller;

import simulation.*;
import simulation.Simulation.SimulationDescriptor;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class responsible for invoking specified in command line simulation and for providing arguments from user input
 */
public class Commander {

    public static void execute(Simulation simulation) {
        long sumOfAll = 0;
        simulation.setSimulationDescriptor(getSimulationDescriptor());
        Integer numberOfSimulations = getNumberOfSimulations();
        for (int i = 1; i <= numberOfSimulations; i++) {
            simulation.simulate();
            sumOfAll += simulation.getAverage();
        }
        System.out.println("\nAVG from " + numberOfSimulations + " simulations: " + sumOfAll / numberOfSimulations);
    }

    public static void executeManualSimulation() {
        ManualSimulation manualSimulation = new ManualSimulation();
        manualSimulation.simulate();
    }

    public static SimulationDescriptor getSimulationDescriptor() {
        SimulationDescriptor simulationDescriptor = null;
        Scanner in = new Scanner(System.in);

        while (simulationDescriptor == null) {
            simulationDescriptor = fillDescriptorWithInput(in);
        }
        return simulationDescriptor;
    }

    private static SimulationDescriptor fillDescriptorWithInput(Scanner in) {
        try {
            System.out.println("Enter number of trucks in each simulation, max weight of " + "single truck and press enter");
            int numberOfTrucks = in.nextInt();
            int maxWeight = in.nextInt();
            if (numberOfTrucks < 1 || maxWeight < 1) {
                System.out.println("Entered values must be greater than 0");
                return null;
            }
            SimulationDescriptor simulationDescriptor = new SimulationDescriptor().setNumberOfTrucks(numberOfTrucks)
                    .setMaxWeight(maxWeight);
            return simulationDescriptor;
        } catch (InputMismatchException e) {
            System.out.println("Entered values must be numbers");
            return null;
        }
    }

    public static Integer getNumberOfSimulations() {
        Integer number = null;
        Scanner in = new Scanner(System.in);
        while (number == null) {
            number = readNumberOfSimul(in);
        }
        return number;
    }

    private static Integer readNumberOfSimul(Scanner in) {
        try {
            System.out.println("Enter number of simulations and press enter");
            int numberOfSim = in.nextInt();
            if (numberOfSim < 1) {
                System.out.println("Entered values must be greater than 0");
                return null;
            }
            return numberOfSim;
        } catch (InputMismatchException e) {
            System.out.println("Entered values must be numbers");
            return null;
        }
    }
}
