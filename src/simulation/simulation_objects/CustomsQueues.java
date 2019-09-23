package simulation.simulation_objects;

import static simulation.simulation_objects.QueueName.LEFT;
import static simulation.simulation_objects.QueueName.RIGHT;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.signum;
import static java.lang.Math.abs;

/**
 * Class representing two queues to customs check
 * Defining algorithm of adding and shuffling trucks
 */
public class CustomsQueues {

    private Queue leftQueue = new Queue(LEFT);

    private Queue rightQueue = new Queue(RIGHT);

    /**
     * leftCheck and rightCheck represent a queue elements that is being check in customs check on left or right
     */
    private QueueElement leftCheck = new QueueElement();
    private QueueElement rightCheck = new QueueElement();

    /**
     * timeWaited and finishedTrucks hold values of time spent in queues and number of (0-2) trucks,
     * which finished check in current step
     * Both used by Statistics class
     */
    private int timeWaited = 0;
    private int finishedTrucks = 0;

    /**
     *
     * @param queueElement
     * @return true if adding finished with success or false if queues are full
     * or waiting in Main Queue could be more efficient
     */
    public boolean add(QueueElement queueElement) {

        if (rightQueue.size() >= 5 && leftQueue.size() >= 5) {
            return false;
        }

        if (rightQueue.size() == 0 && rightCheck.getCheckDuration() == 0) {
            rightCheck = queueElement;
            return true;
        } else if (leftQueue.size() == 0 && leftCheck.getCheckDuration() == 0) {
            leftCheck = queueElement;
            return true;
        }

        if (rightWaitingTime() == leftWaitingTime()) {
            if (rightQueue.size() < leftQueue.size()) {
                rightQueue.add(queueElement);
            } else if (leftQueue.size() < 5) {
                leftQueue.add(queueElement);
            }
        } else if (rightQueue.size() < 5 && rightWaitingTime() < leftWaitingTime()) {
            rightQueue.add(queueElement);
        } else if (leftQueue.size() < 5 && leftWaitingTime() < rightWaitingTime()) {
            leftQueue.add(queueElement);
        }
        else {
            return false;
        }
        optimize();
        return true;

    }

    /**
     * Optimization by searching for configuration with the most sorted elements witch still remains balanced
     */
    private void optimize() {
        int[] bitArr = new int[5];
        makeSorted(bitArr, 0);
        //        while (!areQueuesBalanced()) {
        //            shuffle();
        //        }
    }

    /**
     * Generates 4 elements bit arrays to later generate all possible queue configurations
     * @param bitArr
     * @param i
     */

    public void makeSorted(int[] bitArr, int i) {
        if (i == 4) {
            shuffle(bitArr);
        } else {
            bitArr[i] = 0;
            makeSorted(bitArr, i + 1);
            bitArr[i] = 1;
            makeSorted(bitArr, i + 1);
        }
    }

    /**
     * For every 0 in bit array shuffle is performed (truck on left is switched with right)
     * If obtained order is the most sorted from all previous ones and still remains balanced
     * operation od shuffling back is not performed and mostSorted flag is raised
     * @param indexes array od indexes to shuffle
     */

    private void shuffle(int[] indexes) {
        boolean mostSorted = false;
        int sortStatus = calcSorted();
        int k = 1;
        while (!mostSorted && k <= 2) {
            for (int i = 1; i < 5; i++) {
                if (indexes[i - 1] == 0) {
                    QueueElement tmpRight = rightQueue.onIndex(i);
                    QueueElement tmpLeft = leftQueue.onIndex(i);
                    if (!tmpRight.equals(new QueueElement()) && !tmpLeft.equals(new QueueElement())) {
                        rightQueue.switchElement(i, tmpLeft);
                        leftQueue.switchElement(i, tmpRight);
                    }
                }
            }
            k++;
            if (calcSorted() > sortStatus && areQueuesBalanced()) {
                mostSorted = true;
            }
        }
    }

    /**
     * Calculates number of ascending following pars in configuration
     */

    private int calcSorted() {
        int howSorted = 0;
        for (int i = 0; i < rightQueue.size() - 1; i++) {
            if (rightQueue.onIndex(i)
                    .getCheckDuration() <= rightQueue.onIndex(i + 1)
                    .getCheckDuration()) {
                howSorted++;
            }
        }
        for (int i = 0; i < leftQueue.size() - 1; i++) {
            if (leftQueue.onIndex(i)
                    .getCheckDuration() <= leftQueue.onIndex(i + 1)
                    .getCheckDuration()) {
                howSorted++;
            }
        }
        return howSorted;
    }

    /**
     * Increments waiting time of all elements in queues
     */
    public void incrementWaitingTime() {
        rightQueue.incrementTime();
        leftQueue.incrementTime();
    }

    /**
     * Finds element in queue and calculates remaining time
     * @param el
     * @return remaining time, 0 if elements is currently checked or -1 if element is not present in any of lists
     */
    public int estimatedTime(QueueElement el) {
        int result = 0;

        int index = rightQueue.indexOf(el);
        if (index != -1) {
            for (int i = index - 1; i >= 0; i--) {
                result += rightQueue.onIndex(i)
                        .getCheckDuration();
            }
            result += rightCheck.getCheckDuration();
            return result;
        }

        index = leftQueue.indexOf(el);
        if (index != -1) {
            result += leftCheck.getCheckDuration();
            for (int i = index - 1; i >= 0; i--) {
                result += leftQueue.onIndex(i)
                        .getCheckDuration();
            }
            return result;
        }

        if (rightCheck.equals(el) || leftCheck.equals(el)) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * @return sum of overall waiting time in right or left queue
     */

    public int rightWaitingTime() {
        return rightQueue.sumOfWaitingTimeInQueue() + rightCheck.getCheckDuration();
    }

    public int leftWaitingTime() {
        return leftQueue.sumOfWaitingTimeInQueue() + leftCheck.getCheckDuration();
    }

    /**
     * @return true if there is less than 1 element in every queue, true if queues are balanced
     * (meaning that does not exist any possible shuffle between right and left queue thad would reduce
     * overall waiting time difference between queues)
     * false otherwise
     */
    private boolean areQueuesBalanced() {
        return smallestDifferenceDoubled() == 0 || abs(calculateDifferenceInTime()) <= abs((calculateDifferenceInTime() - smallestDifferenceDoubled()));
    }

    private int calculateDifferenceInTime() {
        int rightQueueWaitingTime = rightQueue.sumOfWaitingTimeInQueue() + rightCheck.getCheckDuration();
        int leftQueueWaitingTime = leftQueue.sumOfWaitingTimeInQueue() + leftCheck.getCheckDuration();
        return rightQueueWaitingTime - leftQueueWaitingTime;
    }

    /**
     * @return doubled value of smallest difference in check duration between corresponding elements in queues
     */
    private int smallestDifferenceDoubled() {
        int smallestDiff = MAX_VALUE;
        for (int i = 1; i < 5; i++) {
            int currentDiff = rightQueue.onIndex(i)
                    .getCheckDuration() - leftQueue.onIndex(i)
                    .getCheckDuration();
            if (abs(smallestDiff) > abs(currentDiff) && currentDiff != 0 && signum(currentDiff) == signum(calculateDifferenceInTime())) {
                smallestDiff = currentDiff;
            }
        }
        if (smallestDiff == MAX_VALUE)
            return 0;
        return 2 * smallestDiff;
    }

    public void print() {
        System.out.printf("%-10s ---> %130s\t | in check: %10s - time left: %1s\n", leftQueue.getQueueName(), leftQueue.asRevertedListOfStrings(),
                leftCheck.asString(), leftCheck.getCheckDuration());
        System.out.printf("%-10s ---> %130s\t | in check: %10s - time left: %1s\n", rightQueue.getQueueName(), rightQueue.asRevertedListOfStrings(),
                rightCheck.asString(), rightCheck.getCheckDuration());
    }

    /**
     * Simulates step in time
     * Resets counters because of new step
     * If check is finished first truck from queue is moved forward
     * time spend in queues is assigned to timeWaited and counter od finished Trucks is incremented
     * If there are no trucks left in queue, default element is assigned to left or rightCheck
     */
    public void step() {
        timeWaited = 0;
        finishedTrucks = 0;
        try {
            rightCheck.decrementTimeLeft();
        } catch (Exception e) {
            if (rightQueue.size() > 0) {
                rightCheck = rightQueue.poll();
                timeWaited = rightCheck.getWaitingTime();
                finishedTrucks++;
            } else {
                rightCheck = new QueueElement();
            }
        }
        try {
            leftCheck.decrementTimeLeft();
        } catch (Exception e) {
            if (leftQueue.size() > 0) {
                leftCheck = leftQueue.poll();
                timeWaited += leftCheck.getWaitingTime();
                finishedTrucks++;
            } else {
                leftCheck = new QueueElement();
            }
        }
    }

    /**
     * returns false if there is still some element in queues or check
     * @return
     */
    public boolean isNotEmpty() {
        return rightQueue.isNotEmpty() || leftQueue.isNotEmpty() || !rightCheck.isReady() || !leftCheck.isReady();
    }

    public int getTimeWaited() {
        return timeWaited;
    }

    public int getFinishedTrucks() {
        return finishedTrucks;
    }

    public void reset() {
        leftQueue = new Queue(LEFT);
        rightQueue = new Queue(RIGHT);
        leftCheck = new QueueElement();
        rightCheck = new QueueElement();
        timeWaited = 0;
        finishedTrucks = 0;
    }
}
