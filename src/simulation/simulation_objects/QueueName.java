package simulation.simulation_objects;

public enum QueueName {
    RIGHT ("right line"),
    LEFT ("left line");

    QueueName(String queueName) {
        this.queueName = queueName;
    }
    private String queueName;

    public String getQueueName(){
        return queueName;
    }
}
