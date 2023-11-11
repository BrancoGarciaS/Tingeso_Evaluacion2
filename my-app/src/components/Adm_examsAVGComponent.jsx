import React, { Component } from 'react';
import AdministrationService from '../services/AdministrationService';

class AVGcomponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      examMeans: [],
    };
  }

  componentDidMount() {
    this.loadExamMeans();
  }

  loadExamMeans() {
    AdministrationService.getMeans()
      .then((response) => {
        this.setState({ examMeans: response.data });
      })
      .catch((error) => {
        console.error('Error loading exam means:', error);
      });
  }

  render() {
    return (   
      <div>
        <h2 className="titulo text-center">Media de Exámenes</h2>
        <h1>&nbsp;</h1>
        <table class="table table-hover" border = "3">
          <thead>
            <tr class="cabecera-tabla table-danger">
              <th>RUT</th>
              <th>Número de Exámenes</th>
              <th>Puntaje Promedio</th>
            </tr>
          </thead>
          <tbody>
            {this.state.examMeans.map((examMean) => (
              <tr key={examMean.rut}>
                <td>{examMean.rut}</td>
                <td>{examMean.n_exams}</td>
                <td>{examMean.avg}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <h2>&nbsp;</h2>
        <a href="/exams" className="btn btn-light ml-2 main-button2">
            Regresar
        </a>
      </div>
    );
  }
}

export default AVGcomponent;
