package simulation.simulation_objects;

public class QueueElement {
    /**
     * time to spend in customs check - depends on weight
     */
    private int checkDuration;
    /**
     * time already waited in queues
     */
    private int timeWaited = 0;

    public QueueElement(int time) {
        this.checkDuration = time;
    }

    public QueueElement() {
        this.checkDuration = 0;
    }

    public int getCheckDuration() {
        return checkDuration;
    }

    public int getWaitingTime() {
        return timeWaited;
    }

    public void addTime() {
        ++timeWaited;
    }

    public void decrementTimeLeft() throws Exception {
        --checkDuration;
        if (isReady()) {
            throw new Exception("ready");
        }
    }

    public boolean isReady() {
        if (checkDuration <= 0) {
            checkDuration = 0;
            return true;
        }
        return false;
    }

    public String asString() {
        return " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueueElement)) return false;
        QueueElement that = (QueueElement) o;
        return checkDuration == that.checkDuration;
    }
}
