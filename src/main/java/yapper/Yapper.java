package yapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Yapper {

    private static final String SAVE_FILE = "../data/yapper.txt";
    private static ArrayList<Task> list;

    private static void saveToFile() {
        try {
            Path path = Paths.get(SAVE_FILE);
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();
            for (Task task : list) {
                String completed = "x".equalsIgnoreCase(task.getStatus().trim()) ? "X" : "0";
                if (task instanceof Todo) {
                    lines.add(String.join(" | ", "T", completed, ((Todo) task).getDescription()));
                } else if (task instanceof Deadline) {
                    Deadline deadlineTask = (Deadline) task;
                    lines.add(String.join(" | ", "D", completed, deadlineTask.getDescription().trim(), "by: ", DateParser.formatDate(deadlineTask.getDeadline())));
                } else if (task instanceof Event) {
                    Event eventTask = (Event) task;
                    lines.add(String.join(" | ", "E", completed, eventTask.getDescription().trim(), "from: ",  DateParser.formatDate(eventTask.getStartTime()), "to: ", DateParser.formatDate(eventTask.getEndTime())));
                }
            }
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadFiles() {
        list = new ArrayList<>();
        Path path = Paths.get(SAVE_FILE);

        if (!Files.exists(path)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                if (parts.length < 3) {
                    continue;
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
                        continue;
                }
                if (task != null && marked) {
                    task.markDone();
                }
                if (task != null) {
                    list.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadFiles();
        System.out.println("Hello! I'm Yapper!\nWhat can I do for you?");

        String line = "------------------------------";

        Scanner sc = new Scanner(System.in);

        while (true) {
            if (!sc.hasNextLine()) {
                break;
            }

            String prompt = sc.nextLine().trim();
            String parts[] = prompt.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String details = parts.length > 1 ? parts[1].trim() : "";
            Task task;

            switch (cmd) {
                case "bye":
                    sc.close();
                    System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
                    return;

                case "list":
                    int len = list.size();
                    String ls = "";
                    for (int i = 1; i <= len; i++) {
                        String curr = String.format("%d.%s\n", i, list.get(i - 1).toString());
                        ls = ls + curr;
                    }
                    System.out.println(line + "\n" + "Here are the tasks in your list:\n");
                    System.out.println(ls + line);
                    break;

                case "mark":
                    int index = Integer.parseInt(details) - 1;
                    Task currTask = list.get(index);
                    if (currTask.getStatus().equalsIgnoreCase("x")) {
                        System.out.println("This task is already marked!");
                        break;
                    }
                    currTask.markDone();
                    saveToFile();
                    System.out.println(line + "\nNice! I've marked this task as done:\n");
                    System.out.println(currTask.toString());
                    break;

                case "unmark":
                    int unmarkIndex = Integer.parseInt(details) - 1;
                    Task unmarkTask = list.get(unmarkIndex);
                    if (unmarkTask.getStatus().equalsIgnoreCase(" ")) {
                        System.out.println("Task is already unmarked!!");
                        break;
                    }
                    unmarkTask.unmark();
                    saveToFile();
                    System.out.println(line + "\nOk, I've marked this task as not done yet:\n");
                    System.out.println(unmarkTask.toString());
                    break;

                case "deadline":
                    try {
                        String[] deadline = details.split("/by", 2);
                        if (deadline.length < 2) {
                            throw new Exception("Please enter the task!");
                        }
                        String detail = deadline[0].trim();
                        String time = deadline[1].trim();
                        if (detail.isEmpty() || time.isEmpty()) {
                            throw new Exception("Missing deadline task details!");
                        }
                        task = new Deadline(detail, time);
                        list.add(task);
                        saveToFile();
                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("\t" + task.toString());
                        System.out.println(String.format("Now you have %d tasks in the list", list.size()));
                        System.out.println(line);
                    } catch (Exception e) {
                        System.out.println(line + "\nError: " + e + "\n" + line);
                    }
                    break;

                case "event":
                    try {
                        String[] eventTime = details.split("/from", 2);
                        if (eventTime.length < 2) {
                            throw new Exception("Please enter all relevant details for the event!");
                        }
                        String eventDetail = eventTime[0].trim();
                        String[] fromTo = eventTime[1].trim().split("/to", 2);
                        String from = fromTo[0].trim();
                        String to = fromTo[1].trim();
                        if (from.isEmpty() || to.isEmpty()) {
                            throw new Exception("Please ensure the timings are given!");
                        }
                        task = new Event(eventDetail, from, to);
                        list.add(task);
                        saveToFile();
                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("\t" + task.toString());
                        System.out.println(String.format("Now you have %d tasks in the list", list.size()));
                        System.out.println(line);
                    } catch (Exception e) {
                        System.out.println(line + "\nError: " + e + "\n" + line);
                    }

                    break;

                case "todo":
                    try {
                        if (details.isEmpty()) {
                            throw new Exception("Please enter the task!");
                        }
                        task = new Todo(details);
                        list.add(task);
                        saveToFile();
                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println("\t" + task.toString());
                        System.out.println(String.format("Now you have %d tasks in the list", list.size()));
                        System.out.println(line);
                    } catch (Exception e) {
                        System.out.println(line + "\nError: " + e + "\n" + line);
                    }
                    break;

                case "delete":
                    try {
                        if (details.isEmpty()) {
                            throw new Exception("Please choose task to delete!");
                        }
                        int taskNum = Integer.parseInt(details) - 1;
                        Task removedTask = list.get(taskNum);
                        list.remove(taskNum);
                        saveToFile();
                        System.out.println(line);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("\t" + removedTask.toString());
                        System.out.println(String.format("Now you have %d tasks in the list", list.size()));
                        System.out.println(line);
                    } catch (Exception e) {
                        System.out.println(line + "\nError: " + e + "\n" + line);
                    }
                    break;

                default:
                    System.out.println("Sorry, I'm not sure what you mean!");
                    break;

            }
        }

    }
}
