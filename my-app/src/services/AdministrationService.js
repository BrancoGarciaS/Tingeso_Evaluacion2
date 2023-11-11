import axios from 'axios';


class AdministrationService {
    getAllExams(){
        return axios.get(`http://localhost:8080/exam/get`);
    }
    getExamsByRut(rut){
        return axios.get(`http://localhost:8080/exam/get/${rut}`);
    }

    createReport(rut){
        return axios.get(`http://localhost:8080/exam/report/${rut}`);
    }

    getMeans(){
        return axios.get(`http://localhost:8080/exam/getMeans`);
    }
    loadExcel(formData){
        return axios.post(`http://localhost:8080/exam/load_excel`, formData);
    }
}

export default new AdministrationService();