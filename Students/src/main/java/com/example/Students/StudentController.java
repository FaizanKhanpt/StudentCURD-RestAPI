package com.example.Students;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository StudentRepository;

    @PostMapping("/Students")
    public String createNewStudent(@RequestBody Student Student) {
        StudentRepository.save(Student);
        return "Student Created in database";
    }

    @GetMapping("/Students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> stuList = new ArrayList<>();
        StudentRepository.findAll().forEach(stuList::add);
        return new ResponseEntity<List<Student>>(stuList, HttpStatus.OK);
    }

    @GetMapping("/Students/{rollno}")
    public ResponseEntity<Student> getStudentById(@PathVariable int rollno) {
        Optional<Student> stu = StudentRepository.findById((long) rollno);
        if (stu.isPresent()) {
            return new ResponseEntity<Student>(stu.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/Students/{rollno}")
    public String updateStudentById(@PathVariable int rollno, @RequestBody Student Student) {
        Optional<Student> stu = StudentRepository.findById((long) rollno);
        if (stu.isPresent()) {
            Student existstu = stu.get();
            existstu.setAge(Student.getAge());
            existstu.setSubject(Student.getSubject());
            existstu.setName(Student.getName());
            StudentRepository.save(existstu);
            return "Student Details against Id " + rollno + " updated";
        } else {
            return "Student Details does not exist for rollno " + rollno;
        }
    }

    @DeleteMapping("/Students/{rollno}")
    public String deleteStudentByrollno(@PathVariable int rollno) {
        StudentRepository.deleteById((long) rollno);
        return "Student Deleted Successfully";
    }

    @DeleteMapping("/Students")
    public String deleteAllStudent() {
        StudentRepository.deleteAll();
        return "Student deleted Successfully..";
    }

    @GetMapping("/Students/subj")
    public ResponseEntity<Student> getStudentBysubject(@RequestParam("subject") String subject) {
        Student stu = StudentRepository.findBySubject(subject);
        return new ResponseEntity<Student>(stu, HttpStatus.FOUND);
    }

    @GetMapping("/Student/StudentGreaterThan")
    public ResponseEntity<List<Student>> getStudentGreaterThan(@RequestParam("age") int age){
        Optional<List<Student>> stuList = StudentRepository.findByAgeGreaterThan(age);
        return new ResponseEntity<List<Student>>(stuList.get(), HttpStatus.FOUND);
    }
}