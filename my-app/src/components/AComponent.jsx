import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileArchive, faCircleInfo, faFileCircleQuestion, faSchool, faHighlighter, faEnvelopeOpenText, faAddressBook, faFileExcel, faChartColumn, faGraduationCap, faMagnifyingGlass, faUserCircle, faFloppyDisk,
 } from '@fortawesome/free-solid-svg-icons'; // Importa los íconos

function IconExample() {
  return (
    <div>
      <h2 className='titulo text-center'>Seleccione un botón</h2>
      <div className='group-bottoms'>
        <h3> &nbsp; </h3>
        <a href="/list_students" class="boton list-group-item ">
          Ver estudiantes registrados
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faSchool} bounce style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton list-group-item ">
          Ver exámenes del sistema
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileCircleQuestion} bounce style={{ fontSize: "50px", paddingRight: "10px"}} />
          <FontAwesomeIcon icon={faHighlighter} bounce style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton list-group-item ">
          Ver cuotas del sistema
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faEnvelopeOpenText} bounce style={{ fontSize: "50px", paddingRight: "10px"}} />
          <FontAwesomeIcon icon={faAddressBook} bounce style={{fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton list-group-item ">
          Importar excel
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileExcel} bounce style={{ fontSize: "50px" }} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton list-group-item ">
          Cargar promedios
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faChartColumn} bounce style={{ fontSize: "50px" }} />
        </a>

        <h3> &nbsp; </h3>
      </div>
      <div className='group-bottoms'>
        <br></br>
        <a href="/list_students" class="boton2 list-group-item ">
          Registrar Estudiante
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faGraduationCap} bounce style={{ fontSize: "50px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton2 list-group-item ">
          Buscar Estudiante
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faUserCircle} bounce style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass} bounce style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton2 list-group-item ">
          Buscar Exámenes
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileCircleQuestion} bounce style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass} bounce style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; </h3>
      </div>
      <div className='group-bottoms'>
        <br></br>
        <a href="/list_students" class="boton2 list-group-item ">
          Generar cuotas
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faAddressBook} bounce style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faFloppyDisk} bounce style={{ fontSize: "35px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton2 list-group-item ">
          Buscar Cuotas
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faAddressBook} bounce style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faMagnifyingGlass} bounce style={{ fontSize: "25px"}} />
        </a>

        <h3> &nbsp; &nbsp; </h3>

        <a href="/list_students" class="boton2 list-group-item ">
          Generar reporte
          <h5><br></br></h5>
          <FontAwesomeIcon icon={faFileArchive} bounce style={{ fontSize: "50px", paddingRight: "5px"}} />
          <FontAwesomeIcon icon={faCircleInfo} bounce style={{ fontSize: "30px"}} />
        </a>
        <h3> &nbsp; </h3>
      </div>
    </div>
  );
}

export default IconExample;

