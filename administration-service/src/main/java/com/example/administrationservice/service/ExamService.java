package com.example.administrationservice.service;
import com.example.administrationservice.entity.Exam;
import com.example.administrationservice.model.Installment;
import com.example.administrationservice.model.Student;
import com.example.administrationservice.repository.ExamRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;

    @Autowired
    RestTemplate restTemplate;

    // Para obtener todos los exámenes
    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    // Para guardar una examen
    public Exam saveData(Exam exam) {
        exam.setLoad_date(LocalDate.now());
        return examRepository.save(exam);
    }

    // Para borrar todos los exámenes
    public void deleteAll(){
        examRepository.deleteAll();
    }

    // Para calcular la media de examenes por rut
    public List<Map<String, Object>> getMeanExams() {
        List<Object[]> means = examRepository.groupMean();
        List<Map<String, Object>> mean_exams = new ArrayList<>();

        for (Object[] obj : means) {
            Map<String, Object> examAverage = new HashMap<>();
            examAverage.put("rut", obj[0]);
            examAverage.put("avg", obj[1]);
            examAverage.put("n_exams", obj[2]);
            mean_exams.add(examAverage);
        }
        // para ordenar los promedios de exámenes
        mean_exams.sort((a, b) -> Double.compare((Double) b.get("avg"), (Double) a.get("avg")));
        return mean_exams;
    }

    // Para obtener exámenes por rut
    public List<Exam> getByRut(String rut){
        return examRepository.findByStudentRut(rut);
    }

    // Para guardar un examen en la base de datos
    public Exam saveDataDB(String rut, String dateString, String score){
        Exam exam = new Exam();
        exam.setRut(rut);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // formato de la fecha
        LocalDate date = LocalDate.parse(dateString, formatter);
        exam.setExam_date(date);
        exam.setExam_score(Float.parseFloat(score));
        exam.setLoad_date(LocalDate.now());
        return examRepository.save(exam);
    }

    // Para borrar los exámenes (se usa cuando se calcula el promedio)
    public void deleteAllExams(){
        examRepository.deleteAll();
    }

    // Leer el archivo csv
    @Generated
    public void readCsv(String filename){
        String texto = ""; // para almacenar el contenido del texto
        BufferedReader bf = null; // Objeto para leer
        try{
            bf = new BufferedReader(new FileReader(filename)); // abro el archivo csv para lectura
            String temp = ""; // para acumular las líneas leidas
            String bfRead; // para almacenar cada línea de archivo
            int count = 1; // desde que fila va a leer (omite la primera línea del archivo csv que
            // tiene los nombres de las variables)
            while((bfRead = bf.readLine()) != null){ // mientras hayan lineas por leer
                if (count == 1){ // si el contador es 1
                    count = 0; // omite la primera fila del csv
                }
                else{ // sino, significa que se están leyendo las otras líneas
                    // guardo los datos de cada celda de la fila en la base de datos de examenes
                    saveDataDB(bfRead.split(";")[0],
                            bfRead.split(";")[1],
                            bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead; // acumulo la linea leida
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close(); // me encargo que se cierre BufferedReader
                }catch(IOException e){

                }
            }
        }
    }

    // Para guardar archivo csv
    public String saveFile(MultipartFile file){
        // obtengo el nombre del archivo
        String filename = file.getOriginalFilename();
        if(filename != null){ // si el nombre no es nulo y el archivo no está vacío
            if(!file.isEmpty()){
                try{
                    // Obtengo los bytes del archivo
                    byte [] bytes = file.getBytes();
                    // creo un archivo path del archivo
                    Path path  = Paths.get(file.getOriginalFilename());
                    // escribo los bytes en el archivo correspondiente
                    Files.write(path, bytes);
                    System.out.printf("archivo guardado");
                }
                catch (IOException e){ // en caso de error
                    System.out.printf("error");
                }
            }
            return "Archivo guardado con exito";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    // Para obtener estudiantes por rut
    public Student getStudentByRut(String rut) {
        String url = "http://student-service/student/get2/" + rut;

        ParameterizedTypeReference<Student> responseType = new ParameterizedTypeReference<Student>() {};
        ResponseEntity<Student> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Student s = responseEntity.getBody();
            if (s != null) {
                System.out.println("Estudiante encontrado con éxito");
            }
            return s;
        }
        else{
            return null;
        }
    }

    // Para generar cuotas por rut
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

    // Para ver si una cuota está atrasada
    public boolean isLate(Installment inst){
        // en el caso que se haya pagado la cuota
        LocalDate due_date = inst.getDue_date();
        if(inst.getInstallmentState() == 1){
            LocalDate pay_date = inst.getPayment_date();
            // si la fecha de vencimiento de la cuota
            // es anterior a la fecha de pago, está atrasada
            if(due_date.isBefore(pay_date)){
                return true;
            }
        }
        // en caso que la cuota aun no haya sido pagada
        // y actualmente se ha sobrepasado la fecha de vencimiento
        else{
            // si la cuota tiene una fecha de vencimiento
            // que es anterior a la fecha actual
            if(due_date.isBefore(LocalDate.now())){
                return true;
            }
        }
        return false;
    }

    // Para crear reporte
    public Map<String, Object> createReport(String rut){
        // actualizar cuotas
        Student student_rut = getStudentByRut(rut); // obtener estudiante por rut
        Map<String, Object> report = new HashMap<>();
        if(student_rut != null){ // si existe el estudiante en la base de datos
            // creo el reporte
            report.put("rut", student_rut.getRut());
            report.put("name_student", student_rut.getName());
            report.put("last_name", student_rut.getLast_name());
            report.put("num_exams", student_rut.getNum_exams());
            report.put("mean_score", student_rut.getScore());
            report.put("original_tariff", student_rut.getTariff());
            report.put("payment_type", student_rut.getPayment_type());
            report.put("num_installments", student_rut.getNum_installments());
            report.put("tuition", student_rut.getTuition());

            // Obtengo las cuotas de esa persona
            List<Installment> c = getInstallmentsByRut(rut);
            System.out.println("numero de cuotas: " + c.size());

            if(student_rut.getPayment_type() == 0){
                // si pagó al contado
                report.put("interest_tariff", student_rut.getTariff());
                report.put("num_installments_paid", 1);
                report.put("late_installments", 0); // tiene 0 cuotas atrasadas porque pagó al contado
                if(c.size() > 0){ // si tiene más de una cuota
                    report.put("areGenerated", 1);
                    LocalDate d = c.get(0).getPayment_date();
                    report.put("last_payment", d); // la ultima fecha de pago y es unica
                }
                else{
                    // en caso que no se han generado las cuotas
                    report.put("areGenerated", 0);
                }
                report.put("num_installments_paid", 1); // se ha pagado una cuota
                report.put("tariff_paid", 750000); // tarifa pagada
                report.put("tariff_to_pay", 0); // tarifa a pagar
                report.put("late_installments", 0); // 0 cuotas atrasadas
                report.put("total_tariff", 750000);
                return report;
            }

            int count_paid = 0; // numero de cuotas pagadas
            float tariff_paid_s = 0; // monto total pagado
            float tariff_to_pay = 0; // monto a pagar
            float interes_tariff = 0; // arancel a pagar con intereses
            Integer later = 0; // cuotas atrasadas
            if(c.size() == 0){ // en caso que no tenga las cuotas generadas
                report.put("areGenerated", 0);
            }
            if(c.size() > 0){
                report.put("areGenerated", 1);
                LocalDate last_payment = c.get(0).getPayment_date();
                for(Installment installment_s : c){
                    // si la cuota está pagada se aumenta el contador
                    if(installment_s.getInstallmentState() == 1){
                        count_paid++;
                        tariff_paid_s += installment_s.getPayment_amount();
                        // si aun no se ha encontrado una fecha de pago
                        // o si la fecha de pago de la cuota es más reciente que la fecha
                        // más reciente encontrada
                        if(last_payment != null && installment_s.getPayment_date() != null){
                            if(installment_s.getPayment_date().isAfter(last_payment)){
                                last_payment = installment_s.getPayment_date();
                            }
                        }
                        // en caso que aun no se ha encontrado una fecha de pago
                        else if(last_payment == null && installment_s.getPayment_date() != null){
                            last_payment = installment_s.getPayment_date();
                        }
                    }
                    else{ // si no está pagada
                        tariff_to_pay += installment_s.getPayment_amount();
                    }
                    interes_tariff += installment_s.getPayment_amount();
                    if(isLate(installment_s)){
                        // si la cuota está atrasada se cuenta en atrasos
                        later++;
                    }
                }
                if (last_payment != null) { // Verifica si last_payment no es nulo
                    report.put("last_payment", last_payment);
                }
            }

            report.put("interest_tariff", interes_tariff);
            report.put("num_installments_paid", count_paid);
            report.put("tariff_paid", tariff_paid_s); // tarifa pagada
            report.put("tariff_to_pay", tariff_to_pay); // tarifa a pagar
            report.put("late_installments", later); // cuotas atrasadas
            float total = tariff_paid_s + tariff_to_pay;
            report.put("total_tariff", total);
            return report;
        }
        return report;
    }

}
