package redis.stream.exception;

public class RemovingRunningJobException extends RuntimeException {
    public RemovingRunningJobException(Long jobId) {
        super("job with id " + jobId + " is running and can't be removed from the queue");
    }
}
