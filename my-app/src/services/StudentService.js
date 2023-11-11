import axios from 'axios';


class StudentService {
    getStudents(){
        return axios.get('http://localhost:8080/student/get');
    }

    createStudent(student){
        return axios.post('http://localhost:8080/student/post', student);
    }

    getStudentByRut(rut){
        return axios.get(`http://localhost:8080/student/get/${rut}`);
    }
    saveExams(){
        return axios.get(`http://localhost:8080/student/saveExams`);
    }

}

export default new StudentService();