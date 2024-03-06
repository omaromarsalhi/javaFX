package pidev.javafx.controller.reclamation;

import java.io.IOException;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class CallPythonFromJava {
    public static String run()  {
        String pythonScriptPath = "src/main/java/pidev/javafx/python/reco.py";

        ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/khali/anaconda3/python.exe", pythonScriptPath, "voice");
        processBuilder.redirectErrorStream(true);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

        // Read the output from the Python script
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException( e );
            }
            output.append(line).append("\n");
        }

        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }
        return output.toString();
    }
}

