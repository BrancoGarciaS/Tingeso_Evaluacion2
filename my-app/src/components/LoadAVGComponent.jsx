import React, { Component } from 'react';
import StudentService from '../services/StudentService';

class SaveExamsComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      successMessage: null,
      errorMessage: null,
    };
  }

  handleSaveExams = () => {
    StudentService.saveExams()
      .then((response) => {
        if (response.data.length > 0) {
          // Hay datos en la respuesta, mostrar promedios guardados con éxito
          const data = response.data.map((item, index) => (
            <div key={index}>
              Rut: {item.rut}, Número de Exámenes: {item.n_exams}, Promedio: {item.avg}
            </div>
          ));
          this.setState({ successMessage: 'Promedios guardados con éxito', successData: data, errorMessage: null });
        } else {
          // La respuesta es una lista vacía, mostrar mensaje de que no hay exámenes guardados
          this.setState({ successMessage: null, successData: null, errorMessage: 'No hay exámenes guardados' });
        }
      })
      .catch((error) => {
        // Manejar cualquier error de la solicitud
        console.error(error);
        this.setState({ successMessage: null, successData: null, errorMessage: 'Error al guardar los promedios' });
      });
  }

  render() {
    const { successMessage, successData, errorMessage } = this.state;

    return (
      <div>
        <h2 className="titulo text-center">Guardar Promedios de Exámenes</h2>
        <br></br>
        <div className='container_subida'>
        <h2 className="entrada titulo">Precaución: <h2>Al presionar el botón se guardarán<br></br> 
        los promedios y se borrarán todos los exámenes</h2></h2>
          <button className="btn btn-light ml-2 main-button2" onClick={this.handleSaveExams}>Guardar Promedios</button>
          {successMessage && (
            <div>
              <p>{successMessage}</p>
              {successData}
            </div>
          )}
          {errorMessage && <p>{errorMessage}</p>}
        </div>
      </div>
    );
  }
}

export default SaveExamsComponent;
