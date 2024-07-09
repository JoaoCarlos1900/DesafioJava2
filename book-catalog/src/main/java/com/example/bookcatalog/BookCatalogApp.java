package com.example.bookcatalog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class 1BookCatalogApp {
    private static GutendexClient gutendexClient = new GutendexClient();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Buscar livros");
            System.out.println("2. Listar livros no banco de dados");
            System.out.println("3. Adicionar livro ao banco de dados");
            System.out.println("4. Remover livro do banco de dados");
            System.out.println("5. Sair");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    searchBooks();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void searchBooks() {
        System.out.print("Digite o título do livro: ");
        String query = scanner.nextLine();

        try {
            List<Book> books = gutendexClient.searchBooks(query);
            for (Book book : books) {
                System.out.println("Título: " + book.getTitle() + ", Autor: " + book.getAuthor());
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
        }
    }

    private static void listBooks() {
        // Implementar lógica para listar livros no banco de dados
    }

    private static void addBook() {
        System.out.print("Digite o título do livro: ");
        String title = scanner.nextLine();
        System.out.print("Digite o autor do livro: ");
        String author = scanner.nextLine();

        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.executeUpdate();
            System.out.println("Livro adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    private static void removeBook() {
        // Implementar lógica para remover livro do banco de dados
    }
}
