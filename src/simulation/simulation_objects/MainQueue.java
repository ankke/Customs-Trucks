package simulation.simulation_objects;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainQueue {

    private LinkedList<QueueElement> mainQueue = new LinkedList<>();

    public void add(QueueElement el) {
        mainQueue.add(el);
    }

    /**
     * for every truck in queue its time waited is incremented
     */
    public void incrementWaitingTime() {
        mainQueue.forEach(QueueElement::addTime);
    }

    public boolean isNotEmpty() {
        return !mainQueue.isEmpty();
    }

    public QueueElement getFirst() {
        return mainQueue.peekFirst();
    }

    public void removeFirst() {
        mainQueue.poll();
    }

    private int indexOf(QueueElement el) {
        return mainQueue.indexOf(el);
    }

    public void print() {
        System.out.println("direction of lines --------------->");
        List<String> result;
        if (mainQueue.isEmpty()) {
            result = Collections.singletonList("EMPTY");

        } else {
            result = mainQueue.stream().map(QueueElement::asString).collect(Collectors.toList());
            Collections.reverse(result);
        }
        System.out.println("main line: " + result + "\n");

    }

    /**
     * Estimates time to be spend in main queue by adding durations of all elements before and dividing by 2
     * because of 2 customs queues
     *
     * @param el
     * @return -1 id element not found
     */

    public int estimatedTimeInMainQueue(QueueElement el) {
        int sum = 0;
        int index = indexOf(el);
        if (index > 0) {
            for (int i = index - 1; i >= 0; i--) {
                sum += mainQueue.get(i).getCheckDuration();
            }
        } else if (index == 0) {
            return 0;
        } else {
            return -1;
        }
        return sum / 2;
    }

    public void reset() {
        mainQueue = new LinkedList<>();
    }
}
