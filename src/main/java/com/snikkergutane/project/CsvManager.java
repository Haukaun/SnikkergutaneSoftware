package com.snikkergutane.project;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvManager {
    private File csvOutputFile;
    private String saveFileDirectory;
    private final Window stage;


    public CsvManager(Window stage) {
        this.stage = stage;
    }

    /**
     * Converts a string array to a .csv-friendly format.
     * @param data {@code String[]} of data to be converted.
     * @return {@code String} of converted data.
     */
    public String convertToCsv(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(";"));
    }

    /**
     * Encapsulates semicolons and double quotes in double quotes,
     * and replaces new lines with whitespace.
     * This all to make the String .csv-friendly.
     * @param data {@code String} of data to be converted.
     * @return {@code String} of .csv-compatible data.
     */
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(";") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    /**
     * Creates a new .csv file in the given directory.
     * @param fileName {@code String} name of the file to be created.
     * @return {@code boolean} true if the file was created,
     *      or {@code boolean} false if the file already exists.
     */
    public boolean createCsvFile(String fileName) {
        boolean success = false;
        try {
            csvOutputFile = new File(saveFileDirectory + "/" + fileName + ".csv");
            if (csvOutputFile.createNewFile()) {
                success = true;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
        return success;
    }

    /**
     * Sets the directory where new files will be created.
     * @param url the directory where new files will be created.
     */
    public void setSaveFileDirectory(String url) {
        this.saveFileDirectory = url;
    }

    /**
     * Writes the given list of string to this object's csvOutPutFile, in a .csv format.
     * @param dataList {@code List<String[]>} of data to be written to file.
     */
    public void writeToCsv(List<String[]> dataList) {
        try (BufferedWriter bw = new BufferedWriter((
                new OutputStreamWriter(
                        new FileOutputStream(csvOutputFile), StandardCharsets.UTF_8)))) {
            bw.newLine();
            for (String[] strings : dataList) {
                String s = convertToCsv(strings);
                bw.write(s);
                bw.newLine();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Parses a .csv file and returns it in a list format.
     * @return {@code List<List<String} of the content of the .csv file, returns an empty list if no file is chosen,
     *      and returns a List with one empty String if unable to parse file.
     */
    public List<List<String>> importCsv() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open resource file");
        File file = fileChooser.showOpenDialog(stage);
        ArrayList<List<String>> records = new ArrayList<>();
        Charset charset = StandardCharsets.UTF_8;
        if (null != file) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), charset))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
                return records;
            } catch (Exception exception) {
                return new ArrayList<>(Collections.singleton(
                        new ArrayList<>(Collections.singleton(""))));
            }
        } else {
            return records;
        }
    }
}
