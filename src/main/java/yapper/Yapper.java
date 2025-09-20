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


    public Yapper() {
        this.ui = new Ui();
        try {
            list = storage.loadFile();
        } catch (IOException e) {
            list = new TaskList();
        }
        assert list != null : "List should not be null after initialization";
    }


    public String showAdded(Task task) {
        assert task != null : "Task to be added should not be null";

        return line + "\nAye. Big man gone do big man things:\n" + "\t" + task.toString() +
                "\n" + String.format("Now you have %d tasks in the list", list.size()) + "\n" + line;
    }

    public String showDeleted(Task task) {
        assert task != null : "Task to be removed should not be null";

        return line + "\nAye. I done removed this:\n" + "\t" + task.toString() +
                "\n" + String.format("Now you have %d tasks in the list", list.size()) + "\n" + line;
    }

    public String showMatched(List<Task> list) {
        String res = "";
        String reply = "";
        if (list.isEmpty()) {
            return "None of that around here big man";
        } else {
            reply = "\nI got what you looking for innit:\n";
            for (int i = 0; i < list.size(); i++) {
                res = (i + 1) + "." + list.get(i);
            }
        }
        return line + reply + res + "\n" + line;
    }
    public String showUpdated(Task t) {
        assert t != null : "task should be an existing one";

        return line + "\nMake up yo mind next time T_T: \n" + t;
    }

    public String getResponse(String input) {
        try {
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
                    case UPDATE:
                        try {
                            Task updated = list.update(pc.getIndex(), pc.getDetails(), pc.getUpdatedVal());
                            storage.saveTask(list);
                            return showUpdated(updated);
                        } catch (Exception e) {
                            return e.getMessage();
                        }
                    default:
                        return "Big mans done talk bout some " + "'" + input + "'";


                }
            } catch (Exception e) {
                return e.getMessage();
            }

        } catch (Exception e) {
            return e.getMessage();
        }

    }
}