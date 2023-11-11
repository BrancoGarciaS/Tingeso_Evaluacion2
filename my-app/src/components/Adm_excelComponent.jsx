import React, { Component } from 'react';
import AdministrationService from '../services/AdministrationService';

class LoadExcelComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: null,
      message: '',
    };
  }

  handleFileChange = (event) => {
    this.setState({
      file: event.target.files[0],
    });
  };

  handleUpload = () => {
    if (!this.state.file) {
      this.setState({ message: 'Selecciona un archivo antes de subirlo.' });
      return;
    }

    const formData = new FormData();
    formData.append('file', this.state.file);

    AdministrationService.loadExcel(formData)
      .then((response) => {
        this.setState({ message: response.data });
      })
      .catch((error) => {
        console.error('Error uploading Excel:', error);
        this.setState({ message: 'Error al cargar el archivo Excel.' });
      });
  };

  render() {
    return (
      <div>
        <h2 className="titulo text-center">Cargar Archivo Excel</h2>
        <br></br>
        <div className='container_subida'>
          <div className="form-group">
            <label className='entrada titulo'>Selecciona un archivo Excel:</label>
            <br></br>
            <br></br>
            <input
              type="file"
              onChange={this.handleFileChange}
              accept=".csv"
            />
          </div>
          <br></br>
          <button className="btn btn-light ml-2 main-button2" onClick={this.handleUpload}>
            Subir Excel
          </button>
          {this.state.message && <p className="text-success">{this.state.message}</p>}
        </div>
      </div>
    );
  }
}

export default LoadExcelComponent;
