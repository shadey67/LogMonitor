package com.logmonitor;

import com.logmonitor.objects.Job;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, Job> parseJobs(){
        try{
            FileInputStream fileInputStream = new FileInputStream("logmonitor/src/main/resources/logs[7][50][15].log");
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

    public static void getStatus(Job job){
        
        //I've assumed that if the job hasn't completed at time of processing we mark it as an error
        if(job.getEndTime() == null){
            job.setStatus("ERROR");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.parse(job.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(job.getEndTime(), formatter);

        long minutesBetween = ChronoUnit.MINUTES.between(startTime, endTime);
        job.setMinutes(String.valueOf(minutesBetween));

        if(minutesBetween<5){
            job.setStatus("PASS");
        }
        else if(minutesBetween<10){
            job.setStatus("WARNING");
        }
        else{
            job.setStatus("ERROR");
        }
    }

        public static void outputFaultyJobs(Map<String, Job> jobs) {
        try{
            FileWriter writer = new FileWriter("output.txt");
            for(Job job : jobs.values()){

                //I've decided to treat those jobs that didn't finish by time of processing differently from actual errors, thus the logging is slightly different
                if(job.getMinutes() == null){
                    writer.write("CAUTION: Job " + job.getJobId() + " starting at " + job.getStartTime() + " was not complete at time of processing.\n");
                }
                else if(job.getStatus().equals("WARNING")){
                    writer.write("WARNING" + ": Job " + job.getJobId() + " starting at " + job.getStartTime() + " took " + job.getMinutes() + " minutes.\n");
                }
                else if(job.getStatus().equals("ERROR")){
                    writer.write("ERROR" + ": Job " + job.getJobId() + " starting at " + job.getStartTime() + " took " + job.getMinutes() + " minutes.\n");
                }
            }
            writer.close();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<String, Job> jobs = parseJobs();
        for(Job job: jobs.values()){
            getStatus(job);
        }
        outputFaultyJobs(jobs);
    }
}