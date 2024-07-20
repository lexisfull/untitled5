package ru.panov;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        String clientLogin = console.nextLine();

        // 127.0.0.1 или localhost
        try (Socket server = new Socket("localhost", 8888)) {
            System.out.println("Успешно подключились к серверу");

            try (PrintWriter out = new PrintWriter(server.getOutputStream(), true)) {
                Scanner in = new Scanner(server.getInputStream());

                String loginRequest = createLoginRequest(clientLogin);
                out.println(loginRequest);

                String loginResponseString = in.nextLine();
                if (!checkLoginResponse(loginResponseString)) {
                    System.out.println("Не удалось подключиться к серверу");
                    return;
                }

                new Thread(() -> {
                    while (true) {
                        String msgFromServer = in.nextLine();
                        System.out.println("Сообщение от сервера: " + msgFromServer);
                    }
                }).start();


                while (true) {
                    System.out.println("Что хочу сделать?");
                    System.out.println("1. Послать сообщение другу");
                    System.out.println("2. Послать сообщение всем");
                    System.out.println("3. Получить список логинов");

                    String type = console.nextLine();
                    if (type.equals("1")) {
                        SendMessageRequest request = new SendMessageRequest();
                        request.setMessage(console.nextLine());
                        request.setRecipient("unknown");

                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("3")) {

                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время подключения к серверу: " + e.getMessage());
        }

        System.out.println("Отключились от сервера");
    }

    private static String createLoginRequest(String login) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(login);

        try {
            return objectMapper.writeValueAsString(loginRequest);
        } catch (JsonProcessingException e) {

        }
    }

    private static boolean checkLoginResponse(String loginResponse) {
        try {
            LoginResponse resp = objectMapper.reader().readValue(loginResponse, LoginResponse.class);
            return resp.isConnected();
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON: " + e.getMessage());
            return false;
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(Duration.ofMinutes(5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


