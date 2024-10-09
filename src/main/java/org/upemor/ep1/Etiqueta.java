package org.upemor.ep1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gerardo
 */

@Getter
@Setter
@AllArgsConstructor

public class Etiqueta {
    
    private Vertice vertice;
    private double costo;
    
    @Override
    public String toString(){
        return "["+vertice+","+costo+"]";
    }
    
}
