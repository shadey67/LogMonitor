package com.logmonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.logmonitor.constants.Constants;
import com.logmonitor.objects.Job;

public class MainTest {

    @Test
    void parseJobsCreatesCorrectly() throws Exception {
        String log =
                "11:35:23,INFO,START,J123\n" +
                "11:36:18,INFO,END,J123\n";
        try (InputStream in = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8))) {
            
            Map<String, Job> jobs = Main.parseJobs(in);

            Job jobA = jobs.get("J123");
            assertEquals(1, jobs.size());
            assertEquals("11:35:23", jobA.getStartTime());
            assertEquals("11:36:18", jobA.getEndTime());
        }
    }

    @Test
    void parseJobsCreatesCorrectly_NoEndTime() throws Exception{
                String log =
                "11:35:23,INFO,START,J123\n";
        try (InputStream in = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8))) {
            
            Map<String, Job> jobs = Main.parseJobs(in);

            Job jobA = jobs.get("J123");
            assertEquals(1, jobs.size());
            assertEquals("11:35:23", jobA.getStartTime());
            assertEquals(null, jobA.getEndTime());
        }
    }
    
    @Test
    void getStatus_Pass(){
        Job pass = new Job();
        pass.setStartTime("17:25:00");
        pass.setEndTime("17:27:00");
        
        Main.getStatus(pass);
        assertEquals(pass.getStatus(), Constants.JOB_PASS);
    }

    @Test
    void getStatus_Warning(){
        Job warning = new Job();
        warning.setStartTime("17:25:00");
        warning.setEndTime("17:31:00");

        Main.getStatus(warning);
        assertEquals(warning.getStatus(), Constants.JOB_WARNING);
    }

    @Test
    void getStatus_Error(){
        Job error = new Job();
        error.setStartTime("17:25:00");
        error.setEndTime("17:37:00");
        
        Main.getStatus(error);
        assertEquals(error.getStatus(), Constants.JOB_ERROR);
    }
}
