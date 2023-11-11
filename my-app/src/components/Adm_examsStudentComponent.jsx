import React, { Component } from 'react';
import AdministrationService from '../services/AdministrationService'; 

class ExamsStudent extends Component {
    constructor(props) {
      super(props);
      this.state = {
        rut: '',
        exams: [],
        hasSearched: false, // Agrega una propiedad hasSearched en el estado inicial
      };
    }
  
    handleRutChange = (e) => {
      this.setState({ rut: e.target.value });
    }
  
    handleSearch = () => {
      const { rut } = this.state;
      AdministrationService.getExamsByRut(rut)
        .then((response) => {
          this.setState({ exams: response.data, hasSearched: true });
        })
        .catch((error) => {
          console.error(error);
          this.setState({ hasSearched: true });
        });
    }
  
    render() {
      const { rut, exams, hasSearched } = this.state;
  
      return ( 
        <div>
          <h2 className="titulo text-center">Buscar exámenes de un estudiante</h2>
          <div className='container_subida'>
            <div>
              <label className='entrada'>Buscar RUT:</label>
              <br></br><br></br>
              <input type="text" value={rut} onChange={this.handleRutChange} />
              <br></br><br></br>
              <button className="btn btn-light ml-2 main-button2" onClick={this.handleSearch}>Buscar</button>
            </div>
          </div>
          {hasSearched && (
            <div>
              <br></br>
              <h3 className='titulo text-center'>Exámenes de la persona con RUT: {rut}</h3>
              {exams.length > 0 ? (
                <table class="table table-hover" border = "3">
                  <thead>
                    <tr class = "cabecera-tabla table-danger" style={{color: "red"}}>
                      <th>Id del exámen</th>
                      <th>Fecha del exámen</th>
                      <th>Puntaje del exámen</th>
                      <th>Fecha de carga</th>
                    </tr>
                  </thead>
                  <tbody>
                    {exams.map((exam) => (
                      <tr key={exam.id_exam}>
                        <td>{exam.id_exam}</td>
                        <td>{exam.exam_date}</td>
                        <td>{exam.exam_score}</td>
                        <td>{exam.load_date}</td>
                      </tr>
                    ))}
                  </tbody>
              
                </table>
              ) : (
                <h4 className='titulo'> <br></br>No se encontraron exámenes para el RUT proporcionado.</h4>
              )}
            </div>
          )}
        </div>
      );
    }
  }
  

export default ExamsStudent;
