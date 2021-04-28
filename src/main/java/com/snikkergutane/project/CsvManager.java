package com.snikkergutane.project;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvManager {
    private File csvOutputFile;
    private String saveFileDirectory;

    public CsvManager() {
        //Intentionally empty
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
            csvOutputFile = new File(saveFileDirectory,fileName + ".csv");
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
    public Project importCsv(File file) throws ArrayIndexOutOfBoundsException {
        Project returnProject = null;
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
            } catch (IOException io) {
                return null;
            }
            List<String> projectInfo = records.get(0);
            Project project = new Project(projectInfo.get(0), projectInfo.get(1),
                    projectInfo.get(2), projectInfo.get(3), projectInfo.get(4),
                    LocalDate.parse(projectInfo.get(5)), LocalDate.parse(projectInfo.get(6)), projectInfo.get(7));
            records.remove(0);

            List<Comment> commentList = new ArrayList<>();
            records.forEach(line -> {
                //if a line starts with "+" it is interpreted as a new task.
                if (line.get(0).startsWith("+")) {
                    String name = line.get(0).replace("+", "");
                    LocalDate startDate = LocalDate.parse(line.get(1));
                    LocalDate endDate = LocalDate.parse(line.get(2));
                    String description = line.get(3);

                    //Removes the information we've already used so that only the image urls remain.
                    ArrayList<String> imageUrls = new ArrayList<>(line);
                    int numberOfImages = line.size() - 4;
                    while (imageUrls.size() > numberOfImages) {
                        imageUrls.remove(0);
                    }

                    //Creates the task
                    Task task = new Task(name, startDate, endDate, description, imageUrls, project.getTasks().size()+1);
                    commentList.forEach(task::addComment);
                    commentList.clear();
                    project.addTask(task);
                } else {
                    //If the line does not start with "+", it is read as a new comment.
                    LocalDate date = LocalDate.parse(line.get(0));
                    String user = line.get(1);
                    String commentText = line.get(2);

                    //Removes the information we've already used so that only the image urls remain.
                    ArrayList<String> imageUrls = new ArrayList<>(line);
                    int numberOfImages = line.size() - 3;
                    while (imageUrls.size() > numberOfImages) {
                        imageUrls.remove(0);
                    }

                    commentList.add(new Comment(date, user, commentText, imageUrls.get(0)));
                }
        });
            returnProject = project;
    }
        return returnProject;
    }

    public List<Project> importFolder(File directory) throws IOException {
        List<Project> projects = new ArrayList<>();
        Path dir = directory.toPath();
        DirectoryStream<Path> stream =
                Files.newDirectoryStream(dir, "*.csv");
            for (Path entry : stream) {
                projects.add(importCsv(entry.toFile()));
            }
            stream.close();
            return projects;
    }
}
