package com.stockmart.api.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//La notacion ResponseStatus se usa para identificar cual tipo de error se va a mostrar en la vista si no se encuentra lo esperado
@ResponseStatus(value = HttpStatus.NOT_FOUND)
//La extension la uso para que en caso de una peticion http salga el resultado esperado para una peticion asi
public class RecursoNoEncontradoExcepcion extends RuntimeException{
    public RecursoNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}
