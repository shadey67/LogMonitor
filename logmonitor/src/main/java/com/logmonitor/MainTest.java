package com.logmonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.logmonitor.objects.Job;

public class MainTest {
    
    @Test
    void getStatus_Pass(){
        Job error = new Job();
        error.setStartTime("17:25:00");
        error.setEndTime("17:27:00");
        
        assertEquals(error.getStatus(), "pass");
    }

    @Test
    void getStatus_Warning(){
        Job error = new Job();
        error.setStartTime("17:25:00");
        error.setEndTime("17:31:00");
        
        assertEquals(error.getStatus(), "warning");
    }

    @Test
    void getStatus_Error(){
        Job error = new Job();
        error.setStartTime("17:25:00");
        error.setEndTime("17:37:00");
        
        assertEquals(error.getStatus(), "error");
    }
}
