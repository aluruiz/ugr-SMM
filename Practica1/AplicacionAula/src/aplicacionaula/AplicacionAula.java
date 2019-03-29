package aplicacionaula;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AplicacionAula {

       static double a = 3.7;
       char letra = 'a'; 
    public static void main(String[] args) {
        Profesor jesus,joaquin;
        Alumno juan,beatriz;
        Persona per;

        jesus   = new Profesor("Jesus");
        joaquin = new Profesor("Joaquin","B7");
        juan  = new Alumno("Juan");
        beatriz = new Alumno("Beatriz",4,5);
        per = new Persona();

        per.altura=3.3F;
        System.out.println("\n"+jesus.toString());   // Mostramos a los profesores
        System.out.println(joaquin.toString());
        jesus.setDespacho("C7");                     // Cambiamos el despacho de Jesus
        System.out.println(jesus.toString()+"\n");   // Comprobamos la nueva informacion

        jesus.darClase();                               // Jesus comineza la clase
        jesus.habla();
        juan.pregunta("¿Que es un objeto?");             // El alumno juan pregunta
        jesus.responderPregunta("¿Que es un objeto?");   // Jesus contesta
        beatriz.pregunta("¿Es lo mismo clase y objeto?");
        jesus.responderPregunta("¿Es lo mismo clase y objeto?");
        jesus.pregunta("¿Os habeis enterado?");
        
        System.out.println("Usando las opciones de java.lang.\n Practica 1: ");
        System.out.println("Absoluto de A: " + abs(a));
        System.out.println("Raiz cuadrada de A: " + sqrt(a));
        if (Character.isLowerCase('a')){
            System.out.println("Es minuscula.");
        } else{
            System.out.println("Es mayuscula.");
        }
        
        System.out.println("Convertir 5 a String");
        String valor = Integer.toString(5);
        
        System.out.println("Convertir '5' en Entero");
        int valor2 = Integer.parseInt(valor);
        
        System.out.println("Convertir 5.5 a String");
        String valor3 = Double.toString(5.5);
        
        System.out.println("1: " + valor + " 2: " + valor2 + " 3: " + valor3);
        
        System.out.println("¿Numero maximo de flotantes? ");
        
        System.out.println("Mostrar la segundo caracter de hola");
        String string = "hola";
        String[] parts = string.split("");
        String part2 = parts[1];
        System.out.println(part2);
        
        System.out.println(string.equals("Hola"));
        
        String string2 = "Hola";
        
        string2 = string2 + " adios"; 
        
        System.out.println(string2);        
    }
}
