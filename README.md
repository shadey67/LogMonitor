# Log Monitor
This is a Log Monitor application written in Java 17 for a technical challenge set by LSEG.

The application reads the given log from the project structure, creates a job object for each line, finds the run time for each job, and finally outputs all 'faulty' jobs.

I have made the following assumptions:
- A job which runs for exactly 5 minutes should give a warning
- A job which runs for exactly 10 minutes should give an error
- A job which doesn't have an end time is output as "caution"

The project uses JUnit for testing.

# Further Work
In further iterations, the application would be able to handle multiple log files in different locations.
This could be done in many different ways, for example by passing the path to the log as a command line argument.

# Thanks
Thanks to LSEG for giving me the opportunity to complete this technical assessment, and I hope to hear back soon. 

