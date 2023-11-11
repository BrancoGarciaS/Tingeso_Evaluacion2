import React, { Component } from 'react';
import StudentService from '../services/StudentService';

class SearchStudentComponent extends Component {
  
  constructor(props) {
    super(props);

    this.state = {
      rut: '',
      student: null,
      showError: false, // Agrego una variable de estado showError
    };
  }

  handleInputChange = (event) => {
    this.setState({ rut: event.target.value });
  };

  searchStudent = () => {
    const { rut } = this.state;

    if (rut) {
      StudentService.getStudentByRut(rut)
        .then((res) => {
          this.setState({ student: res.data, showError: false });
        })
        .catch((error) => {
          console.error('Error:', error);
          this.setState({ student: null, showError: true }); // Establezco showError en true
        });
    }
  };

  render() {
    const { student, showError } = this.state;
    // Mapeo de tipos de escuela
    const schoolTypeMap = {
      1: "Municipal",
      2: "Subvencionado",
      3: "Privado",
    };
    return (
      <div>
        <h2 className="titulo text-center">Buscar Estudiante</h2>
        <div className="row">
          <div className='container_subida'>
            <label className= "entrada" htmlFor="rut">Ingrese el RUT del estudiante para buscar su información: </label>
            <br></br>
            <br></br>
            <input
              type="text"
              className="form-control"
              style={{ width: '600px' }}
              placeholder="Ingrese el RUT del estudiante"
              value={this.state.rut}
              onChange={this.handleInputChange}
            />
            <br></br>
            <button className="btn btn-light ml-2 main-button2" onClick={this.searchStudent}>Buscar Estudiante</button>
          </div>
        </div>
        {showError && !student && ( // Mostrar el mensaje solo si showError es true y student es null
          <p className="text-center">Estudiante no encontrado.</p>
        )}
        {student && (
          <div>
            <h2 className="text-center">Detalles del Estudiante</h2>
            <ul className="list-group">
            <li className="list-group-item">ID: {student.id}</li>
            <li className="list-group-item">Nombre: {student.name}</li>
            <li className="list-group-item">Apellido: {student.last_name}</li>
            <li className="list-group-item">RUT: {student.rut}</li>
            <li className="list-group-item">Correo: {student.email}</li>
            <li className="list-group-item">Edad: {student.age}</li>
            <li className="list-group-item">Escuela: {student.school_name}</li>
            <li className="list-group-item">Tipo de Colegio: {schoolTypeMap[student.school_type]}</li>
            <li className="list-group-item">Año de egreso: {student.senior_year > 2017 ? student.senior_year : "2017 o antes"}</li>
            <li className="list-group-item">Número de exámenes: {student.num_exams === 0 ? "Sin exámenes" : student.num_exams}</li>
            <li className="list-group-item">Puntaje promedio en exámenes: {student.score}</li>
            <li className="list-group-item">Tipo de pago: {student.payment_type === 0 ? "Pago al contado" : "Pago en cuotas"}</li>
            <li className="list-group-item">Número de cuotas: {student.num_installments === 1 ? "Pago al contado" : student.num_installments}</li>
            <li className="list-group-item">Monto inicial de Arancel: {student.tariff}</li>
            <li className="list-group-item">Matricula: {student.tuition}</li>
              
            </ul>
          </div>
        )}
      </div>
    );
  }
}

export default SearchStudentComponent;
