import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileArchive, faCircleInfo, faFileCircleQuestion, faSchool, faHighlighter, faEnvelopeOpenText, faAddressBook, faFileExcel, faChartColumn, faGraduationCap, faMagnifyingGlass, faUserCircle, faFloppyDisk,
 } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

function ModuleHome() {
  return (
    <div>
      <h1 className='titulo2 text-header'>Seleccione un botón</h1>
      <div className='group-bottoms'>
        <h3> &nbsp; </h3>
        <a href="/students" class="boton list-group-item ">
          Ver estudiantes registrados
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faSchool}  style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/exams" class="boton list-group-item ">
          Ver exámenes del sistema
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileCircleQuestion}  style={{ fontSize: "50px", paddingRight: "10px"}} />
          <FontAwesomeIcon icon={faHighlighter}  style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/installments" class="boton list-group-item ">
          Ver cuotas del sistema
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faEnvelopeOpenText}  style={{ fontSize: "50px", paddingRight: "10px"}} />
          <FontAwesomeIcon icon={faAddressBook}  style={{fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/exams/csv" class="boton list-group-item ">
          Importar excel
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileExcel}  style={{ fontSize: "50px" }} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/loadAVG" class="boton list-group-item ">
          Cargar promedios
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faChartColumn}  style={{ fontSize: "50px" }} />
        </a>

        <h3> &nbsp; </h3>
      </div>
      <div className='group-bottoms'>
        <br></br>
        <a href="/students/register" class="boton2 list-group-item ">
          Registrar Estudiante
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faGraduationCap}  style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/students/search" class="boton2 list-group-item ">
          Buscar Estudiante
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faUserCircle}  style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass}  style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/exams/student" class="boton2 list-group-item ">
          Buscar Exámenes
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileCircleQuestion}  style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass}  style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; </h3>
      </div>
      <div className='group-bottoms'>
        <br></br>
        <a href="/installments/create" class="boton2 list-group-item ">
          Generar cuotas
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faAddressBook}  style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faFloppyDisk}  style={{ fontSize: "35px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/installments/student" class="boton2 list-group-item ">
          Buscar Cuotas
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faAddressBook}  style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass}  style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/report" class="boton2 list-group-item ">
          Generar reporte
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileArchive}  style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faCircleInfo}  style={{ fontSize: "30px"}} />
        </a>
        <h3> &nbsp; </h3>
      </div>
    </div>
  );
}

export default ModuleHome;

