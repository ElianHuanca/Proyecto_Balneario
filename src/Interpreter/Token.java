/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpreter;

/**
 *
 * @author ELIAN
 */
public class Token {
    private int name;// si es CU, ACTION o ERROR
    private int attribute; // que tipo ya sea CU, ACTION o ERROR
    
    //constantes numericas para manejar el analex
    public static final int CU = 0;
    public static final int ACTION = 1;
    public static final int PARAMS = 2;
    public static final int END = 3;
    public static final int ERROR = 4;
    
    // ajustar de acuerdo a sus casos de uso con valores entre 100 a 199
    //Titulos de casos de uso en numero
    public static final int USUARIOS = 100;  
    public static final int TIPOSMEMBRESIAS = 101;
    public static final int MEMBRESIAS = 102;
    public static final int AMBIENTES = 103;
    public static final int RESERVAS = 104;
    public static final int DETALLERESERVAS = 105;
    public static final int PRODUCTOS = 106;
    public static final int USOS = 107;    
    public static final int INGRESOS = 108;
    public static final int PAGOS = 109;
    public static final int COMANDOS = 110;
    public static final int HELP = 111;
    
    //ajustar de acuerdo a sus acciones con valores entre 200 a 299
    //Titulos de las acciones generales
    public static final int ADD = 200;
    public static final int DELETE = 201;
    public static final int MODIFY = 202;
    public static final int GET = 203;
    public static final int VERIFY = 204;
    public static final int CANCEL = 205;
    public static final int REPORT = 206;
    public static final int AGREGAR = 207;
    public static final int POST = 208;
    public static final int PUT = 209;    
    public static final int GRAFICA = 210;    
    
    public static final int ERROR_COMMAND = 300;
    public static final int ERROR_CHARACTER = 301;
    
    //constantes literales para realizar un efecto de impresión
    public static final String LEXEME_CU = "caso de uso";
    public static final String LEXEME_ACTION = "action";
    public static final String LEXEME_PARAMS = "params";
    public static final String LEXEME_END = "end";
    public static final String LEXEME_ERROR = "error";
    
    // ajustar de acuerdo a sus casos de uso con valores en string
    //Titulos de casos de uso con string
    public static final String LEXEME_USUARIOS = "usuarios";
    public static final String LEXEME_TIPOSMEMBRESIAS = "tiposmembresias";
    public static final String LEXEME_MEMBRESIAS = "membresias";
    public static final String LEXEME_AMBIENTES = "ambientes";
    public static final String LEXEME_RESERVAS = "reservas";
    public static final String LEXEME_DETALLERESERVAS = "detallereservas";    
    public static final String LEXEME_PRODUCTOS = "productos";
    public static final String LEXEME_USOS = "usos";
    public static final String LEXEME_INGRESOS = "ingresos";
    public static final String LEXEME_PAGOS = "pagos";        
    public static final String LEXEME_COMANDOS = "comandos";     
    public static final String LEXEME_HELP = "help";     
    
    //ajustar de acuerdo a sus acciones con valores en string
    //Titulos de las acciones generales en string
    public static final String LEXEME_ADD = "add";
    public static final String LEXEME_DELETE = "delete";
    public static final String LEXEME_MODIFY = "modify";
    public static final String LEXEME_GET = "get";
    public static final String LEXEME_VERIFY = "verify";
    public static final String LEXEME_CANCEL = "cancel";
    public static final String LEXEME_REPORT = "report"; 
    public static final String LEXEME_AGREGAR = "agregar"; 
    public static final String LEXEME_POST = "post"; 
    public static final String LEXEME_PUT = "put";  
    public static final String LEXEME_GRAFICA = "grafica";  
    
    public static final String LEXEME_ERROR_COMMAND = "UNKNOWN COMMAND";
    public static final String LEXEME_ERROR_CHARACTER = "UNKNOWN CHARACTER";
    
    /**
     * Constructor por default.
     */
    public Token(){
        
    }
    
    /**
     * Constructor parametrizado por el literal del token
     * @param token 
     */
    //No Tocar
    public Token(String token){
        int id = findByLexeme(token);
        if(id != -1){
            if(100 <= id && id < 200){
                this.name = CU;
                this.attribute = id;
            } else if(200 <= id && id < 300){
                this.name = ACTION;
                this.attribute = id;
            }
        } else {
            this.name = ERROR;
            this.attribute = ERROR_COMMAND;
            System.err.println("Class Token.Constructor dice: \n "
                    + " El lexema enviado al constructor no es reconocido como un token \n"
                    + "Lexema: "+token);
        }
    }
    
    /**
     * Constructor parametrizado 2.
     * @param name 
     */
    public Token(int name){
        this.name = name;
    }
    
    /**
     * Constructor parametrizado 3.
     * @param name
     * @param attribute 
     */
    public Token(int name, int attribute){
        this.name = name;
        this.attribute = attribute;
    }
    
    // Setters y Getters
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public String toString(){
        if(0 <= name  && name <=1){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }else if(name == 2){
            return "< " + getStringToken(name) + " , " + attribute + ">";
        }else if(3 == name){
            return "< " + getStringToken(name) + " , " + "_______ >";
        } else if(name == 4){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }
        return "< TOKEN , DESCONOCIDO>";
    }
    
    /**
     * Devuelve el valor literal del token enviado
     * Si no lo encuentra retorna N: token.
     * @param token
     * @return 
     */
    //ajustar de acuerdo a sus CU
    public String getStringToken(int token){
        switch(token){
            case CU:
                return LEXEME_CU;
            case ACTION:
                return LEXEME_ACTION;
            case PARAMS:
                return LEXEME_PARAMS;
            case END:
                return LEXEME_END;
            case ERROR:
                return LEXEME_ERROR;
                
            //CU                     
            case USUARIOS:
                return LEXEME_USUARIOS;
            case TIPOSMEMBRESIAS:
                return LEXEME_TIPOSMEMBRESIAS;
            case MEMBRESIAS:
                return LEXEME_MEMBRESIAS;
            case AMBIENTES:
                return LEXEME_AMBIENTES;
            case RESERVAS:
                return LEXEME_RESERVAS;
            case DETALLERESERVAS :
                return LEXEME_DETALLERESERVAS;
            case PRODUCTOS :
                return LEXEME_PRODUCTOS;
            case USOS :
                return LEXEME_USOS;
            case INGRESOS :
                return LEXEME_INGRESOS;
            case PAGOS :
                return LEXEME_PAGOS;
            case COMANDOS :
                return LEXEME_COMANDOS;
            case HELP :
                return LEXEME_HELP;
                
            //ACCION
            case ADD:
                return LEXEME_ADD;
            case DELETE:
                return LEXEME_DELETE;
            case MODIFY:
                return LEXEME_MODIFY;
            case GET:
                return LEXEME_GET;
            case VERIFY:
                return LEXEME_VERIFY;
            case CANCEL:
                return LEXEME_CANCEL;
            case REPORT:
                return LEXEME_REPORT;
            case POST:
                return LEXEME_POST;
            case PUT:
                return LEXEME_PUT;
            case GRAFICA:
                return LEXEME_GRAFICA; 
                
            case ERROR_COMMAND:
                return LEXEME_ERROR_COMMAND;
            case ERROR_CHARACTER:
                return LEXEME_ERROR_CHARACTER;
            default:
                return "N: " + token;
        }
    }
    
    /**
     * Devuelve el valor numerico del lexema enviado
     * Si no lo encuentra retorna -1.
     * @param lexeme
     * @return 
     */
    //ajustar de acuerdo a sus CU
    private int findByLexeme(String lexeme){
        switch(lexeme){
            case LEXEME_CU:
                return CU;
            case LEXEME_ACTION:
                return ACTION;
            case LEXEME_PARAMS:
                return PARAMS;
            case LEXEME_END:
                return END;
            case LEXEME_ERROR:
                return ERROR;
              
            //CU                         
            case LEXEME_USUARIOS:
                return USUARIOS;
            case LEXEME_TIPOSMEMBRESIAS:
                return TIPOSMEMBRESIAS;
            case LEXEME_MEMBRESIAS:
                return MEMBRESIAS;
            case LEXEME_AMBIENTES:
                return AMBIENTES;
            case LEXEME_RESERVAS:
                return RESERVAS;
            case LEXEME_DETALLERESERVAS:
                return DETALLERESERVAS;
            case LEXEME_PRODUCTOS:
                return PRODUCTOS;
            case LEXEME_USOS:
                return USOS;
            case LEXEME_INGRESOS:
                return INGRESOS;
            case LEXEME_PAGOS:
                return PAGOS;
            case LEXEME_COMANDOS:
                return COMANDOS;
            case LEXEME_HELP:
                return HELP;
                
            //ACTION    
            case LEXEME_ADD:
                return ADD;
            case LEXEME_DELETE:
                return DELETE;
            case LEXEME_MODIFY:
                return MODIFY;
            case LEXEME_GET:
                return GET;
            case LEXEME_VERIFY:
                return VERIFY;
            case LEXEME_CANCEL:
                return CANCEL;
            case LEXEME_REPORT:
                return REPORT;
            case LEXEME_AGREGAR:
                return AGREGAR;
            case LEXEME_POST:
                return POST;
            case LEXEME_PUT:
                return PUT;
            case LEXEME_GRAFICA:
                return GRAFICA;
                
            case LEXEME_ERROR_COMMAND:
                return ERROR_COMMAND;            
            case LEXEME_ERROR_CHARACTER:
                return ERROR_CHARACTER;            
            default:
                return -1;
        }
    }
}
