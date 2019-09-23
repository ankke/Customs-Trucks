package command_line_controller;

import simulation.SimulationWithVisualisation;
import simulation.SimulationWithoutVisualisation;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1){
            printHelp();
        }
        else if(args[0].trim().equals("1")) {
            Commander.executeManualSimulation();
        }
        else if(args[0].trim().equals("2")){
            Commander.execute(new SimulationWithVisualisation());
        }
        else if(args[0].trim().equals("3")){
            Commander.execute(new SimulationWithoutVisualisation());
        }
        else{
           printHelp();
        }
    }

    private static void printHelp(){
        System.out.println("Add \"1\" to command line arguments for manual simulation");
        System.out.println("Add \"2\" for automatic simulation with visualisation");
        System.out.println("Add \"3\" for automatic simulation without visualisation");
    }
}
