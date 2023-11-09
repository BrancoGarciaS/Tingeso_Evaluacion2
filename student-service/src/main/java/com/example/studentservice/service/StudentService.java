package com.example.studentservice.service;

import com.example.studentservice.entity.Student;
import com.example.studentservice.model.Exam;
import com.example.studentservice.model.Installment;

import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RestTemplate restTemplate;

    // Para borrar todos los estudiantes
    public List<Student> getAll() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    // Para obtener estudiante por id
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Para obtener estudiante por rut
    public Student getStudentByRut(String rut) {
        return studentRepository.findByRut(rut).orElse(null);
    }

    // Para guardar un estudiante en la base de datos
    public Student saveData(Student student) {
        LocalDate now = LocalDate.now(); // fecha de ahora
        Period period = Period.between(student.getBirthdate(), now); // obtengo periodo entre fecha de nacimiento y hoy
        int age = period.getYears();
        student.setAge(age);
        student.setNum_exams(0L); // inicialmente habrá rendido 0 examenes
        if(student.getNum_installments() > 1){ // si tiene mas de una cuota
            student.setPayment_type(1); // pago en cuotas
        }
        if(student.getNum_installments() < 2){
            student.setPayment_type(0); // pago al contado
        }
        // tipo de pago (iniciara por defecto como contado)
        if(student.getSchool_type() == 2 && student.getNum_installments() > 7){
            student.setNum_installments(7);
        }
        else if(student.getSchool_type() == 3 && student.getNum_installments() > 4){
            student.setNum_installments(4);
        }
        else{
            // iniciará con 0 cuotas pagas en total
            student.setNum_installments(student.getNum_installments());
        }
        student.setTuition(70000); // costo de la matrícula
        student.setScore(0); // promedio de examenes
        if(student.getPayment_type() == 0){
            // si paga al contado se descuenta el 50% del arancel
            student.setTariff(750000);
        }
        else if(student.getPayment_type() == 1){
            // si paga en cuotas se aplica descuento por tipo de escuela y año de egreso
            Integer studentTariff = discount_tariff(student); // arancel con descuento
            student.setTariff(studentTariff); // valor del arancel con descuento
        }

        Student studentNew = studentRepository.save(student);
        //generateInstallmentByStudent(studentNew);
        return studentNew;
    }

    public void saveData_2(Student student) {
        studentRepository.save(student);
    }

    // Para calcular descuento por tipo de escuela
    public Integer schoolTypeDiscount(Student student){
        Integer tariff = 1500000;  // arancel total sin descuento
        Integer discount = 0; // descuento inicial
        if(student.getSchool_type() == 1){ // si su colegio es tipo municipal
            discount = (int) (tariff * 0.2); // hay descuento del 20%
        }
        else if(student.getSchool_type() == 2){ // si es su colegio es tipo subvencionado
            discount = (int) (tariff * 0.1); // hay descuento del 10%
        }
        // en caso de que el colegio sea privado (o sea 3) el descuento se conserva en 0
        return discount;
    }

    // Para calcular descuento por año de ingreso
    public Integer seniorYearDiscount(Student student){
        Integer tariff = 1500000;  // arancel total sin descuentos
        Integer discount = 0; // descuento inicial
        Integer seniorYear = 2023 - student.getSenior_year();
        if(seniorYear < 1){
            discount = (int) (tariff * 0.15); // hay descuento del 15%
        }
        // entre 1 o 2 años de egreso
        else if(seniorYear < 3){
            discount = (int) (tariff * 0.08); // hay descuento del 8%
        }
        //  entre los 3 años a 4 años de egreso
        else if(seniorYear < 5){
            discount = (int) (tariff * 0.04); // hay descuento del 4%
        }
        // a los 5 años o más no hay descuento (es igual a 0)
        return discount;
    }

    // Para aplicar descuento al arancel de 1.500.000
    public Integer discount_tariff(Student student){
        Integer tariff = 1500000; // arancel total sin descuento
        // aplico cada uno de los descuentos
        tariff = tariff - schoolTypeDiscount(student);
        tariff = tariff - seniorYearDiscount(student);
        return tariff;
    }

    // Para obtener las cuotas de un estudiante por rut
    public List<Installment> getInstallmentsByRut(String rut) {
        String url = "http://installment-service/installment/get/" + rut;

        ParameterizedTypeReference<List<Installment>> responseType = new ParameterizedTypeReference<List<Installment>>() {};
        ResponseEntity<List<Installment>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<Installment> installments = responseEntity.getBody();
            System.out.println("Cuotas obtenidas con éxito");
            return installments;
        }
        return null;
    }

    // Para obtener los examenes de un estudiante
    public List<Exam> getExams(Long studentId) {
        String rut = "";
        Student s1 = getStudentById(studentId);
        if(s1 != null){
            rut = s1.getRut();
        }
        String url = "http://administration-service/exam/get/";
        List<Exam> exams = restTemplate.getForObject(url + rut, List.class);
        return exams;
    }

    // Para guardar una cuota
    public Installment saveInstallment(Long studentId, Installment installment) {
        installment.setIdStudent(studentId);
        HttpEntity<Installment> request = new HttpEntity<Installment>(installment);
        String url = "http://installment-service/installment/post";
        Installment new_ins = restTemplate.postForObject(url, request, Installment.class);
        return new_ins;
    }

    // Para generar las cuotas de un estudiante
    public List<Installment> generateInstallmentByStudent(Student student){
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("id_student", student.getId());
        jsonData.put("rut", student.getRut());
        jsonData.put("payment_type", student.getPayment_type());
        jsonData.put("tariff", student.getTariff());
        jsonData.put("num_installments", student.getNum_installments());
        // Define el encabezado de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Creo el cuerpo de la solicitud con el objeto jsonData
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonData, headers);

        String url = "http://installment-service/installment/generateInstallments";
        List<Installment> installments = restTemplate.postForObject(url, requestEntity, List.class);
        return installments;
    }

    // Para obtener la media de examenes
    public List<Map<String, Object>> getAVG_exams() {
        String url = "http://administration-service/exam/getMeans";
        List<Map<String, Object>> exams = restTemplate.getForObject(url, List.class);
        return exams;
    }

    // Para aplicar descuento por puntaje
    public float scoreDiscount(Installment installment, float exam_score){
        float m = installment.getPayment_amount();
        if(exam_score <= 1000 && exam_score >= 950){
            // como el descuento es del 10% se retorna el 90% del monto de la cuota
            return (float) (m * 0.9);
        }
        else if(exam_score < 950 && exam_score >= 900){
            // como el descuento es del 5% se retorna el 95% del monto de la cuota
            return (float) (m * 0.95);
        }
        else if(exam_score < 900 && exam_score >= 850){
            // como el descuento es del 2% se retorna el 98% del monto de la cuota
            return (float) (m * 0.98);
        }
        return m;
    }


    // Para guardar la media en la base de datos
    public List<Map<String, Object>> saveMean(){
        // agrupo por rut y calculo media
        List<Map<String, Object>> results = getAVG_exams();
        for (Map<String, Object> result : results) {
            // por cada fila de la consulta
            String rut = (String) result.get("rut");
            Double mean = (Double) result.get("avg");
            Integer num_exams = (Integer) result.get("n_exams");
            // obtengo el estudiante por el rut
            Student s = getStudentByRut(rut);
            if(s != null){ // si el estudiante está en la base de datos
                System.out.print("Rut encontrado:" + rut);
                float meanFloat = mean.floatValue(); // como mean es double lo convierte a float
                s.setScore(meanFloat); // actualizo el promedio del estudiante
                Long n = num_exams + s.getNum_exams(); // sumo el número de examenes con el anterior
                s.setNum_exams(n);
                studentRepository.save(s);
                List<Installment> cuotas = getInstallmentsByRut(rut);
                if(s.getPayment_type() == 1 && !(cuotas.isEmpty())){
                    // Para estudiantes con cuotas
                    for(Installment cuota : cuotas){
                        // Por cada cuota pendiente
                        if(cuota.getInstallmentState() == 0){ // si la cuota está pendiente
                            // aplico descuento en base al promedio del estudiante
                            float m = scoreDiscount(cuota, meanFloat);
                            System.out.println("descuento "+ m);
                            // y modifico ese descuento en la cuota
                            cuota.setPayment_amount(m);
                            if(cuota.getPayment_amount() < cuota.getInterest_payment_amount()){
                                cuota.setInterest_payment_amount(cuota.getPayment_amount());
                            }
                            saveInstallment(cuota.getId_installment(), cuota);
                        }
                    }
                }
            }
            else{
                System.out.print("Rut no encontrado:" + rut);
            }
        }
        deleteAllExams(); // borro todos los exámenes
        return results;
    }

    // Para borrar un estudiante
    public void deleteStudent(Long id){
        if(getStudentById(id) != null){
            studentRepository.deleteById(id);
        }
    }

    // Para borrar todos los exámenes
    public void deleteAllExams() {
        String url = "http://administration-service/exam/delete";

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // La solicitud DELETE fue exitosa
            System.out.print("Exámenes borrados con éxito");
        } else {
            // Maneja el caso en el que la solicitud DELETE falla
            System.out.print("Error");
        }
    }



}
