package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;


import entities.Product;

public class App {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter full file path: ");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Product> list = new ArrayList<>();
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            //faz a média utilizando stream.map para pegar apenas os valores, e o .reduce para somar todas os valores
            double avg = list.stream().map(p -> p.getPrice()).reduce(0.0, (x,y) -> x + y) / list.size();
            System.out.println("Average price: " + String.format("%.2f", avg));

            //Comparator para ordenar em ordem crescente
            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            //cria uma nova lista filtrando apenas os produtos com preco abaixo da média usando  list.stream.filter, e em seguida selecionando apenas o nome dos produtos com .map, para ordenar utiliza o .sortede com um Comparator personalizado
            List<String> names = list.stream().filter(p -> p.getPrice() < avg).map(p -> p.getName()).sorted(comp.reversed()).collect(Collectors.toList());

            //imprime a lista names usando forEach
            names.forEach(System.out::println);

        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
