package org.upemor.ep1;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gerardo
 */

@Setter
@Getter

public class Adyacencia {
    
    private Vertice vertice;
    private double costo;

    public Adyacencia(Vertice vertice, double costo) {
        this.vertice = vertice;
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "[" + vertice + ", " + costo + "]";
    }
    
}
