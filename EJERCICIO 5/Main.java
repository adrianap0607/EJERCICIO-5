import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            ArrayList<Jugador> jugadores = new ArrayList<>();
            System.out.println("==== Menú ====");
            System.out.println("1. Registrar jugador");
            System.out.println("2. Calcular efectividad");
            System.out.println("3. Mostrar los 3 mejores líberos");
            System.out.println("4. Guardar en CSV");
            System.out.println("5. Cargar desde CSV");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarJugador(jugadores, scanner);
                    break;
                case 2:
                    calcularEfectividad(jugadores);
                    break;
                case 3:
                    mostrarMejoresLiberos(jugadores);
                    break;
                case 4:
                    guardarEnCSV(jugadores);
                    break;
                case 5:
                    ArrayList<Jugador>jugadores2 = cargarDesdeCSV();
                    jugadores.addAll(jugadores2);
                    break;

                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Introduce un número del 1 al 6.");
                    break;
            }
        }

        System.out.println("Saliendo del programa...");
    }

    private static void registrarJugador(ArrayList<Jugador> jugadores, Scanner scanner) {
        System.out.println("Ingresa el nombre del jugador: ");
        String nombre = scanner.next();
        System.out.println("Ingresa el país del jugador: ");
        String pais = scanner.next();
        System.out.println("Ingresa la cantidad de errores del jugador: ");
        int errores = scanner.nextInt();
        System.out.println("Ingresa la cantidad de aces del jugador: ");
        int aces = scanner.nextInt();
        System.out.println("Ingresa el total de servicios del jugador: ");
        int totalServicios = scanner.nextInt();

        System.out.println("Selecciona el tipo de jugador (Libero, Pasador, Auxiliar): ");
        String tipoJugador = scanner.next();

        switch (tipoJugador) {
            case "Libero":
                System.out.println("Ingresa la cantidad de recibos del líbero: ");
                int recibos = scanner.nextInt();
                jugadores.add(new Libero(nombre, pais, errores, aces, totalServicios, recibos));
                break;
            case "Pasador":
                System.out.println("Ingresa la cantidad de pases del pasador: ");
                int pases = scanner.nextInt();
                System.out.println("Ingresa la cantidad de fintas del pasador: ");
                int fintas = scanner.nextInt();
                jugadores.add(new Pasador(nombre, pais, errores, aces, totalServicios, pases, fintas));
                break;
            case "Auxiliar":
                System.out.println("Ingresa la cantidad de ataques del auxiliar/opuesto: ");
                int ataques = scanner.nextInt();
                System.out.println("Ingresa la cantidad de bloqueos efectivos del auxiliar/opuesto: ");
                int bloqueosEfectivos = scanner.nextInt();
                System.out.println("Ingresa la cantidad de bloqueos fallidos del auxiliar/opuesto: ");
                int bloqueosFallidos = scanner.nextInt();
                jugadores.add(new Auxiliares(nombre, pais, errores, aces, totalServicios, ataques, bloqueosEfectivos, bloqueosFallidos));
                break;
            default:
                System.out.println("Tipo de jugador no válido.");
        }
        System.out.println("Jugador registrado con éxito.");
    }

    private static void calcularEfectividad(ArrayList<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            double efectividad = jugador.calcularEfectividad();
            System.out.println(jugador.getNombre() + " - Efectividad: " + efectividad);
        }
    }

    private static void mostrarMejoresLiberos(ArrayList<Jugador> jugadores) {
        ArrayList<Libero> liberos = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            if (jugador instanceof Libero) {
                liberos.add((Libero) jugador);
            }
        }

        if (liberos.isEmpty()) {
            System.out.println("No hay líberos registrados.");
        } else {
            Collections.sort(liberos, Comparator.comparing(Libero::calcularEfectividad).reversed());
            System.out.println("Los 3 mejores líberos son:");
            int contador = 0;
            for (Libero libero : liberos) {
                if (contador < 3) {
                    System.out.println(libero.getNombre() + " - Efectividad: " + libero.calcularEfectividad());
                    contador++;
                } else {
                    break;
                }
            }
        }
    }

    private static void guardarEnCSV(ArrayList<Jugador> jugadores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Jugadores.csv", true))) {
            for (Jugador jugador : jugadores) {
                String tipoJugador = jugador.getTipoJugador();
                StringBuilder fila = new StringBuilder();
                fila.append(jugador.getNombre()).append(",");
                fila.append(jugador.getPais()).append(",");
                fila.append(jugador.getErrores()).append(",");
                fila.append(jugador.getAces()).append(",");
                fila.append(jugador.getTotalServicios()).append(",");
                fila.append(tipoJugador).append(",");
    
                if ("Libero".equals(tipoJugador)) {
                    Libero libero = (Libero) jugador;
                    fila.append(libero.getRecibos()).append(",");
                } else if ("Pasador".equals(tipoJugador)) {
                    Pasador pasador = (Pasador) jugador;
                    fila.append(pasador.getPases()).append(",");
                    fila.append(pasador.getFintas()).append(",");
                } else if ("Auxiliar".equals(tipoJugador)) {
                    Auxiliares auxiliar = (Auxiliares) jugador;
                    fila.append(auxiliar.getAtaques()).append(",");
                    fila.append(auxiliar.getBloqueosEfectivos()).append(",");
                    fila.append(auxiliar.getBloqueosFallidos()).append(",");
                }
                writer.println(fila.toString());
            }
            System.out.println("Datos guardados en CSV exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Jugador> cargarDesdeCSV() {
        ArrayList<Jugador>jugadores= new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Jugadores.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0].trim();
                String pais = parts[1].trim();
                int errores = Integer.parseInt(parts[2].trim());
                int aces = Integer.parseInt(parts[3].trim());
                int totalServicios = Integer.parseInt(parts[4].trim());
                String tipoJugador = parts[5].trim();

                if (tipoJugador.equals("Libero")) {
                    int recibos = Integer.parseInt(parts[6].trim());
                    jugadores.add(new Libero(nombre, pais, errores, aces, totalServicios, recibos));
                } else if (tipoJugador.equals("Pasador")) {
                    int pases = Integer.parseInt(parts[6].trim());
                    int fintas = Integer.parseInt(parts[7].trim());
                    jugadores.add(new Pasador(nombre, pais, errores, aces, totalServicios, pases, fintas));
                } else if (tipoJugador.equals("Auxiliar")) {
                    int ataques = Integer.parseInt(parts[6].trim());
                    int bloqueosEfectivos = Integer.parseInt(parts[7].trim());
                    int bloqueosFallidos = Integer.parseInt(parts[8].trim());
                    jugadores.add(new Auxiliares(nombre, pais, errores, aces, totalServicios, ataques, bloqueosEfectivos, bloqueosFallidos));
                }
                
            }
            System.out.println("Datos cargados desde CSV exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadores;
    }
}
