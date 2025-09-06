package yapper;

import parser.*;
import ui.Ui;
import storage.Storage;
import tasklist.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Yapper {


    private final Ui ui;
    private final Storage storage = new Storage("data/yapper.txt");
    private TaskList list;
    private Parser parser = new Parser();
    final String line = "----------------------------------";


    public Yapper () {
        this.ui = new Ui();
        try {
            list = storage.loadFile();
        } catch (IOException e) {
            list = new TaskList();
        }
    }



    public void showAdded(Task task) {
        ui.showMsg(line);
        ui.showMsg("Got it. I've added this task:");
        ui.showMsg("\t" + task.toString());
        ui.showMsg(String.format("Now you have %d tasks in the list", list.size()));
        ui.showMsg(line);
    }

    public void showDeleted(int i) {
        ui.showMsg(line);
        ui.showMsg("Got it. I've deleted this task:");
        ui.showMsg("\t" + list.get(i - 1).toString());
        ui.showMsg(String.format("Now you have %d tasks in the list", list.size()));
        ui.showMsg(line);
    }


    public void run() {
        ui.showMsg("Hello! I'm Yapper! \nWhat can I do for you?");
        while (true) {
            String prompt = ui.handleCmd().trim();
            if (prompt.isEmpty()) continue;

            ParsedCommand pc = parser.parse(prompt);
            CommandType cmd = pc.getType();
            String details = pc.getDetails();
            Task task;

            try {
                switch (cmd) {
                    case BYE:
                        storage.saveTask(list);
                        ui.close();
                        return;

                    case LIST:
                        ui.showList(list);
                        break;

                    case MARK:
                        int index = Integer.parseInt(details) - 1;
                        Task currTask = list.get(index);
                        if (currTask.getStatus().equalsIgnoreCase("x")) {
                            ui.showMsg("This task is already marked!");
                            break;
                        }
                        currTask.markDone();
                        storage.saveTask(list);
                        ui.showMsg(line + "\nNice! I've marked this task as done:\n");
                        ui.showMsg(currTask.toString());
                        break;

                    case UNMARK:
                        int unmarkIndex = Integer.parseInt(details) - 1;
                        Task unmarkTask = list.get(unmarkIndex);
                        if (unmarkTask.getStatus().equalsIgnoreCase(" ")) {
                            ui.showMsg("Task is already unmarked!!");
                            break;
                        }
                        unmarkTask.unmark();
                        storage.saveTask(list);
                        ui.showMsg(line + "\nOk, I've marked this task as not done yet:\n");
                        ui.showMsg(unmarkTask.toString());
                        break;

                    case DEADLINE:
                        try {
                            task = parser.deadlineTask(details);
                            list.add(task);
                            storage.saveTask(list);
                            showAdded(task);
                        } catch (Exception e) {
                            ui.showError(e.getMessage());
                        }
                        break;

                    case EVENT:
                        try {
                            task = parser.eventTask(details);
                            list.add(task);
                            storage.saveTask(list);
                            showAdded(task);
                        } catch (Exception e) {
                            ui.showError(e.getMessage());
                        }

                        break;

                    case TODO:
                        try {
                            task = parser.todoTask(details);
                            list.add(task);
                            storage.saveTask(list);
                            showAdded(task);
                        } catch (Exception e) {
                            ui.showError(e.getMessage());
                        }
                        break;

                    case DELETE:
                        try {
                            if (details.isEmpty()) {
                                throw new Exception("Please choose task to delete!");
                            }
                            int taskNum = Integer.parseInt(details) - 1;
                            list.remove(taskNum);
                            storage.saveTask(list);
                            showDeleted(taskNum);
                        } catch (Exception e) {
                            ui.showError(e.getMessage());
                        }
                        break;

                    default:
                        ui.showMsg("Sorry, I'm not sure what you mean!");
                        break;

                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }

            }
        }

    public static void main(String[] args) {
        new Yapper().run();
    }
}
