import React, { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import '../App.css';

export default function Header() {
  const [adminMenuVisible, setAdminMenuVisible] = useState(false);
  const [studentMenuVisible, setStudentMenuVisible] = useState(false);

  const toggleAdminMenu = () => {
    setAdminMenuVisible(!adminMenuVisible);
  };

  const toggleStudentMenu = () => {
    setStudentMenuVisible(!studentMenuVisible);
  };

  return (
    <div>
      <header>
        <nav className="container-header">
          <div className="header-content">
            <a href="/" className="text-header">
              Top Education
            </a>
            <div className="button-container">
                
              <button className="btn btn-light ml-2 main-button" onClick={toggleAdminMenu}>
                Modo administrativo
              </button>
              {adminMenuVisible && (
                <div className="sub-buttons">
                  <a href="/students" className="btn btn-light ml-2 sub-button">
                    Ver estudiantes
                  </a>
                  <a href="/exams" className="btn btn-light ml-2 sub-button">
                    Ver exámenes
                  </a>
                  <a href="/installments" className="btn btn-light ml-2 sub-button">
                    Ver cuotas
                  </a>
                  <a href="/exams/csv" className="btn btn-light ml-2 sub-button">
                    Importar excel
                  </a>
                  <a href="/loadAVG" className="btn btn-light ml-2 sub-button">
                    Cargar Promedios
                  </a>
                  
                </div>
              )}
              <h3> &nbsp; &nbsp; </h3>
              <button className="btn btn-light ml-2 main-button" onClick={toggleStudentMenu}>
                Modo estudiante
              </button>
              {studentMenuVisible && (
                <div className="sub-buttons">
                  <a href="/students/register" className="btn btn-light ml-2 sub-button">
                    Registrarse
                  </a>
                  <a href="/installments/create" className="btn btn-light ml-2 sub-button">
                    Generar cuotas
                  </a>
                  <a href="/installments/student" className="btn btn-light ml-2 sub-button">
                    Buscar cuotas
                  </a>
                  <a href="/students/search" className="btn btn-light ml-2 sub-button">
                    Buscar estudiante
                  </a>
                  <a href="/report" className="btn btn-light ml-2 sub-button">
                    Generar reporte
                  </a>
                  <a href="/exams/student" className="btn btn-light ml-2 sub-button">
                    Buscar exámenes
                  </a>
                </div>
              )}
            </div>
          </div>
        </nav>
      </header>
    </div>
  );
  
}




  
  
  
  