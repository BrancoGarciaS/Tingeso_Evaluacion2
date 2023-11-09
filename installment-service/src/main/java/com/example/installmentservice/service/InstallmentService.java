package com.example.installmentservice.service;

import com.example.installmentservice.entity.Installment;
import com.example.installmentservice.model.Student;
import com.example.installmentservice.repository.InstallmentRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;

    @Autowired
    RestTemplate restTemplate;

    // Para obtener todas las cuotas
    public List<Installment> getAll(){
        return installmentRepository.findAll();
    }

    // Obtener una cuota por id
    public Installment getInstallmentById(Long id_installment) {
        return installmentRepository.findById(id_installment).orElse(null);
    }

    // Obtener cuotas por rut
    public List<Installment> getInstallmentByRut(String rut) {
        interest(rut);
        return installmentRepository.findInstallmentByRut(rut);
    }

    //public Installment updateInstallment(Installment installment)

    // Para guardar una cuota
    public Installment saveData(Installment cuota){
        return installmentRepository.save(cuota);
    }

    public List<Installment> generate_Installments(Student s){
        Long id_student = s.getId();
        String rut = s.getRut();
        Integer payment_type = s.getPayment_type();
        Integer tariff = s.getTariff();
        Integer num_installments = s.getNum_installments();
        // Lista de cuotas:
        List<Installment> cuotas = new ArrayList<>();

        // divido el arancel con descuento entre el numero de cuotas
        float monto_por_cuota = (float) tariff / num_installments;
        if(payment_type == 0){ // si paga al contado
            Installment cuota = new Installment();
            cuota.setInstallmentState(1); // se asume que el pago al contado se realiza inmediatamente
            cuota.setPayment_date(LocalDate.now());
            float pago_al_contado = 1500000 / 2;  // 50% del arancel
            cuota.setPayment_amount(pago_al_contado);
            cuota.setInterest_payment_amount(pago_al_contado);
            cuota.setIdStudent(id_student); // le agrego el estudiante asociado
            cuota.setRut_installment(rut); // rut del estudiante que pagó al contado
            saveData(cuota); // guardo el pago en la base de datos
            System.out.print("\ncuota creada");
            cuotas.add(cuota); // agrego el pago a la lista de cuotas del usuario
            return cuotas;
        }

        for (int i = 0; i < num_installments; i++) {
            // acá voy creando cada cuota en el momento
            Installment cuota = new Installment();
            // Calcula la fecha de vencimiento al dìa 10 de cada mes
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue() + i + 1;
            // Ajustar el año y el mes por si se sobrepasan los 12 meses del año actual
            if(month > 12) {
                year += (month - 1) / 12;
                month = (month - 1) % 12 + 1;
            }
            // la fecha de vencimiento serìa el 10 de cada mes
            LocalDate dueDate = LocalDate.of(year, month, 10);
            // y la fecha de inicio de pago sería el 5 de cada mes
            LocalDate startDate = LocalDate.of(year, month, 5);
            cuota.setDue_date(dueDate);
            cuota.setStart_date(startDate);
            cuota.setInstallmentState(0); // 0 para pendiente (pago pendiente)
            cuota.setPayment_amount(monto_por_cuota);
            // inicialmente el monto original y de interés serán iguales
            cuota.setInterest_payment_amount(cuota.getPayment_amount());
            cuota.setIdStudent(id_student); // le agrego el estudiante asociado
            cuota.setRut_installment(rut);
            saveData(cuota); // guardo la cuota en la base de datos
            cuotas.add(cuota); // agrego la cuota a la lista de cuotas del usuario
            System.out.print("\ncuota creada");
        }
        return cuotas;

    }
    public List<Installment> generateInstallments(Map<String, Object> jsonData){
        // JSON: id_student, rut, payment_type, tariff, num_installments
        int id_student = (int) jsonData.get("id_student");
        String rut = (String) jsonData.get("rut");
        int payment_type = (int) jsonData.get("payment_type");
        int tariff = (int) jsonData.get("tariff");
        int num_installments = (int) jsonData.get("num_installments");
        // casteo id_student
        Long idS = Long.valueOf(id_student);
        // Lista de cuotas:
        List<Installment> cuotas = new ArrayList<>();
        // en caso que se han generado anteriormente cuotas con ese id_student, no
        // se van a generar mas
        if(!getInstallmentByIdStudent(idS).isEmpty() || getInstallmentByIdStudent(idS).size() != 0){
            return null;
        }
        // divido el arancel con descuento entre el numero de cuotas
        float monto_por_cuota = (float) tariff / num_installments;
        if(payment_type == 0){ // si paga al contado
            Installment cuota = new Installment();
            cuota.setInstallmentState(1); // se asume que el pago al contado se realiza inmediatamente
            cuota.setPayment_date(LocalDate.now());
            float pago_al_contado = 1500000 / 2;  // 50% del arancel
            cuota.setPayment_amount(pago_al_contado);
            cuota.setInterest_payment_amount(pago_al_contado);
            cuota.setIdStudent(idS); // le agrego el estudiante asociado
            cuota.setRut_installment(rut); // rut del estudiante que pagó al contado
            saveData(cuota); // guardo el pago en la base de datos
            cuotas.add(cuota); // agrego el pago a la lista de cuotas del usuario
            return cuotas;
        }

        for (int i = 0; i < num_installments; i++) {
            // acá voy creando cada cuota en el momento
            Installment cuota = new Installment();
            // Calcula la fecha de vencimiento al dìa 10 de cada mes
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue() + i + 1;
            // Ajustar el año y el mes por si se sobrepasan los 12 meses del año actual
            if(month > 12) {
                year += (month - 1) / 12;
                month = (month - 1) % 12 + 1;
            }
            // la fecha de vencimiento serìa el 10 de cada mes
            LocalDate dueDate = LocalDate.of(year, month, 10);
            // y la fecha de inicio de pago sería el 5 de cada mes
            LocalDate startDate = LocalDate.of(year, month, 5);
            cuota.setDue_date(dueDate);
            cuota.setStart_date(startDate);
            cuota.setInstallmentState(0); // 0 para pendiente (pago pendiente)
            cuota.setPayment_amount(monto_por_cuota);
            // inicialmente el monto original y de interés serán iguales
            cuota.setInterest_payment_amount(cuota.getPayment_amount());
            cuota.setIdStudent(idS); // le agrego el estudiante asociado
            cuota.setRut_installment(rut);
            saveData(cuota); // guardo la cuota en la base de datos
            cuotas.add(cuota); // agrego la cuota a la lista de cuotas del usuario
        }
        return cuotas;
    }

    // Para obtener cuotas por id del estudiante
    // HU4: Listar cuotas de pago de un estudiante y el estado de pago de cada cuota.
    public List<Installment> getInstallmentByIdStudent(Long idStudent) {
        // aplico los intereses necesarios
        //interest(idStudent);
        return installmentRepository.findByIdStudent(idStudent);
    }



    // Para pagar una cuota (0: fracaso, 1: exito)
    // HU5: Registrar pagos de cuotas de arancel.
    public int pay_installment(Long id_installment) {
        Installment ins = getInstallmentById(id_installment);
        if(ins == null){ // en caso que no exista una cuota con ese id
            return 0;
        }
        LocalDate actualDate = LocalDate.now();
        // Verificar si la fecha actual está entre el 5 y el 10 del mes
        if (actualDate.getDayOfMonth() >= 5 && actualDate.getDayOfMonth() <= 10) {
            // Se realiza el procesamiento de pago aquí
            // Se actualiza el estado de la cuota a "pagado"
            ins.setInstallmentState(1); // 1 para pagado
            // Establecer la fecha de pago actual
            ins.setPayment_date(LocalDate.now());
            // Guardar la cuota actualizada en la base de datos
            saveData(ins);
            return 1; // pago con éxito
        }
        return 0; // pago rechazado
    }

    // Para calcular el monto de interes con el numero de meses de atrasos
    public float interest_amount(long monthsLate){
        float interest = 0F;
        if (monthsLate == 1) {
            interest = 0.03F; // 3% de interés para 1 mes de atraso
        }
        else if (monthsLate == 2) {
            interest = 0.06F; // 6% de interés para 2 meses de atraso
        }
        else if (monthsLate == 3) {
            interest = 0.09F; // 9% de interés para 3 meses de atraso
        }
        else if (monthsLate > 3) {
            interest = 0.15F; // 15% de interés para más de 3 meses de atraso
        }
        return interest;
    }

    public void interest(String rut){
        LocalDate nowDate = LocalDate.now();
        List<Installment> i = installmentRepository.findInstallmentByRut(rut);
        for(Installment ins : i){ // por cada cuota del estudiante
            // Si la cuota está sin pagar y la fecha máxima de pago ya pasó
            if(ins.getInstallmentState() == 0 && nowDate.isAfter(ins.getDue_date())){
                // calculo los meses entre la fecha de vencimiento y la actual
                long monthsLate = ChronoUnit.MONTHS.between(ins.getDue_date(), nowDate);
                float interest = interest_amount(monthsLate);
                float original_amount = ins.getPayment_amount();
                // le aplico interes al monto original
                float interest_amount = original_amount + original_amount * interest;
                // guardo el monto con interes en la base de datos
                ins.setInterest_payment_amount(interest_amount);
                installmentRepository.save(ins);
            }
        }
    }

    // Obtener estudiante por rut
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


    // 0: error (estudiante no creado), 1: cuotas creadas, 2: ya creadas anteriormente
    public int createInstallments(String rut){
        System.out.print("rut dado " + rut);
        Student s1 = getStudentByRut(rut);
        System.out.print("\nprocesando\n");
        if(s1 == null){
            System.out.print("estudiante no encontrado");
            return 0;
        }
        List<Installment> ins = getInstallmentByRut(rut);
        // si tiene cuotas previamente creadas
        if(s1 != null && ins != null && ins.size() > 0){
            System.out.print("cuotas ya creadas");
            return 2;
        }
        else {
            // si no tiene cuotas y el estudiante existe, se crearan las cuotas
            System.out.print("cuotas sin crear");
            generate_Installments(s1);
            return 1;
        }
    }



}

