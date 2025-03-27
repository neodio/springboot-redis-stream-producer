package redis.stream.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.stream.service.JobProducer;
import redis.stream.dto.JobDto;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobProducer jobProducer;

    @PostMapping("/start")
    public String startJob(@RequestBody JobDto job) {
        return jobProducer.produce(job);
    }

    @GetMapping("/queued")
    public List<Long> getQueuedJobIds() {
        return jobProducer.getQueuedJobsIds();
    }

    @DeleteMapping("/{job_id}/queued")
    public void removeJobFromQueue(@PathVariable("job_id") Long jobId) {
        jobProducer.removeJobFromQueue(jobId);
    }

    @DeleteMapping("/queued")
    public void clearJobQueue() {
        jobProducer.clearJobQueue();
    }
}
