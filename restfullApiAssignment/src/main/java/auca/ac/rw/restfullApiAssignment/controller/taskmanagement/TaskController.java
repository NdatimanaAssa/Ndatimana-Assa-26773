package auca.ac.rw.restfullApiAssignment.controller.taskmanagement;

import auca.ac.rw.restfullApiAssignment.modal.taskmanagement.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing tasks
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // In-memory list to store tasks
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 6L;

    // Initialize with sample data
    public TaskController() {
        tasks.add(new Task(1L, "Complete Spring Boot Assignment", "Finish all 5 questions for the RESTful API assignment", false, "HIGH", "2026-02-15"));
        tasks.add(new Task(2L, "Review Java Documentation", "Read and understand Java 17 new features", false, "MEDIUM", "2026-02-20"));
        tasks.add(new Task(3L, "Setup Development Environment", "Install IntelliJ IDEA and configure Maven", true, "HIGH", "2026-02-05"));
        tasks.add(new Task(4L, "Learn Docker Basics", "Complete Docker tutorial and create first container", false, "LOW", "2026-02-28"));
        tasks.add(new Task(5L, "Practice Algorithm Problems", "Solve 5 LeetCode problems on arrays", false, "MEDIUM", "2026-02-18"));
    }

    /**
     * GET /api/tasks - Get all tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * GET /api/tasks/{taskId} - Get task by ID
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();
        
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/tasks/status?completed={true/false} - Get tasks by completion status
     */
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> result = new ArrayList<>();
        
        for (Task task : tasks) {
            if (task.isCompleted() == completed) {
                result.add(task);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/tasks/priority/{priority} - Get tasks by priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> result = new ArrayList<>();
        
        for (Task task : tasks) {
            if (task.getPriority().equalsIgnoreCase(priority)) {
                result.add(task);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST /api/tasks - Create new task
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setTaskId(nextId++);
        tasks.add(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    /**
     * PUT /api/tasks/{taskId} - Update task
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(taskId)) {
                updatedTask.setTaskId(taskId);
                tasks.set(i, updatedTask);
                return new ResponseEntity<>(updatedTask, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * PATCH /api/tasks/{taskId}/complete - Mark task as completed
     */
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                task.setCompleted(true);
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /api/tasks/{taskId} - Delete task
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(task -> task.getTaskId().equals(taskId));
        
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}