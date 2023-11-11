import React, { Component } from 'react';
import AdministrationService from '../services/AdministrationService';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInfoCircle, faUser, faFileLines } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

class ReportComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      rut: '',
      reportData: null,
      errorMessage: '',
    };
  }

  handleInputChange = (event) => {
    this.setState({
      rut: event.target.value,
    });
  };

  generateReport = () => {
    const { rut } = this.state;

    if (!rut) {
      this.setState({ errorMessage: 'Ingresa un RUT válido.' });
      return;
    }

    AdministrationService.createReport(rut)
      .then((response) => {
        const reportData = response.data;

        if (Object.keys(reportData).length === 0) {
          this.setState({ reportData: null, errorMessage: 'El estudiante no está registrado.' });
        } else {
          this.setState({ reportData, errorMessage: '' });
        }
      })
      .catch((error) => {
        console.error('Error generating report:', error);
        this.setState({ reportData: null, errorMessage: 'Error al generar el informe.' });
      });
  };

  render() {
    return (
      <div>
        <FontAwesomeIcon icon={faUser} beat style={{color: "#1f2251", fontSize: "50px", padding: "15px"}} />
        <FontAwesomeIcon icon={faInfoCircle} beat style={{color: "#1f2251", fontSize: "30px", paddingRight: "10px"}}/>
        <FontAwesomeIcon icon={faFileLines} beat style={{color: "#1f2251", fontSize: "30px"}}/>
        {/* 
        <FontAwesomeIcon icon={faAddressBook} beat style={{color: "#1f2251", fontSize: "50px", padding: "10px"}} />
        <FontAwesomeIcon icon={faPen} beat style={{color: "#1f2251", fontSize: "25px"}} />
        */}
        
        <h2 className="titulo text-center">Generar Reporte</h2>
        <div className='container_subida'>
        <div className="form-group">
          <label className='entrada'>Ingresa el RUT del estudiante para generar reporte:</label>
          <br></br>
          <br></br>
          <input
            type="text"
            name="rut"
            value={this.state.rut}
            onChange={this.handleInputChange}
            className="form-control"
          />
        </div>
        <br></br>
        <button className="btn btn-light ml-2 main-button2" onClick={this.generateReport}>
          Generar Informe
        </button>
        </div>
        {this.state.errorMessage && <p className="text-danger">{this.state.errorMessage}</p>}
        {this.state.reportData && (
          <div className='container_subida'>
            <h3>Informe del Estudiante</h3>
            <p>RUT: {this.state.reportData.rut}</p>
            <p>Nombre: {this.state.reportData.name_student}</p>
            <p>Apellido: {this.state.reportData.last_name}</p>
            <p>Estado de planilla: {this.state.reportData.areGenerated === 1 ? 'Cuotas generadas' : 'Cuotas sin generar'}</p>
            <p>Número de exámenes rendidos: {this.state.reportData.num_exams}</p>
            <p>Puntaje promedio de exámenes: {this.state.reportData.mean_score}</p>
            <p>Monto de la matrícula: {this.state.reportData.tuition}</p>
            <p>Monto total inicial del arancel: {this.state.reportData.original_tariff}</p>
            <p>Monto total actual del arancel: {this.state.reportData.total_tariff}</p>
            <p>Monto del arancel con intereses: {this.state.reportData.interest_tariff}</p>
            <p>Saldo por pagar: {this.state.reportData.tariff_to_pay}</p>
            <p>Monto total pagado: {this.state.reportData.tariff_paid}</p>
            <p>Tipo de pago: {this.state.reportData.payment_type === 0 ? 'Pago al Contado' : 'Pago en Cuotas'}</p>
            <p>Número de cuotas: {this.state.reportData.num_installments}</p>
            <p>Número de cuotas pagadas: {this.state.reportData.num_installments_paid}</p>
            <p>Número de cuotas atrasadas: {this.state.reportData.late_installments}</p>
            <p>Última fecha de pago: {this.state.reportData.last_payment || 'Sin pagos realizados'}</p>

          </div>
        )}
      </div>
    );
  }
}

export default ReportComponent;
