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

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student getStudentByRut(String rut) {
        return studentRepository.findByRut(rut).orElse(null);
    }

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
        generateInstallmentByStudent(studentNew);
        return studentNew;
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

    /*
    public List<Installment> getInstallments(Long studentId) {
        String url = "http://localhost:8002/installment/getByStudent/";
        List<Installment> installments = restTemplate.getForObject(url + studentId, List.class);
        return installments;
    }*/

    public List<Installment> getInstallmentsByRut(String rut) {
        String url = "http://localhost:8002/installment/get/" + rut;

        ParameterizedTypeReference<List<Installment>> responseType = new ParameterizedTypeReference<List<Installment>>() {};
        ResponseEntity<List<Installment>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<Installment> installments = responseEntity.getBody();
            System.out.println("Cuotas obtenidas con éxito");
            return installments;
        }
        return null;
    }

    public List<Exam> getExams(Long studentId) {
        String rut = "";
        Student s1 = getStudentById(studentId);
        if(s1 != null){
            rut = s1.getRut();
        }
        String url = "http://localhost:8003/exam/get/";
        List<Exam> exams = restTemplate.getForObject(url + rut, List.class);
        return exams;
    }


    public Installment saveInstallment(Long studentId, Installment installment) {
        installment.setIdStudent(studentId);
        HttpEntity<Installment> request = new HttpEntity<Installment>(installment);
        String url = "http://localhost:8002/installment/post";
        Installment new_ins = restTemplate.postForObject(url, request, Installment.class);
        return new_ins;
    }


    public List<Installment> generateInstallmentByStudent(Student student){
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("id_student", student.getId()); // Reemplaza esto por la forma en que obtienes el ID del estudiante
        jsonData.put("rut", student.getRut());
        jsonData.put("payment_type", student.getPayment_type());
        jsonData.put("tariff", student.getTariff());
        jsonData.put("num_installments", student.getNum_installments());
        // Define el encabezado de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Creo el cuerpo de la solicitud con el objeto jsonData
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonData, headers);

        String url = "http://localhost:8002/installment/generateInstallments";
        List<Installment> installments = restTemplate.postForObject(url, requestEntity, List.class);
        return installments;
    }

    // Para obtener la media de examenes
    public List<Map<String, Object>> getAVG_exams() {
        String url = "http://localhost:8003/exam/getMeans";
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
        return results;
    }



    /*
    public List<Installment> generateInstallmentsByStudent(Student student) {
        String url = "http://localhost:8002/installment/generate_installments";

        // Crea un objeto de solicitud de multipart/form-data
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("id_student", student.getId());
        request.add("rut", student.getRut());
        request.add("payment_type", student.getPayment_type());
        request.add("tariff", student.getTariff());
        request.add("num_installments", student.getNum_installments());

        // Define el encabezado de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Creo el cuerpo de la solicitud con los parámetros
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(request, headers);

        List<Installment> installments = restTemplate.postForObject(url, requestEntity, List.class);
        return installments;
    }

     */


    /*
    public Pet savePet(int studentId, Pet pet) {
        pet.setStudentId(studentId);
        HttpEntity<Pet> request = new HttpEntity<Pet>(pet);
        Pet petNew = restTemplate.postForObject("http://localhost:8003/pet", request, Pet.class);
        return petNew;
    }

     */

}
