package com.example.Project1.Service;

import com.example.Project1.DTO.AverageResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class NumberService {
    private static final int WINDOW_SIZE = 10;
    private final Map<String, LinkedList<Integer>> windows = new HashMap<>();
    private static final Map<String, String> URLS = Map.of(
            "p", "http://20.244.56.144/test/primes",
            "f", "http://20.244.56.144/test/fibo",
            "e", "http://20.244.56.144/test/even",
            "r", "http://20.244.56.144/test/random"
    );

    public AverageResponse getNumbers(String numberId) {
        List<Integer> newNumbers = fetchNumbersFromUrl(numberId);
        LinkedList<Integer> window = windows.getOrDefault(numberId, new LinkedList<>());

        // Save previous state
        List<Integer> prevState = new ArrayList<>(window);


        for (int number : newNumbers) {
            if (!window.contains(number)) {
                window.add(number);
            }
        }
        while (window.size() > WINDOW_SIZE) {
            window.removeFirst();
        }
        double avg = window.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        windows.put(numberId, window);

        return new AverageResponse(newNumbers, prevState, new ArrayList<>(window), avg);
    }

    private List<Integer> fetchNumbersFromUrl(String numberId) {
        String url = URLS.get(numberId);
        if (url == null) {
            return Collections.emptyList();
        }

        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);

            // Add bearer token to request headers
            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzE3ODI2Mjc1LCJpYXQiOjE3MTc4MjU5NzUsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6IjRmNmVjZTExLTgxZDYtNDAyMy1hNDdhLWQwM2ZkODBlZTA2YSIsInN1YiI6IlNhamFsMjExNTQwNkBha2dlYy5hYy5pbiJ9LCJjb21wYW55TmFtZSI6ImdvTWFydCIsImNsaWVudElEIjoiNGY2ZWNlMTEtODFkNi00MDIzLWE0N2EtZDAzZmQ4MGVlMDZhIiwiY2xpZW50U2VjcmV0IjoiaGdSTFpnY2tjY3pDcWNWTyIsIm93bmVyTmFtZSI6IlNhamFsIiwib3duZXJFbWFpbCI6IlNhamFsMjExNTQwNkBha2dlYy5hYy5pbiIsInJvbGxObyI6IjIxMDAyNzE1NDAwNDcifQ.MS4gtRofq9UwR8WOXG47U-quQ-nRk3s3BdpquDmVbEI");

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                return parseNumbers(response.toString());
            } else if (responseCode == 401) {
                System.err.println("Unauthorized access: Received 401 status code.");

            } else {
                System.err.println("Received error response: " + responseCode);

            }
        } catch (Exception e) {
            System.err.println("Error fetching numbers from URL: " + e.getMessage());
        }
        return Collections.emptyList();
    }


    private List<Integer> parseNumbers(String response) {
        int start = response.indexOf('[') + 1;
        int end = response.indexOf(']');
        if (start > 0 && end > start) {
            String[] numbers = response.substring(start, end).split(",");
            List<Integer> numberList = new ArrayList<>();
            for (String number : numbers) {
                numberList.add(Integer.parseInt(number.trim()));
            }
            return numberList;
        }
        return Collections.emptyList();
    }
}
