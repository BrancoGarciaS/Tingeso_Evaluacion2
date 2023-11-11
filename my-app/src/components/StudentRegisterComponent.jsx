import React, { Component } from 'react';
import studentService from '../services/StudentService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGraduationCap, faUser } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

class RegisterStudentComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      rut: '',
      name: '',
      last_name: '',
      email: '',
      birthdate: '',
      school_name: '',
      school_type: '1', // Inicializo con '1' (Municipal) por defecto
      senior_year: '2017', // Año anterior por defecto
      num_installments: '1', // Pago al contado por defecto
      message: '', // Mensaje de registro
    };
  }

  handleInputChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  handleSubmit = (event) => {
    event.preventDefault();

    const studentData = {
      rut: this.state.rut,
      name: this.state.name,
      last_name: this.state.last_name,
      email: this.state.email,
      birthdate: this.state.birthdate,
      school_name: this.state.school_name,
      school_type: parseInt(this.state.school_type),
      senior_year: parseInt(this.state.senior_year),
      num_installments: parseInt(this.state.num_installments),
    };

    studentService.createStudent(studentData)
      .then((res) => {
        this.setState({
          message: 'Estudiante registrado con éxito',
        });
        console.log('Estudiante registrado con éxito:', res.data);
      })
      .catch((error) => {
        this.setState({
          message: 'Error al registrar estudiante',
        });
        console.error('Error al registrar estudiante:', error);
      });
  };

  render() {
    return (
      <div>
        <br></br>
        <FontAwesomeIcon icon={faGraduationCap} beat style={{color: "#1f2251", fontSize: "40px", padding: "20px"}} />
        <FontAwesomeIcon icon={faUser} beat style={{color: "#1f2251", fontSize: "50px"}} />
        <h2 className="titulo text-center">Registrar Estudiante</h2>
        <h1>&nbsp;</h1>
        <div className="registro">
        <form onSubmit={this.handleSubmit}>
          <div className="form-group">
            <label className='entrada2'>RUT:</label>
            <input
              type="text"
              name="rut"
              value={this.state.rut}
              onChange={this.handleInputChange}
              className="form-control"
            />
          </div>
          <h6> &nbsp; </h6>
          <div className="form-group">
              <label className='entrada2'>Nombre:</label>
              <input
                type="text"
                name="name"
                value={this.state.name}
                onChange={this.handleInputChange}
                className="form-control"
              />
            </div>
            <h6> &nbsp; </h6>
            <div className="form-group">
              <label className='entrada2'>Apellido:</label>
              <input
                type="text"
                name="last_name"
                value={this.state.last_name}
                onChange={this.handleInputChange}
                className="form-control"
              />
            </div>
            <h6> &nbsp; </h6>
            <div className="form-group">
              <label className='entrada2'>Correo Electrónico:</label>
              <input
                type="email"
                name="email"
                value={this.state.email}
                onChange={this.handleInputChange}
                className="form-control"
              />
            </div>
            <h6> &nbsp; </h6>
            <div className="form-group">
              <label className='entrada2'>Fecha de Nacimiento:</label>
              <input
                type="date"
                name="birthdate"
                value={this.state.birthdate}
                onChange={this.handleInputChange}
                className="form-control"
              />
            </div>
            <h6> &nbsp; </h6>
            <div className="form-group">
              <label className='entrada2'>Nombre de la Escuela:</label>
              <input
                type="text"
                name="school_name"
                value={this.state.school_name}
                onChange={this.handleInputChange}
                className="form-control"
              />
            </div>
            <h6> &nbsp; </h6>
          <div className="form-group">
            <label className='entrada2'>Tipo de Escuela:</label>
            <select
              name="school_type"
              value={this.state.school_type}
              onChange={this.handleInputChange}
              className="form-control"
            >
              <option value="1">Municipal</option>
              <option value="2">Subvencionado</option>
              <option value="3">Privado</option>
            </select>
          </div>
          <h6> &nbsp; </h6>
          <div className="form-group">
            <label className='entrada2'>Año de Egreso:</label>
            <select
              name="senior_year"
              value={this.state.senior_year}
              onChange={this.handleInputChange}
              className="form-control"
            >
              <option value="2023">2023</option>
              <option value="2022">2022</option>
              <option value="2021">2021</option>
              <option value="2020">2020</option>
              <option value="2019">2019</option>
              <option value="2018">2018</option>
              <option value="2017">2017 o anterior</option>
            </select>
          </div>
          <h6> &nbsp; </h6>
          <div className="form-group">
            <label className='entrada2'>Número de Cuotas:</label>
            <select
              name="num_installments"
              value={this.state.num_installments}
              onChange={this.handleInputChange}
              className="form-control"
            >
              <option value="1">1 (Pago al contado)</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
            </select>
          </div>
          <h6> &nbsp; </h6>
          <button className="btn btn-light ml-2 main-button2" type="submit">
            Guardar
          </button>
        </form>
        <p>{this.state.message}</p>
        </div>
      </div>
    );
  }
}

export default RegisterStudentComponent;
