package org.upemor.ep1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gerardo
 */

@Setter
@Getter

public class Vertice {
    private String nombre;
    private boolean visitado;
    private Etiqueta etiqueta;
    
    private List<Adyacencia> adyacencias;

    public Vertice(String nombre) {    
        this.nombre = nombre;
        this.adyacencias = new LinkedList<>();
    }
    
    public void addAdyacencias(Adyacencia... adyacencias){
        Collections.addAll(this.adyacencias, adyacencias);
        
    }

    @Override
    public String toString() {
        return nombre;
    } 

    public Vertice(boolean visitado) {
        this.visitado = visitado;
    }
}
