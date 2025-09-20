package storage;

import parser.DateParser;
import tasklist.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import tasklist.*;


public class Storage {
    private final Path file;

    public Storage(String path) {
        this.file = Paths.get(path);
    }

    /**
     * @brief                   writes the current list into a file that can be opened again on new runs
     * @param list              task list to save
     * @throws IOException
     */
    public void saveTask(TaskList list) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            for (Task t : list.asList()) {
                bw.write(encode(t) + "\n");
            }
        }
    }

    /**
     * @brief               loads a determined savefile containing a task list
     * @return              a tasklist that contains information from previous runs
     * @throws IOException
     */
    public TaskList loadFile() throws IOException {
        TaskList list = new TaskList();
        if (!Files.exists(file)) {
            return list;
        }
        for (String line : Files.readAllLines(file)) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            Task t = decode(line);
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * @brief           saves a task in a standard format in the save file such that it is easier for parser to read and decode
     * @param task      current task to save into the save file
     * @return          a string that details the task information in a specific order
     */
    public String encode (Task task) {
        String completed = "x".equalsIgnoreCase(task.getStatus().trim()) ? "X" : "0";
        if (task instanceof Todo) {
            return String.join(" | ", "T", completed, ((Todo) task).getDescription());
        } else if (task instanceof Deadline) {
            Deadline deadlineTask = (Deadline) task;
            return String.join(" | ", "D", completed, deadlineTask.getDescription().trim(), "by:", DateParser.formatDate(deadlineTask.getDeadline()));
        } else if (task instanceof Event) {
            Event eventTask = (Event) task;
            return String.join(" | ", "E", completed, eventTask.getDescription().trim(), "from:",  DateParser.formatDate(eventTask.getStartTime()), "to:", DateParser.formatDate(eventTask.getEndTime()));
        }
        throw new IllegalArgumentException("Unknown task type: " + task.getClass());
    }

    /**
     * @brief       unpacks the save file information and creates the task based on the information on that file
     * @param line  the current line the parser is looking at in the save file, each line details one task
     * @return      a specific task, either a Todo, Deadline or Event
     */
    public Task decode(String line) {
        try{
            String[] parts = line.split("\\|");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }
            if (parts.length < 3) {
                return null;
            }
            String type = parts[0].trim();
            boolean marked = "x".equalsIgnoreCase(parts[1].trim());
            Task task = null;

            switch (type) {
                case "T":
                    task = new Todo(parts[2]);
                    break;
                case "D":
                    if (parts.length >= 4) {
                        task = new Deadline(parts[2], parts[4]);
                    }
                    break;
                case "E":
                    if (parts.length >= 5) {
                        task = new Event(parts[2], parts[4], parts[6]);
                    }
                    break;
                default:
                    return null;
            }
            if (task != null && marked) {
                task.markDone();
            }
            return task;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
