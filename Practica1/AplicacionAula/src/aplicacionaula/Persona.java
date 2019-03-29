package aplicacionaula;

public class Persona {

  // Variables
  public String nombre;
  public float peso;
  public int edad;
  protected  float altura;

  // Metodos
  public Persona() { // Constructor 1
   nombre = "";
   peso=0.0F;
   edad=0;
   altura=0.0F;
  }

  public Persona(String nom){ // Constructor 2
   nombre = nom;
   peso=0.0F;
   edad=0;
   altura=0.0F;
  }

  public void habla(){
    System.out.println(nombre+" esta hablando");
  }

  public void come(){
    System.out.println(nombre+" esta comiendo");
  }

  public void duerme(){
    System.out.println(nombre+" esta durmiendo");
  }

  public void trabaja(){
    System.out.println(nombre+" esta trabajando");
  }

}
