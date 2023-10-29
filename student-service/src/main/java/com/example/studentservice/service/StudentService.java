package com.example.studentservice.service;

import com.example.studentservice.entity.Student;
import com.example.studentservice.model.Exam;
import com.example.studentservice.model.Installment;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveData(Student student) {
        Student studentNew = studentRepository.save(student);
        return studentNew;
    }

    public List<Installment> getInstallments(Long studentId) {
        String url = "http://localhost:8002/installment/getByStudent/";
        List<Installment> installments = restTemplate.getForObject(url + studentId, List.class);
        return installments;
    }

    public List<Exam> getExams(Long studentId) {
        String rut = "";
        Student s1 = getStudentById(studentId);
        if(s1 != null){
            rut = s1.getRut();
        }
        String url = "http://localhost:8003/exam/getByRut/";
        List<Exam> exams = restTemplate.getForObject(url + rut, List.class);
        return exams;
    }

    /*
    public Book saveBook(int studentId, Book book) {
        book.setStudentId(studentId);
        HttpEntity<Book> request = new HttpEntity<Book>(book);
        Book bookNew = restTemplate.postForObject("http://localhost:8002/book", request, Book.class);
        return bookNew;
    }

    public Pet savePet(int studentId, Pet pet) {
        pet.setStudentId(studentId);
        HttpEntity<Pet> request = new HttpEntity<Pet>(pet);
        Pet petNew = restTemplate.postForObject("http://localhost:8003/pet", request, Pet.class);
        return petNew;
    }

     */
}
