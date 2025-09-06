package tasklist;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> task = new ArrayList<>();

    public int size() {
        return task.size();
    }

    public Task get(int i) {
        return task.get(i);
    }

    public void remove(int i) {
        task.remove(i);
    }

    public void add(Task t) {
        task.add(t);
    }

    public List<Task> asList() {
        return task;
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
