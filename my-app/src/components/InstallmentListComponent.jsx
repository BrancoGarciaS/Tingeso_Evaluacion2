import React, { useState, useEffect } from "react";
import InstallmentService from "../services/InstallmentService";

function InstallmentsList() {
  const [installments, setInstallments] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    InstallmentService.getAllInstallments()
      .then((response) => {
        const data = response.data;
        setInstallments(data);
        setMessage(data.length === 0 ? "No hay cuotas en el sistema." : "");
      })
      .catch((error) => {
        setMessage("Hubo un error al obtener las cuotas.");
        console.error(error);
      });
  }, []);

  return (
    <div>
      <br></br>
      <h2 className="titulo text-center">Cuotas del Sistema</h2>
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
              <th>Fecha de Vencimiento</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {installments.map((item) => (
              <tr key={item.id_installment}>
                <td>{item.id_installment}</td>
                <td>{item.rut_installment}</td>
                <td>{item.payment_amount}</td>
                <td>{item.due_date}</td>
                <td>{item.installmentState === 0 ? "Sin pagar" : "Pagada"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default InstallmentsList;