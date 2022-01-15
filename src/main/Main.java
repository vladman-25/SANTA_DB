package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.Constants;
import data.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import static data.InputReader.readData;
import static tasks.Simulation.simulateForInput;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) {

        File theDir = new File("output");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }


        for (int i = 1; i <= Constants.TESTS_NUMBER; i++) {
            Database.getDatabase();

            String inputPath = Constants.TESTS_PATH + String.valueOf(i) + Constants.FILE_EXTENSION;
            readData(inputPath);

            String outputPath = Constants.OUTPUT_PATH + String.valueOf(i)
                    + Constants.FILE_EXTENSION;
            File outputFile = new File(outputPath);
            outputFile.delete();
            try {
                outputFile.createNewFile();

                FileWriter writer = new FileWriter(outputPath);

                ObjectMapper mapper = new ObjectMapper().
                        enable(SerializationFeature.INDENT_OUTPUT);
                String json = mapper.writeValueAsString(simulateForInput());
                writer.write(json);
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            Database.getDatabase().destroyDatabase();
        }


        Checker.calculateScore();
    }
}
