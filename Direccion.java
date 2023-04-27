
package com.mycompany.javaeat;


public class Direccion {
    
    
    private String calle;
    private int numero;
    private int codigoPostal;
    private String ciudad;

    public Direccion(String calle, int numero, String ciudad , int codigoPostal) 
        throws IllegalArgumentException { 
        if (ciudad == null || ciudad.isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía.");
        }
        
        if (numero <= 0) {
            throw new IllegalArgumentException("El número debe ser un número positivo.");
        }
        if (codigoPostal < 10000 || codigoPostal > 99999) {
            throw new IllegalArgumentException("El código postal debe ser un número de 5 dígitos.");
        }
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal= codigoPostal;
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


}
