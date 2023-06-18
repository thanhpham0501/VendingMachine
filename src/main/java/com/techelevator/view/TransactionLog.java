
package com.techelevator.view;

import java.io.*;

public class TransactionLog {

    private final String pathToLogFile;

    private final PrintWriter out;

    private File logFile;

    public TransactionLog(String pathToLogFile) throws Exception {
        this.pathToLogFile = pathToLogFile;
        initiateLogFile();
        this.out = new PrintWriter(new FileWriter(pathToLogFile, true));
    }

    public void initiateLogFile() throws IOException {
        this.logFile = new File(pathToLogFile);
        if(!logFile.exists()) {
            logFile.createNewFile();
        }
    }

    public void log(String logDetail) {
        // Write logDetail to the file
        out.println(logDetail);
    }

    public void flushAndCloseLog() {
        out.flush();
        out.close();
    }
}
