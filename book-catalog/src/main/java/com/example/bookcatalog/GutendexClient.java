package com.example.bookcatalog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GutendexClient {
    private static final String BASE_URL = "http://gutendex.com/books/";

    public List<Book> searchBooks(String query) throws IOException {
        List<Book> books = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(BASE_URL + "?search=" + query);
        HttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getEntity().getContent());

        JsonNode results = rootNode.path("results");
        if (results.isArray()) {
            for (JsonNode result : results) {
                Book book = new Book();
                book.setTitle(result.path("title").asText());
                book.setAuthor(result.path("authors").get(0).path("name").asText());
                books.add(book);
            }
        }

        return books;
    }
}
