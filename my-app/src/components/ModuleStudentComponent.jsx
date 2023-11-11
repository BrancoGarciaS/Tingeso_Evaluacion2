import React from "react";

const listGroupStyle = {
    marginTop: "2px", // Ajusta la cantidad de espacio entre el título y la lista
 };

export default function StudentModule(){
    return(
        <div>
            
            <h1>Modo estudiante</h1>
            <h4>(buscar información y registrarse con Rut)</h4>
            <div class="list-group" style={listGroupStyle}>
                <a href="/" class="list-group-item disabled">
                    Opciones
                </a>
                <a href="/register" class="list-group-item">Registrarse</a>
                <a href="/search-student" class="list-group-item">Buscar estudiante</a>
                <a href="/register2" class="list-group-item">Ver cuotas</a>
                <a href="/register3" class="list-group-item">Generar reporte</a>
                <br></br>
                <a href="/" class="list-group-item">Menú principal</a>
            </div>
            
            
        </div>
        
        
    )
}