package com.logmonitor;

import com.logmonitor.objects.Job;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, Job> parseJobs(){
        try{
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\owens\\Desktop\\LogMonitor\\logmonitor\\src\\main\\resources\\logs[7][50][15].log");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            Map<String, Job> jobs = new HashMap();

            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] parts = line.split(",");
                String jobId = parts[3].strip();
                String time = parts[0];
                String status = parts[2].strip();

                Job job = jobs.getOrDefault(jobId, new Job());
                job.setJobId(jobId);

                if(status.equals("START")) {
                    job.setStartTime(time);
                } else if (status.equals("END")) {
                    job.setEndTime(time);
                }

                jobs.put(jobId, job);
            }
            fileInputStream.close();
            return jobs;
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return new HashMap<>();
    }

    public static void main(String[] args) {
        Map<String, Job> jobs = parseJobs();
        for(Job job: jobs.values()){
            System.out.println(job.getJobId());
        }
    }
}