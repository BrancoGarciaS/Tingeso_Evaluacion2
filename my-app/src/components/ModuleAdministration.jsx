import React from "react";


export default function AdministrationModule(){
    return(
        <div>
            <h1>Modo administrativo</h1>
            <div class="list-group">
                <a href="/" class="list-group-item disabled">
                    Opciones
                </a>
                <a href="/list_students" class="list-group-item">Ver estudiantes registrados</a>
                <a href="/" class="list-group-item">Importar notas de excel</a>
                <a href="/" class="list-group-item">Ver promedios de exámenes</a>
                <br></br>
                <a href="/" class="list-group-item">Menú principal</a>
            </div>
            
            
        </div>
        
        
    )
}