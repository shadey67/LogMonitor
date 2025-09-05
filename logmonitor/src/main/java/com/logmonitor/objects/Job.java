package com.logmonitor.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job {
    private String jobId;
    private String startTime;
    private String endTime;
    private String status;
    private String minutes;
}
