package ui;

import java.util.Scanner;
import java.util.List;
import tasklist.*;

public class Ui {
    private final String line = "-------------------------";
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public void greet() {
        System.out.println("Hello! I'm Yapper! \nWhat can I do for you?");
    }

    public void close() {
        this.showMsg("Bye! See you again!");
        sc.close();
    }

    public String handleCmd() {
        return sc.nextLine();
    }

    public void showList(TaskList list) {
        int len = list.size();
        String ls = "";
        for (int i = 1; i <= len; i++) {
            String curr = String.format("%d.%s\n", i, list.get(i - 1).toString());
            ls = ls + curr;
        }
        this.showMsg(line + "\n" + "Here are the tasks in your list:\n");
        this.showMsg(ls + line);
    }

    public void showMatches(List<Task> list ) {
        showMsg(line);
        if (list.isEmpty()) {
            showMsg("No matches in your list of tasks!");
        } else {
            showMsg("Here are the items that match your description:\n");
            for (int i = 0; i < list.size(); i++) {
                showMsg((i+ 1) +"." + list.get(i));
            }
        }
        showMsg(line);
    }


    public void showError(String msg) {
        System.out.println("Error: " + msg);
    }

    public void showMsg(String msg) {
        System.out.println(msg);
    }

}
