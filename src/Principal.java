import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;


public class Principal {
    public static void main(String[] args) {

        int opcion = 0;
        double valorAConvertir = 0;
        double dolarAPesoArgentino = (1.0 / 0.00078);
        double pesoArgentinoADolar = (0.00078);
        double realBrasilenoADolar = (0.18);
        double dolarARealBrasileno = (1.0 / 0.18);
        double dolarAPesoColombiano = (1.0 / 0.00025);
        double pesoColombianoADolar = (0.00025);

        System.out.println("Bienvenido/a al Conversor de Moneda =]");

        Scanner lectura = new Scanner(System.in);

        String apiKey = "b84d33fa726a2b3b1edc47f5";
        String monedaBase = "USD";
        String urlApi = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;

        Map<String, Double> tasasDeConversionAPI = null;

        try {
            HttpClient client = HttpClient.newHttpClient(); // Tu "agente de compras"
            HttpRequest request = HttpRequest.newBuilder() // El "formulario de pedido"
                    .uri(URI.create(urlApi)) // Le dices la dirección completa del restaurante
                    .build(); // Construye el pedido

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // El "agente" envía el pedido y espera la respuesta
            String jsonResponse = response.body(); // La "caja" con los productos (las tasas en JSON)
            System.out.println("Respuesta de la API (JSON crudo):");
            System.out.println(jsonResponse); // Para ver el JSON que la API te devuelve

            Gson gson = new Gson();
            ExchangeRateResponse apiResponse = gson.fromJson(jsonResponse, ExchangeRateResponse.class);
            tasasDeConversionAPI = apiResponse.getConversionRates();

            System.out.println("\nTasas obtenidas de la API (ejemplo):");
            System.out.println("1 USD a ARS: " + tasasDeConversionAPI.get("ARS"));
            System.out.println("1 USD a BRL: " + tasasDeConversionAPI.get("BRL"));
            System.out.println("1 USD a COP: " + tasasDeConversionAPI.get("COP"));


        } catch (IOException | InterruptedException e) {
            System.out.println("Ocurrió un error al obtener las tasas de cambio de la API: " + e.getMessage());
            System.out.println("Se usarán las tasas fijas predefinidas.");
        }catch (Exception e) { // <-- Capturamos otras excepciones generales (ej. de Gson si el JSON es inválido)
            System.out.println("Ocurrió un error general al obtener o procesar las tasas de cambio de la API: " + e.getMessage());
            System.out.println("Se usarán las tasas fijas predefinidas.");
        }


            while (opcion != 7) { // El bucle se repite mientras la opción no sea '7' (Salir)
                System.out.println("\n**************************************");
                System.out.println("1) Dólar => Peso argentino");
                System.out.println("2) Peso argentino => Dólar");
                System.out.println("3) Dólar => Real brasileño");
                System.out.println("4) Real brasileño => Dólar");
                System.out.println("5) Dólar => Peso colombiano"); // Añadimos la opción de la imagen
                System.out.println("6) Peso colombiano => Dólar"); // Añadimos la opción de la imagen
                System.out.println("7) Salir");
                System.out.println("Elija una opción válida:");
                System.out.println("**************************************");

                while (!lectura.hasNextInt()) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número para la opción.");
                    lectura.next(); // Consume la entrada inválida
                }

                opcion = lectura.nextInt(); // Leemos la opción que ingresa el usuario

                if (opcion >= 1 && opcion <= 6) { // Verificamos que sea una opción de conversión válida
                    System.out.println("Ingrese el valor que desea convertir:");
                    while (!lectura.hasNextDouble()) {
                        System.out.println("Entrada inválida. Por favor, ingrese un número para el valor.");
                        lectura.next(); // Consume la entrada inválida
                    }
                    valorAConvertir = lectura.nextDouble(); // Leemos el valor a convertir

                    double resultado = 0; // Variable para almacenar el resultado de la conversión

                    if (tasasDeConversionAPI != null) {
                        switch (opcion) { // Usamos un 'switch' para ejecutar la acción según la opción elegida
                        case 1: // Dólar a Peso Argentino
                            resultado = valorAConvertir * pesoArgentinoADolar;
                            System.out.println(valorAConvertir + " USD es igual a " + resultado + " ARS");
                            break;
                        case 2: // Peso Argentino a Dólar
                            resultado = valorAConvertir / pesoArgentinoADolar;
                            System.out.println(valorAConvertir + " ARS es igual a " + resultado + " USD");
                            break;
                        case 3: // Dólar a Real Brasileño
                            resultado = valorAConvertir / dolarARealBrasileno;
                            System.out.println(valorAConvertir + " USD es igual a " + resultado + " BRL");
                            break;
                        case 4: // Real Brasileño a Dólar
                            resultado = valorAConvertir * dolarARealBrasileno;
                            System.out.println(valorAConvertir + " BRL es igual a " + resultado + " USD");
                            break;
                        case 5: // Dólar a Peso Colombiano
                            resultado = valorAConvertir * tasasDeConversionAPI.get("COP");
                            System.out.println(valorAConvertir + " USD es igual a " + resultado + " COP");
                            break;
                        case 6: // Peso Colombiano a Dólar
                            resultado = valorAConvertir / tasasDeConversionAPI.get("COP");
                            System.out.println(valorAConvertir + " COP es igual a " + resultado + " USD");
                            break;
                    }
                } else {
                        System.out.println("Usando tasas de cambio fijas debido a un problema con la API.");
                        switch (opcion) {
                            case 1:
                                resultado = valorAConvertir * dolarAPesoArgentino;
                                System.out.println(valorAConvertir + " USD es igual a " + resultado + " ARS (fijo)");
                                break;
                            case 2:
                                resultado = valorAConvertir * pesoArgentinoADolar;
                                System.out.println(valorAConvertir + " ARS es igual a " + resultado + " USD (fijo)");
                                break;
                            case 3:
                                resultado = valorAConvertir * dolarARealBrasileno;
                                System.out.println(valorAConvertir + " USD es igual a " + resultado + " BRL (fijo)");
                                break;
                            case 4:
                                resultado = valorAConvertir * realBrasilenoADolar;
                                System.out.println(valorAConvertir + " BRL es igual a " + resultado + " USD (fijo)");
                                break;
                            case 5:
                                resultado = valorAConvertir * dolarAPesoColombiano;
                                System.out.println(valorAConvertir + " USD es igual a " + resultado + " COP (fijo)");
                                break;
                            case 6:
                                resultado = valorAConvertir * pesoColombianoADolar;
                                System.out.println(valorAConvertir + " COP es igual a " + resultado + " USD (fijo)");
                                break;
                        }
                    }

                } else if (opcion == 7) {
                    System.out.println("Saliendo del conversor. ¡Hasta pronto!");
                } else {
                    System.out.println("Opción inválida. Por favor, elija una opción del 1 al 7.");
                }
            }

        lectura.close();
        }
    }






