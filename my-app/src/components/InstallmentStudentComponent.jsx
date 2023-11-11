import React, { useState } from "react";
import InstallmentService from "../services/InstallmentService";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass, faAddressBook } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

function InstallmentsList() {
  const [rut, setRut] = useState("");
  const [installments, setInstallments] = useState([]);
  const [message, setMessage] = useState("");

  const handleRutChange = (event) => {
    setRut(event.target.value);
  };

  const getInstallments = () => {
    InstallmentService.getInstallmentsByRut(rut)
      .then((response) => {
        const data = response.data;
        setInstallments(data);
        setMessage(data.length === 0 ? "El estudiante no tiene cuotas" : "");
      })
      .catch((error) => {
        setMessage("Hubo un error al obtener las cuotas.");
        console.error(error);
      });
  };

  const payInstallment = (id) => {
    InstallmentService.payInstallment(id)
      .then((response) => {
        const result = response.data;
        if (result === 1) {
          setMessage("Pago realizado con éxito.");
          // Actualizar el estado de installmentState en la lista de cuotas
          const updatedInstallments = installments.map((item) =>
            item.id_installment === id ? { ...item, installmentState: 1 } : item
          );
          setInstallments(updatedInstallments);
        } else if (result === 0) {
          setMessage("Pago sin éxito.");
        }
      })
      .catch((error) => {
        setMessage("Hubo un error al procesar el pago.");
        console.error(error);
      });
  };

  return (
    <div>
      <h1>&nbsp;</h1>
      <FontAwesomeIcon icon={faAddressBook} beat style={{color: "#1f2251", fontSize: "50px", padding: "15px"}} />
      <FontAwesomeIcon icon={faMagnifyingGlass} beat style={{color: "#1f2251", fontSize: "25px"}}/>
      
      <h2 className="titulo text-center">Buscar cuotas de Estudiantes</h2>
      <div className='container_subida'>
        <label className= "entrada" htmlFor="rut">Ingrese el RUT del estudiante: </label>
        <br></br>
        <br></br>
        <input
          type="text"
          id="rut"
          value={rut}
          onChange={handleRutChange}
          placeholder="Ejemplo: 21.257.371-K"
        />
        <br></br>
        <br></br>
        <button className="btn btn-light ml-2 main-button2" onClick={getInstallments}>Obtener Cuotas</button>
      </div>
      <div>
        <p>{message}</p>
      </div>
      <div>
        <table class="table table-hover" border = "3">
          <thead>
            <tr class="cabecera-tabla table-danger">
              <th>ID de Cuota</th>
              <th>RUT de Cuota</th>
              <th>Monto</th>
              <th>Fecha de Inicio</th>
              <th>Fecha de Vencimiento</th>
              <th>Fecha de Pago</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {installments.map((item) => (
              <tr key={item.id_installment}>
                <td>{item.id_installment}</td>
                <td>{item.rut_installment}</td>
                <td>{item.interest_payment_amount}</td>
                <td>{item.start_date}</td>
                <td>{item.due_date}</td>
                <td>{item.payment_date}</td>
                <td>{item.installmentState === 0 ? "Sin pagar" : "Pagada"}</td>
                <td>
                  {item.installmentState === 0 && (
                    <button onClick={() => payInstallment(item.id_installment)}>
                      Pagar
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default InstallmentsList;