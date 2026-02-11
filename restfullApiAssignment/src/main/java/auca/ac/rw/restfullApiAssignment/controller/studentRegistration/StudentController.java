package auca.ac.rw.restfullApiAssignment.controller.studentRegistration;

import auca.ac.rw.restfullApiAssignment.modal.studentRegistration.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing student registration
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    // In-memory list to store students
    private List<Student> students = new ArrayList<>();
    private Long nextId = 6L;

    // Initialize with sample data
    public StudentController() {
        students.add(new Student(1L, "John", "Doe", "john.doe@example.com", "Computer Science", 3.8));
        students.add(new Student(2L, "Jane", "Smith", "jane.smith@example.com", "Computer Science", 3.9));
        students.add(new Student(3L, "Michael", "Johnson", "michael.j@example.com", "Business Administration", 3.2));
        students.add(new Student(4L, "Emily", "Davis", "emily.davis@example.com", "Engineering", 3.7));
        students.add(new Student(5L, "David", "Wilson", "david.wilson@example.com", "Computer Science", 3.5));
    }

    /**
     * GET /api/students - Get all students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * GET /api/students/{studentId} - Get student by ID
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
        
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /api/students/major/{major} - Get all students by major
     */
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getMajor().equalsIgnoreCase(major)) {
                result.add(student);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET /api/students/filter?gpa={minGpa} - Filter students with GPA >= minimum
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGpa(@RequestParam Double gpa) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getGpa() >= gpa) {
                result.add(student);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST /api/students - Register a new student
     */
    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        student.setStudentId(nextId++);
        students.add(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    /**
     * PUT /api/students/{studentId} - Update student information
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                updatedStudent.setStudentId(studentId);
                students.set(i, updatedStudent);
                return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}