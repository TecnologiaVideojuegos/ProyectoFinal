
package com.mycompany.javaeat;
 
import java.time.LocalDate;


public class TarjetaCredito {
    
    private String titular;
    private String numero;
    private LocalDate fechaCaducidad; 
    
    
    public TarjetaCredito(String titular, String numero, LocalDate fechaCaducidad){
        
            if (titular == null || titular.isEmpty()) {
            throw new IllegalArgumentException("Debe introducir el nombre del titular");
        }
        
        if (numero.length()!= 16) {
            throw new IllegalArgumentException("Número de tarjeta inválido");
        }
        if (fechaCaducidad.isBefore(LocalDate.now())) {
            
            throw new IllegalArgumentException("Fecha de caducidad inválida.");
        } 
       this.titular = titular;
       this.numero = numero;
       this.fechaCaducidad = fechaCaducidad; 
          
        
    }
        
    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    
    
}
