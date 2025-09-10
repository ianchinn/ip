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



    public String showAdded(Task task) {

        return line + "\nAye. Big man gone do big man things:\n" + "\t" + task.toString() +
                "\n" + String.format("Now you have %d tasks in the list", list.size()) + "\n" + line;
    }

    public String showDeleted(Task task) {
        return line + "\nAye. I done removed this:\n" + "\t" + task.toString() +
                "\n" + String.format("Now you have %d tasks in the list", list.size()) + "\n" + line;
    }

    public String showMatched(List<Task> list ) {
        String res = "";
        String reply = "";
        if (list.isEmpty()) {
            return "None of that around here big man";
        } else {
            reply = "\nI got what you looking for innit:\n";
            for (int i = 0; i < list.size(); i++) {
                res = (i+ 1) +"." + list.get(i);
            }
        }
        return line + reply + res + "\n" + line;
    }

    public String getResponse(String input) {
        ParsedCommand pc = parser.parse(input);
        CommandType cmd = pc.getType();
        String details = pc.getDetails();
        Task task;

        try {
            switch (cmd) {
                case BYE:
                    storage.saveTask(list);
                    return "See you around innit";
                case LIST:
                    return line + "\n" + "Check it out blud:\n" + list.loadList();
                case MARK:
                    int index = Integer.parseInt(details) - 1;
                    Task currTask = list.get(index);
                    if (currTask.getStatus().equalsIgnoreCase("x")) {
                        return "My man did you forget marking this?";
                    }
                    currTask.markDone();
                    storage.saveTask(list);
                    return line + "\nAight G. I've marked this task as done:\n" + currTask.toString();

                case UNMARK:
                    int unmarkIndex = Integer.parseInt(details) - 1;
                    Task unmarkTask = list.get(unmarkIndex);
                    if (unmarkTask.getStatus().equalsIgnoreCase(" ")) {
                        return "Big man this was never marked";
                    }
                    unmarkTask.unmark();
                    storage.saveTask(list);
                    return line + "\nWhatever you say G:\n" + unmarkTask.toString();
                case TODO:
                    task = parser.todoTask(details);
                    list.add(task);
                    storage.saveTask(list);
                    return showAdded(task);
                case DEADLINE:
                    task = parser.deadlineTask(details);
                    list.add(task);
                    storage.saveTask(list);
                    return showAdded(task);
                case EVENT:
                    task = parser.eventTask(details);
                    list.add(task);
                    storage.saveTask(list);
                    return showAdded(task);
                case DELETE:
                    if (details.isEmpty()) {
                        return "My mans done left me hanging..";
                    }
                    int taskNum = Integer.parseInt(details) - 1;
                    if (taskNum < 0 || taskNum >= list.size()) {
                        return String.format("Mans dont got that many tasks. Choose a number between 1 - %s", list.size());
                    }
                    Task removedTask = list.get(taskNum);
                    list.remove(removedTask);
                    storage.saveTask(list);
                    return showDeleted(removedTask);
                case FIND:
                    List<Task> matches = list.findTask(details);
                    return showMatched(matches);
                default:
                    return "Big mans done talk bout some " + "'" + input + "'";


            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }


//    public void run() {
//        ui.showMsg("Hello! I'm Yapper! \nWhat can I do for you?");
//        while (true) {
//            String prompt = ui.handleCmd().trim();
//            if (prompt.isEmpty()) continue;
//
//            ParsedCommand pc = parser.parse(prompt);
//            CommandType cmd = pc.getType();
//            String details = pc.getDetails();
//            Task task;
//
//            try {
//                switch (cmd) {
//                    case BYE:
//                        storage.saveTask(list);
//                        ui.close();
//                        return;
//
//                    case LIST:
//                        ui.showList(list);
//                        break;
//
//                    case MARK:
//                        int index = Integer.parseInt(details) - 1;
//                        Task currTask = list.get(index);
//                        if (currTask.getStatus().equalsIgnoreCase("x")) {
//                            ui.showMsg("This task is already marked!");
//                            break;
//                        }
//                        currTask.markDone();
//                        storage.saveTask(list);
//                        ui.showMsg(line + "\nNice! I've marked this task as done:\n");
//                        ui.showMsg(currTask.toString());
//                        break;
//
//                    case UNMARK:
//                        int unmarkIndex = Integer.parseInt(details) - 1;
//                        Task unmarkTask = list.get(unmarkIndex);
//                        if (unmarkTask.getStatus().equalsIgnoreCase(" ")) {
//                            ui.showMsg("Task is already unmarked!!");
//                            break;
//                        }
//                        unmarkTask.unmark();
//                        storage.saveTask(list);
//                        ui.showMsg(line + "\nOk, I've marked this task as not done yet:\n");
//                        ui.showMsg(unmarkTask.toString());
//                        break;
//
//                    case DEADLINE:
//                        try {
//                            task = parser.deadlineTask(details);
//                            list.add(task);
//                            storage.saveTask(list);
//                            showAdded(task);
//                        } catch (Exception e) {
//                            ui.showError(e.getMessage());
//                        }
//                        break;
//
//                    case EVENT:
//                        try {
//                            task = parser.eventTask(details);
//                            list.add(task);
//                            storage.saveTask(list);
//                            showAdded(task);
//                        } catch (Exception e) {
//                            ui.showError(e.getMessage());
//                        }
//
//                        break;
//
//                    case TODO:
//                        try {
//                            task = parser.todoTask(details);
//                            list.add(task);
//                            storage.saveTask(list);
//                            showAdded(task);
//                        } catch (Exception e) {
//                            ui.showError(e.getMessage());
//                        }
//                        break;
//
//                    case DELETE:
//                        try {
//                            if (details.isEmpty()) {
//                                throw new Exception("Please choose task to delete!");
//                            }
//                            int taskNum = Integer.parseInt(details) - 1;
//                            list.remove(taskNum);
//                            storage.saveTask(list);
//                            showDeleted(taskNum);
//                        } catch (Exception e) {
//                            ui.showError(e.getMessage());
//                        }
//                        break;
//
//                    case FIND:
//                        List<Task> matches = list.findTask(details);
//                        ui.showMatches(matches);
//                        break;
//
//                    default:
//                        ui.showMsg("Sorry, I'm not sure what you mean!");
//                        break;
//
//                }
//            } catch (Exception e) {
//                ui.showError(e.getMessage());
//            }
//
//            }
//        }

//    public static void main(String[] args) {
//        new Yapper().run();
//    }
}
