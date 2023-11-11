import React, { Component } from 'react';
import StudentService from '../services/StudentService';
import 'bootstrap/dist/css/bootstrap.css';

class StudentListComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      students: [],
      selectedStudent: null,
    };
  }

  componentDidMount() {
    this.loadStudents();
  }

  loadStudents() {
    StudentService.getStudents()
      .then((response) => {
        this.setState({ students: response.data });
      })
      .catch((error) => {
        console.error('Error loading students:', error);
      });
  }

  showDetails = (rut) => {
    StudentService.getStudentByRut(rut)
      .then((response) => {
        this.setState({ selectedStudent: response.data });
      })
      .catch((error) => {
        console.error('Error loading student details:', error);
      });
  };

  renderStudentDetails() {
    const student = this.state.selectedStudent;

    if (!student) {
      return null;
    }

    return (
      <div className="student-details">
        <h3>Detalles del Estudiante: </h3>
        <div class="table-responsive-sm" style={{border:"5px solid #FF3D47"}}>
        <table class="table table-hover">
          <tbody>
            <tr class = "table-danger">
              <td>RUT:</td>
              <td>{student.rut}</td>
            </tr>
            <tr class = "table-warning">
              <td>Nombre:</td>
              <td>{student.name}</td>
            </tr>
            <tr class = "table-danger">
              <td>Apellido:</td>
              <td>{student.last_name}</td>
            </tr>
            <tr class = "table-warning">
              <td>Correo Electrónico:</td>
              <td>{student.email}</td>
            </tr>
            <tr class = "table-danger">
              <td>Edad:</td>
              <td>{student.age}</td>
            </tr>
            <tr class = "table-warning">
              <td>Escuela:</td>
              <td>{student.school_name}</td>
            </tr>
            <tr class = "table-danger">
              <td>Exámenes rendidos:</td>
              <td>{student.num_exams}</td>
            </tr>
            <tr class = "table-warning">
              <td>Puntaje de exámenes:</td>
              <td>{student.score}</td>
            </tr>
            <tr class = "table-danger">
              <td>Número de cuotas:</td>
              <td>{student.num_installments}</td>
            </tr>
          </tbody>
        </table>
        </div>
      </div>
    );
  }

  render() {
    return (
      <div className="student-list-container">
        <br></br>
        <div className='st_details_container'>
        <div className="titulo"> 
          <h2 className="text-center">Lista de Estudiantes</h2>
          <table class="table table-hover" border = "3">
            <thead>
              <tr class = "cabecera-tabla table-danger" style={{color: "red"}}>
                <th>RUT</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Correo Electrónico</th>
                <th>Número de cuotas</th>
                <th>Acción</th>
              </tr>
            </thead>
            <tbody>
              {this.state.students.map((student) => (
                <tr key={student.rut}>
                  <td>{student.rut}</td>
                  <td>{student.name}</td>
                  <td>{student.last_name}</td>
                  <td>{student.email}</td>
                  <td>{student.num_installments}</td>
                  <td>
                    <button
                      className="btn btn-light ml-2 main-button2"
                      onClick={() => this.showDetails(student.rut)}
                    >
                      Detalles
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {this.renderStudentDetails()}
        </div>
      
      </div>
    );
  }
}

export default StudentListComponent;