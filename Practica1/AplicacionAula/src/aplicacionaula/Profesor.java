package aplicacionaula;

import java.util.Date;

public class Profesor extends Persona {

  // Variables
  public Date tutoria;            //  Accesible
  private String despacho;        //  No accesible

  // Metodos publicos
  public Profesor(){ //  Constructor 1
    // Implicitamente se llama al constructor Persona()
    tutoria = new Date();
    despacho = "A asignar";
  }

  public Profesor(String nom){ //  Constructor 2
    super(nom); // Llamada explicita al constructor de la superclase
    tutoria = new Date();
    despacho = "A asignar";
  }

  public Profesor(String nom, String desp){  // Constructor 3
    super(nom); // Llamada explicita al constructor de la superclase
    tutoria = new Date();
    despacho = desp;
  }

  public String getDespacho(){
    return(despacho);         // Devuelve el despacho
  }

  public void setDespacho(String NuevoDespacho){
    despacho = NuevoDespacho; // Asigna un nuevo despacho
  }

  public void darClase(){
    this.preparaClase();
    System.out.println(nombre+" esta dando clase...");
  }

  public void responderPregunta(){
    System.out.println(nombre+" esta respondiendo...");
  }

  public void responderPregunta(String pregunta){
    this.pensar(pregunta);
    System.out.println(nombre+" esta respondiendo a la pregunta: "+pregunta);
  }

  public void pregunta(String pregunta){
    System.out.println(nombre+" pregunta:   "+pregunta);
  }

  public String toString(){ // Devuelve una cadena representativa del profesor
    return("Profesor "+nombre+",  con despacho '"+ despacho +"' y tutoria "+tutoria);
  }

  //Metodos privados
  private void preparaClase(){
    System.out.println(nombre+" esta preparandose la clase...");
  }

  private void pensar(String tema){
    System.out.println(nombre+" esta pensando sobre: "+tema);
  }

 //Metodo sobrecargado
  public void trabaja(){
    System.out.println(nombre+" esta trabajando en el despacho "+despacho);
  }
}
