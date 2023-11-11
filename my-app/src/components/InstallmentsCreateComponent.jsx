import React, { useState } from "react";
import InstallmentService from "../services/InstallmentService";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAddressBook, faPen } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

function InstallmentComponent() {
  const [rut, setRut] = useState("");
  const [message, setMessage] = useState("");

  const handleRutChange = (event) => {
    setRut(event.target.value);
  };

  const generateInstallments = () => {
    InstallmentService.createInstallmentsByRut(rut)
      .then((response) => {
        const result = response.data;
        if (result === 0) {
          setMessage("El estudiante no existe en la base de datos.");
        } else if (result === 1) {
          setMessage("Las cuotas se generaron con éxito.");
        } else if (result === 2) {
          setMessage("Las cuotas ya se generaron anteriormente.");
        }
      })
      .catch((error) => {
        setMessage("Hubo un error al procesar la solicitud.");
        console.error(error);
      });
  };

  return (
    <div>
      <FontAwesomeIcon icon={faAddressBook} beat style={{color: "#1f2251", fontSize: "50px", padding: "10px"}} />
      <FontAwesomeIcon icon={faPen} beat style={{color: "#1f2251", fontSize: "25px"}} />
      
      <h2 className="titulo text-center">Generación de Cuotas</h2>
      <br></br>
      <div className='container_subida'>
      <div>
        <label htmlFor="rut" className="entrada titulo">Ingrese el RUT del estudiante: </label>
        <br></br>
        <br></br>
        <input
          type="text"
          id="rut"
          value={rut}
          onChange={handleRutChange}
          placeholder="Ejemplo: 21.257.371-K"
        />
      </div>
      <br></br>
      <button className="btn btn-light ml-2 main-button2" onClick={generateInstallments}>Generar Cuotas</button>
      <div>
        <p>{message}</p>
      </div>
    </div>
    </div>
  );
}

export default InstallmentComponent;
