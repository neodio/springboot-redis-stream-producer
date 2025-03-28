package redis.stream.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import redis.stream.dto.JobDto;
import redis.stream.exception.JobAlreadyQueuedException;
import redis.stream.exception.JobNotFoundInQueueException;
import redis.stream.exception.RemovingRunningJobException;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobProducer {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${redis-stream-example.stream-key}")
    private String streamKey;

    public String produce(JobDto jobMessage) {
        if (getQueuedJobsIds().contains(jobMessage.getId())) {
            throw new JobAlreadyQueuedException(jobMessage.getId());
        }

        ObjectRecord<String, JobDto> jobRecord = StreamRecords.newRecord()
            .ofObject(jobMessage)
            .withStreamKey(streamKey);

        RecordId recordId = redisTemplate.opsForStream()
            .add(jobRecord);

        if (ObjectUtils.isEmpty(recordId)) {
            log.error("error producing message for job {}", jobMessage);
            return null;
        }

        log.info("job {} was added to the queue with id {}", jobMessage, recordId);
        return recordId.getValue();
    }

    public List<Long> getQueuedJobsIds() {
        return redisTemplate.opsForStream()
            .read(JobDto.class, StreamOffset.fromStart(streamKey))
            .stream()
            .map(Record::getValue)
            .map(JobDto::getId)
            .collect(Collectors.toList());
    }

    public void removeJobFromQueue(Long jobId) {
        List<ObjectRecord<String, JobDto>> allqueuedJobs = redisTemplate.opsForStream()
            .read(JobDto.class, StreamOffset.fromStart(streamKey));

        if (allqueuedJobs.isEmpty()) {
            throw new JobNotFoundInQueueException(jobId);
        }

        allqueuedJobs.stream()
            .findFirst()
            .map(Record::getValue)
            .map(JobDto::getId)
            .filter(jobId::equals)
            .ifPresent(firstJobId -> {
                throw new RemovingRunningJobException(jobId);
            });

        allqueuedJobs.stream()
            .skip(1)
            .filter(jobRecord -> jobRecord.getValue().getId().equals(jobId))
            .findFirst()
            .ifPresentOrElse(jobRecord -> redisTemplate.opsForStream().delete(jobRecord),
                () -> {
                    throw new JobNotFoundInQueueException(jobId);
                }
            );
    }

    public void clearJobQueue() {
        redisTemplate.opsForStream()
            .trim(streamKey, 0);
    }
}
