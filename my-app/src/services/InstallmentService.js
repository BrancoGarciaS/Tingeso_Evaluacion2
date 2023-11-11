import axios from 'axios';


class InstallmentService {
    getAllInstallments(){
        return axios.get(`http://localhost:8080/installment/get`);
    }
    getInstallmentsByRut(rut){
        return axios.get(`http://localhost:8080/installment/get/${rut}`);
    }

    createInstallmentsByRut(rut){
        return axios.get(`http://localhost:8080/installment/generate_Installments/${rut}`);
    }

    payInstallment(id){
        return axios.put(`http://localhost:8080/installment/pay/${id}`);
    }
}

export default new InstallmentService();