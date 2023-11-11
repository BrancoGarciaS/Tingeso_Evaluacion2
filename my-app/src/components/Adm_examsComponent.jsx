import React, { Component } from 'react';
import AdministrationService from '../services/AdministrationService';


class ExamListComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      exams: [],
    };
  }

  componentDidMount() {
    this.loadExams();
  }

  loadExams() {
    AdministrationService.getAllExams()
      .then((response) => {
        this.setState({ exams: response.data });
      })
      .catch((error) => {
        console.error('Error loading exams:', error);
      });
  }

  render() {
    return (
      <div>
        <br></br>
        <br></br>
        <h2 className="titulo text-center">Lista de Exámenes</h2>
        <a href="/exams/avg" className="btn btn-light ml-2 main-button2">
            Calcular promedios
        </a>
        <br></br>
        <br></br>
        <table class="table table-hover" border = "3">
          <thead>
            <tr class="cabecera-tabla table-danger">
              <th>ID de Examen</th>
              <th>RUT</th>
              <th>Fecha de Examen</th>
              <th>Puntaje</th>
              <th>Fecha de Carga</th>
            </tr>
          </thead>
          <tbody>
            {this.state.exams.map((exam) => (
              <tr key={exam.id_exam}>
                <td>{exam.id_exam}</td>
                <td>{exam.rut}</td>
                <td>{exam.exam_date}</td>
                <td>{exam.exam_score}</td>
                <td>{exam.load_date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default ExamListComponent;
