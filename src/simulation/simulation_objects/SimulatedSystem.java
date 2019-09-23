package simulation.simulation_objects;

import static java.lang.Math.min;

import simulation.simulation_objects.CustomsQueues;
import simulation.simulation_objects.MainQueue;
import simulation.simulation_objects.QueueElement;

/**
 * Representation of whole system - one main queue and dwo customs queues
 */
public class SimulatedSystem {

    public CustomsQueues customsQueues;

    public MainQueue mainQueue;

    public SimulatedSystem(MainQueue mainQueue, CustomsQueues customsQueues) {
        this.mainQueue = mainQueue;
        this.customsQueues = customsQueues;
    }

    public void add(QueueElement queueElement) {
        mainQueue.add(queueElement);
    }

    public boolean isNotEmpty() {
        return customsQueues.isNotEmpty();
    }

    public void printCurrentStatus() {
        mainQueue.print();
        customsQueues.print();
    }

    /**
     * moves customs queues in time, then if possible moves elements from main queue to one of customs
     * increments time spent in queues
     */
    public void step() {
        customsQueues.step();

        if (mainQueue.isNotEmpty()) {
            if (customsQueues.add(mainQueue.getFirst())) {
                mainQueue.removeFirst();
            }
        }
        customsQueues.incrementWaitingTime();
        mainQueue.incrementWaitingTime();
    }

    /**
     * calculates estimate for element
     * if not found in main queue estimate is calculates in CustomsQueue class - if not found there warning is displayed
     * if found in main queue estimate is sum of time estimated in main queue and smaller from customs
     * @param el
     */

    public void showEstimateFor(QueueElement el) {
        int mainQueueEstimate = mainQueue.estimatedTimeInMainQueue(el);
        int customsQueueEstimate = 0;
        int result = -1;
        if (mainQueueEstimate == -1) {
            customsQueueEstimate = customsQueues.estimatedTime(el);
            if (customsQueueEstimate != -1) {
                result = customsQueueEstimate;
            }
        } else {
            result = mainQueueEstimate + min(customsQueues.rightWaitingTime(),
                    customsQueues.leftWaitingTime());
        }
        if (result == -1) {
            System.out.println("Cannot find element");
        }
        System.out.println("Estimate for element is " + result + " time units");
    }

    public void reset() {
      customsQueues.reset();
      mainQueue.reset();
    }
}
