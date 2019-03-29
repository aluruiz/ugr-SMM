package aplicacionaula;

public class Alumno extends Persona {

  // Variables :
  public int curso;               //  Accesible
  public int numAsignaturas;      //  Accesible

 // Metodos:
  public Alumno(String nom){      // Constructor 1
    super(nom); // Llamada explicita al constructor de la superclase
    curso = numAsignaturas = 1;
  }

  public Alumno(String nom, int cur, int na){  // Constructor 2
    super(nom); // Llamada explicita al constructor de la superclase
    curso = cur;
    numAsignaturas = na;
  }

  public void pregunta(String pregunta){
    System.out.println(nombre+" tiene una duda:  "+pregunta);
  }

}
