package tasklist;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> task = new ArrayList<>();

    public int size() {
        return task.size();
    }

    public boolean isEmpty() {
        return task.size() <= 0;
    }

    public Task get(int i) {
        return task.get(i);
    }

    public void remove(Task t) {
        int taskNum = task.indexOf(t);
        task.remove(t);
    }

    public void add(Task t) {
        task.add(t);
    }

    public List<Task> asList() {
        return task;
    }

    public List<Task> findTask(String k) {
        List<Task> matches = new ArrayList<>();
        for (int i = 0; i < task.size(); i++) {
            Task t = task.get(i);
            if (t.getDescription().toLowerCase().contains(k.toLowerCase())) {
                matches.add(t);
            }
        }
        return matches;
    }

    public String loadList() {
        int len = this.task.size();
        String ls = "";
        for (int i = 1; i <= len; i++) {
            String curr = String.format("%d.%s\n", i, this.task.get(i - 1).toString());
            ls = ls + curr;
        }
        return ls;
    }

    public Task update(int item, String field, String updateVal) {
        if (item <= 0 || item > task.size() ) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
        if (updateVal == null || updateVal.isBlank()) {
            throw new IllegalArgumentException("Please give an appropriate input");
        }

        Task updateTask = task.get(item - 1);
        String updateField = field.toLowerCase();

        switch (updateField) {
            case "name":
                updateTask.setDescription(updateVal);
                break;
            case "by":
                if (updateTask instanceof Deadline) {
                    ((Deadline) updateTask).setDeadline(updateVal);
                } else {
                    throw new IllegalArgumentException("This task is not a Deadline Task!");
                }
                break;
            case "from":
                if (updateTask instanceof Event) {
                    ((Event) updateTask).setStart(updateVal);
                } else {
                    throw new IllegalArgumentException("This task is not an Event Task!");
                }
                break;
            case "to":
                if (updateTask instanceof Event) {
                    ((Event) updateTask).setEnd(updateVal);
                } else {
                    throw new IllegalArgumentException("This task is not an Event Task!");
                }
                break;
            default:
                throw new IllegalArgumentException("Please give appropriate commands: /name, /by, /from, /to, and inputs!");
        }
        return updateTask;
    }


    public String statement() {
        String line = "----------------------------";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < task.size(); i++) {
            sb.append(String.format("%d.%s\n", i+1, task.get(i)));
        }
        return line + "\n" + "Here are the tasks in your list:\n" + sb + line;
    }


}
