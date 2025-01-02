package com.example.Liberalura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiberaluraApplication {

    private static final List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(LiberaluraApplication.class, args);
        new LiberaluraApplication().run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Buscar livros e guardar no banco de dados");
            System.out.println("2 - Mostrar títulos de todos os livros buscados");
            System.out.println("3 - Mostrar nomes dos autores dos livros");
            System.out.println("4 - Verificar autores vivos em um ano");
            System.out.println("5 - Filtrar livros por idioma");
            System.out.println("6 - Finalizar programa");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (choice) {
                case 1 -> fetchBooks();
                case 2 -> showTitles();
                case 3 -> showAuthors();
                case 4 -> checkAuthorsByYear(scanner);
                case 5 -> filterBooksByLanguage(scanner);
                case 6 -> {
                    System.out.println("Finalizando programa...");
                    running = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void fetchBooks() {
        // Simulação de dados da API do Gutendex
        books.add(new Book("Livro Exemplo 1", Arrays.asList(
                new Author("Autor 1", 1900, 1980),
                new Author("Autor 2", 1920, 2000))));
        books.add(new Book("Livro Exemplo 2", Arrays.asList(
                new Author("Autor 3", 1850, 1910))));
        books.add(new Book("Livro Exemplo 3", Arrays.asList(
                new Author("Autor 4", 1975, null))));

        System.out.println("Livros buscados e armazenados no banco de dados.");
    }

    private void showTitles() {
        if (books.isEmpty()) {
            System.out.println("Nenhum livro armazenado.");
        } else {
            books.forEach(book -> System.out.println(book.getTitle()));
        }
    }

    private void showAuthors() {
        if (books.isEmpty()) {
            System.out.println("Nenhum livro armazenado.");
        } else {
            books.stream()
                    .flatMap(book -> book.getAuthors().stream())
                    .map(Author::getName)
                    .distinct()
                    .forEach(System.out::println);
        }
    }

    private void checkAuthorsByYear(Scanner scanner) {
        System.out.println("Informe o ano:");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .filter(author -> author.getBirthYear() != null && author.getBirthYear() <= year && (author.getDeathYear() == null || author.getDeathYear() >= year))
                .map(Author::getName)
                .distinct()
                .forEach(System.out::println);
    }

    private void filterBooksByLanguage(Scanner scanner) {
        System.out.println("Escolha um idioma original:");
        System.out.println("1 - Português\n2 - Inglês\n3 - Espanhol\n4 - Francês\n5 - Alemão\n6 - Finlandês\n7 - Italiano\n8 - Russo");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String language = switch (choice) {
            case 1 -> "Português";
            case 2 -> "Inglês";
            case 3 -> "Espanhol";
            case 4 -> "Francês";
            case 5 -> "Alemão";
            case 6 -> "Finlandês";
            case 7 -> "Italiano";
            case 8 -> "Russo";
            default -> null;
        };

        if (language == null) {
            System.out.println("Opção inválida.");
        } else {
            books.stream()
                    .filter(book -> language.equalsIgnoreCase(book.getLanguage()))
                    .forEach(book -> System.out.println(book.getTitle()));
        }
    }

    static class Book {
        private final String title;
        private final List<Author> authors;
        private final String language = "Inglês"; // Idioma padrão para exemplo

        public Book(String title, List<Author> authors) {
            this.title = title;
            this.authors = authors;
        }

        public String getTitle() {
            return title;
        }

        public List<Author> getAuthors() {
            return authors;
        }

        public String getLanguage() {
            return language;
        }
    }

    static class Author {
        private final String name;
        private final Integer birthYear;
        private final Integer deathYear;

        public Author(String name, Integer birthYear, Integer deathYear) {
            this.name = name;
            this.birthYear = birthYear;
            this.deathYear = deathYear;
        }

        public String getName() {
            return name;
        }

        public Integer getBirthYear() {
            return birthYear;
        }

        public Integer getDeathYear() {
            return deathYear;
        }
    }
}
