
package com.mycompany.javaeat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// si te sobra tiempo verifica si el dni y el cif son reales o no 
public class Usuario {
    
    private static final String EXPRESION_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})*/$";
    //el + indica que debe haber al menos una y * es cero o varios  
    private static final String EXPRESION_NOMBRE = "^([A-Z]{1}[a-z]+[ ]*){1,2}";
    // las llaves son cuantas repeticiones
    private static final String EXPRESION_DNI = "^[0-9]{8}[A-Z]$";
    private static final String EXPRESION_TELEFONO = "^\\+34[69]\\d{8}$";
    /*número de teléfono que empieza con el prefijode España), 
    seguido de un "6" o un "9", y ocho dígitos más*/
    private static final String EXPRESION_CLAVE = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    /* clave con 8 caracteres con al menos un numero, un caracter especial y una letra
     varios grupos lookhead que simplemente evaluan una condicion 
     (?=.*[A-Za-z]) evalua si hay caracteres letras
     (?=.*\d) evalua si hay caracteres digitos 
     (?=.*[@$!%*#?&]) evalua si hay caracteres especiales
     [A-Za-z\\d@$!%*#?&]{8,} longitud minima de 8 caracteres
    */
    private static final String EXPRESION_WEB = "^https://[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(:[0-9]+)?(\\/[A-Za-z0-9-]+)*(\\/[A-Za-z0-9-]+\\.[A-Za-z]+)?$";
    /*
    Comienza con "https://", 
    serie de caracteres alfanuméricos y guiones, que pueden estar seguidos por una o más repeticiones 
    Número de puerto opcional , serie de directorios opcionales, seguidos de un nombre de archivo opcional con una extensión de archivo.  
    */
    private static final String EXPRESION_CIF= "^ [A-HJNP-SUVW]{1}[0-9]{7}[0-9A-J]{1}\"$";
    
    private TipoUsuario tipoUsuario;
    private String correo;
    private String nombre;
    private String DNI;
    private TarjetaCredito tarjetaCredito;
    private Direccion direccion;
    private String numeroTelefono;
    private String clave;
    private String website;
    private String CIF; 
    // PREVENIMOS ALGUNOS ERRORES DEL USUARIO 
    
    public Usuario( TipoUsuario tipoUsuario , String correo, String nombre, String DNI, Direccion direccion, String clave, TarjetaCredito tarjetaCredito, String numeroTelefono, String website, String CIF) {
        if (tipoUsuario == null || correo == null|| clave == null) {
            throw new IllegalArgumentException("Faltan campos por especificar");
                }
        switch (tipoUsuario){
                
            case  ADMINISTRADOR :
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (! "admin".equals(clave)){
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                break;
               
            case CLIENTE_PARTICULAR :
            if ( DNI == null || direccion == null || numeroTelefono == null || tarjetaCredito == null || nombre == null) {
                throw new IllegalArgumentException("Faltan campos por especificar");
                }
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (!esDNIValido(DNI)) {
                    throw new IllegalArgumentException("El DNI  proporcionado es inválido");
                }
                if (!esNombreValido(nombre)) {
                    throw new IllegalArgumentException("El nombre  proporcionado es inválido");
                }
                if (!esNumeroTelefono(numeroTelefono)) {
                    throw new IllegalArgumentException("El número  proporcionado es inválido");
                }
                if (!esClaveCliente(clave)) {
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                break;
            
            case EMPRESA:
                if ( CIF == null || direccion == null || numeroTelefono == null || tarjetaCredito == null || website == null|| nombre == null) {
                    throw new IllegalArgumentException("Faltan campos por especificar");
                }
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (!esNumeroTelefono(numeroTelefono)) {
                    throw new IllegalArgumentException("El número  proporcionado es inválido");
                }
                if (!esClaveCliente(clave)) {
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                if (!esWebSite(website)) {
                    throw new IllegalArgumentException("No se encuentra la página web");
                }
                if (!esCIFValido(CIF)) {
                    throw new IllegalArgumentException("El CIF proporcionado es invalido");
                }
                break;
        default:
            throw new IllegalArgumentException(" Usuario es desconocido");
        }
    
    this.tipoUsuario = tipoUsuario;
    this.correo = correo;
    this.nombre = nombre;
    this.DNI = DNI;
    this.direccion = direccion;
    this.tarjetaCredito = tarjetaCredito;
    this.numeroTelefono = numeroTelefono;
    this.clave = clave;
    this.website = website;
    this.CIF = CIF; 
    
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public TipoUsuario setTipoUsuario(TipoUsuario tipoUsuario) {
        return this.tipoUsuario=tipoUsuario;
    }
    
    public String getCorreo() {
        return correo;
    }
    private boolean esCorreoValido(String email){
       if(email==null){
           return false;
       }
       Pattern patron= Pattern.compile( EXPRESION_CORREO);
       Matcher match = patron.matcher(email);
       return match.find();
    }

    public void setCorreo(String correo) {
        if (esCorreoValido(correo)){
            this.correo = correo;
        }
    }

    public String getNombre() {
        return nombre;
    }
    
    public static boolean esNombreValido(String nombre){
        return nombre.matches(EXPRESION_NOMBRE);
    }
    
    public void setNombre(String nombre) {
        if (esNombreValido(nombre)){
           this.nombre=nombre;     
        }
    }

    public String getCIF() {
        return CIF;
    }

    private static boolean esCIFValido(String CIF){
        Pattern patron = Pattern.compile(EXPRESION_CIF);
        Matcher match = patron.matcher(CIF);
        return match.find();
    }
    public void setCIF(String CIF) {
        if (esCIFValido(CIF)){
        this.CIF = CIF;
        }
    }

    public String getDNI() {
        return DNI;
    }
   
    public static boolean esDNIValido(String DNI){
         return DNI.matches(EXPRESION_DNI);
    }

    public void setDNI(String DNI) {
        if (esDNIValido(DNI)){
            this.DNI = DNI;
        }
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    private boolean esNumeroTelefono( String numeroTelefono){
        Pattern patron = Pattern.compile(EXPRESION_TELEFONO);
        Matcher match = patron.matcher(numeroTelefono);
        return match.find(); 
    }
    public void setNumeroTelefono(String numeroTelefono) {
        if (esNumeroTelefono(numeroTelefono)){
            this.numeroTelefono = numeroTelefono;
        }
    }

    public String getClave() {
        return clave;
    }

    private boolean esClaveCliente(String clave){
        Pattern patron = Pattern.compile(EXPRESION_CLAVE);
        Matcher match = patron.matcher(clave);
        return match.find();   
    }
    
    public void setClave(String clave) {
        if (esClaveCliente (clave)){
            this.clave = clave;
        }     
    }
    
    public String getWebsite() {
        return website;
    }

    private boolean esWebSite(String website){
        Pattern patron = Pattern.compile(EXPRESION_WEB);
        Matcher matcher = patron.matcher(website);
        return matcher.find();
    }
    public void setWebsite(String website) {
        if (esWebSite(website)){
            this.website = website;
        }
    }
    
}
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// si te sobra tiempo verifica si el dni y el cif son reales o no 
public class Usuario {
    
    private static final String EXPRESION_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})*$";
    //el + indica que debe haber al menos una y * es cero o varios  
    private static final String EXPRESION_NOMBRE = "^([A-Z]{1}[a-z]+[ ]*){1,2}";
    // las llaves son cuantas repeticiones
    private static final String EXPRESION_DNI = "^[0-9]{8}[A-Z]$";
    private static final String EXPRESION_TELEFONO = "^\\+34[69]\\d{8}$";
    /*número de teléfono que empieza con el prefijode España), 
    seguido de un "6" o un "9", y ocho dígitos más*/
    private static final String EXPRESION_CLAVE = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    /* clave con 8 caracteres con al menos un numero, un caracter especial y una letra
     varios grupos lookhead que simplemente evaluan una condicion 
     (?=.*[A-Za-z]) evalua si hay caracteres letras
     (?=.*\d) evalua si hay caracteres digitos 
     (?=.*[@$!%*#?&]) evalua si hay caracteres especiales
     [A-Za-z\\d@$!%*#?&]{8,} longitud minima de 8 caracteres
    */
    private static final String EXPRESION_WEB = "^https://[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(:[0-9]+)?(\\/[A-Za-z0-9-]+)*(\\/[A-Za-z0-9-]+\\.[A-Za-z]+)?$";
    /*
    Comienza con "https://", 
    serie de caracteres alfanuméricos y guiones, que pueden estar seguidos por una o más repeticiones 
    Número de puerto opcional , serie de directorios opcionales, seguidos de un nombre de archivo opcional con una extensión de archivo.  
    */
    private static final String EXPRESION_CIF= "^[A-HJNP-SUVW]{1}[0-9]{7}[0-9A-J]{1}$";
    
    private TipoUsuario tipoUsuario;
    private String correo;
    private String nombre;
    private String DNI;
    private TarjetaCredito tarjetaCredito;
    private Direccion direccion;
    private String numeroTelefono;
    private String clave;
    private String website;
    private String CIF; 
    

// PREVENIMOS ALGUNOS ERRORES DEL USUARIO MEDIANTE UN CONSTRUCTOR
    
    public Usuario( TipoUsuario tipoUsuario , String correo, String nombre, String DNI, Direccion direccion, String clave, TarjetaCredito tarjetaCredito, String numeroTelefono, String website, String CIF) {
        if (tipoUsuario == null || correo == null|| clave == null) {
            throw new IllegalArgumentException("Faltan campos por especificar");
                }
        switch (tipoUsuario){
                
            case  ADMINISTRADOR :
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (! "admin".equals(clave)){
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                break;
               
            case CLIENTE_PARTICULAR :
            if ( DNI == null || direccion == null || numeroTelefono == null || tarjetaCredito == null || nombre == null) {
                throw new IllegalArgumentException("Faltan campos por especificar");
                }
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (!esDNIValido(DNI)) {
                    throw new IllegalArgumentException("El DNI  proporcionado es inválido");
                }
                if (!esNombreValido(nombre)) {
                    throw new IllegalArgumentException("El nombre  proporcionado es inválido");
                }
                if (!esNumeroTelefono(numeroTelefono)) {
                    throw new IllegalArgumentException("El número  proporcionado es inválido");
                }
                if (!esClaveCliente(clave)) {
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                break;
            
            case EMPRESA:
                if ( CIF == null || direccion == null || numeroTelefono == null || tarjetaCredito == null || website == null|| nombre == null) {
                    throw new IllegalArgumentException("Faltan campos por especificar");
                }
                if (!esCorreoValido(correo)) {
                    throw new IllegalArgumentException("El correo electrónico proporcionado es inválido");
                }
                if (!esNumeroTelefono(numeroTelefono)) {
                    throw new IllegalArgumentException("El número  proporcionado es inválido");
                }
                if (!esClaveCliente(clave)) {
                    throw new IllegalArgumentException("La clave proporcionada es inválida");
                }
                if (!esWebSite(website)) {
                    throw new IllegalArgumentException("No se encuentra la página web");
                }
                if (!esCIFValido(CIF)) {
                    throw new IllegalArgumentException("El CIF proporcionado es invalido");
                }
                break;
        default:
            throw new IllegalArgumentException(" Usuario es desconocido");
        }
    
    this.tipoUsuario = tipoUsuario;
    this.correo = correo;
    this.nombre = nombre;
    this.DNI = DNI;
    this.direccion = direccion;
    this.tarjetaCredito = tarjetaCredito;
    this.numeroTelefono = numeroTelefono;
    this.clave = clave;
    this.website = website;
    this.CIF = CIF; 
    
    }
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public TipoUsuario setTipoUsuario(TipoUsuario tipoUsuario) {
        return this.tipoUsuario=tipoUsuario;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    private boolean esCorreoValido(String email){
       Pattern patron= Pattern.compile( EXPRESION_CORREO);
       Matcher match = patron.matcher(email);
       return match.find(); // comprobamos unicamente si el formato del correo es correcto
    }

    public void setCorreo(String correo) {
        if (esCorreoValido(correo)){
            this.correo = correo;
        }
    }

    public String getNombre() {
        return nombre;
    }
    
    public static boolean esNombreValido(String nombre){
        Pattern patron= Pattern.compile( EXPRESION_NOMBRE);
        Matcher match = patron.matcher(nombre);
        return match.find(); // De nuevo, solo validamos el formato no si es el nombre es coherente 
    }
    
    public void setNombre(String nombre) {
        if (esNombreValido(nombre)){
           this.nombre=nombre;     
        }
    }

    public String getCIF() {
        return CIF;
    }

    private static boolean esCIFValido(String CIF){
        Pattern patron = Pattern.compile(EXPRESION_CIF);
        Matcher match = patron.matcher(CIF);
        return match.find();
    }
    
    public void setCIF(String CIF) {
        if (esCIFValido(CIF)){
        this.CIF = CIF;
        }
    }

    public String getDNI() {
        return DNI;
    }
   
    public static boolean esDNIValido(String DNI){
       Pattern patron= Pattern.compile( EXPRESION_DNI);
       Matcher match = patron.matcher(DNI);    
       return match.find(); // NO CALCULA SI EL FORMATO ES UN DNI POSIBLE SOLO COMPRUEBA EL FORMATO / ESTRUCTURA 
    }

    public void setDNI(String DNI) {
        if (esDNIValido(DNI)){
            this.DNI = DNI;
        }
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    private boolean esNumeroTelefono( String numeroTelefono){
        Pattern patron = Pattern.compile(EXPRESION_TELEFONO);
        Matcher match = patron.matcher(numeroTelefono);
        return match.find(); 
    }
    
    public void setNumeroTelefono(String numeroTelefono) {
        if (esNumeroTelefono(numeroTelefono)){
            this.numeroTelefono = numeroTelefono;
        }
    }

    public String getClave() {
        return clave;
    }

    private boolean esClaveCliente(String clave){
        Pattern patron = Pattern.compile(EXPRESION_CLAVE);
        Matcher match = patron.matcher(clave);
        return match.find();   
    }
    
    public void setClave(String clave) {
        if (esClaveCliente (clave)){
            this.clave = clave;
        }     
    }
    
    public String getWebsite() {
        return website;
    }

    private boolean esWebSite(String website){
        Pattern patron = Pattern.compile(EXPRESION_WEB);
        Matcher matcher = patron.matcher(website);
        return matcher.find();
    }
    
    public void setWebsite(String website) {
        if (esWebSite(website)){
            this.website = website;
        }
    }
    

    public void registrarse(TipoUsuario tipoUsuario, String correo, String clave, String nombre, Direccion direccion, TarjetaCredito tarjetaCredito, String numeroTelefono, String CIF) {
        
        
        switch(tipoUsuario){
            
            
            case CLIENTE_PARTICULAR:
                this.correo = correo;
                this.clave = clave;
                this.nombre = nombre;
                this.direccion = direccion;
                this.tarjetaCredito = tarjetaCredito;
                this.numeroTelefono = numeroTelefono;
            
            case EMPRESA:
                this.correo = correo;
                this.clave = clave;
                this.nombre = nombre;
                this.direccion = direccion;
                this.tarjetaCredito = tarjetaCredito;
                this.numeroTelefono = numeroTelefono;
                this.CIF = CIF;
                
            case ADMINISTRADOR:
                this.clave=clave;
                this.correo=correo;
            
        }
    
    }
    
    public boolean iniciarSesion(String correo, String clave) {
        return (this.correo.equals(correo) && this.clave.equals(clave));

    }

    public void actualizarDatos(Direccion direccion, TarjetaCredito tarjetaCredito, String numeroTelefono) {
        this.direccion = direccion;
        this.tarjetaCredito = tarjetaCredito;
        this.numeroTelefono = numeroTelefono;
    }

    public void eliminarCuenta() {
        this.tipoUsuario = null;
        this.correo = null;
        this.clave = null;
        this.nombre = null;
        this.direccion = null;
        this.tarjetaCredito = null;
        this.numeroTelefono = null;
    }
    
}
