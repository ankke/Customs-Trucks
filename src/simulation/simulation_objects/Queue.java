package simulation.simulation_objects;

import java.util.*;
import java.util.stream.Collectors;

public class Queue {
    private QueueName queueName;
    private LinkedList<QueueElement> queue = new LinkedList<>();

    public Queue(QueueName name) {
        this.queueName = name;
    }

    public String getQueueName() {
        return queueName.getQueueName();
    }

    public int size() {
        return queue.size();
    }

    public void add(QueueElement element) {
        queue.add(element);
    }

    public int sumOfWaitingTimeInQueue() {
        return queue.stream().map(QueueElement::getCheckDuration).reduce(0, (subSum, time) -> subSum + time);
    }

    /**
     * @return default element if not found for arithmetic purposes in CustomsQueues class
     */
    public QueueElement onIndex(int i) {
        if(queue.size() <= i || Objects.isNull((queue.get(i)))) return new QueueElement();
        return queue.get(i);
    }

    public List<String> asRevertedListOfStrings() {
        if(queue.isEmpty()){
            return Collections.singletonList("EMPTY");
        }
        List result = queue.stream().map(QueueElement::asString).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    public QueueElement poll() {
        return queue.poll();
    }

    public void incrementTime(){
        queue.forEach(QueueElement::addTime);
    }

    public void switchElement(int index, QueueElement newElement){
        queue.set(index, newElement);
    }

    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    public int indexOf(QueueElement el) {
        return queue.indexOf(el);
    }
}
