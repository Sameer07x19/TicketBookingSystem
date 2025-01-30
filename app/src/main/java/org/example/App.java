/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utils.UserServiceUtil;

import java.io.IOException;
import java.util.*;

public class App {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to my Ticket Booking System!");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;

        try{
            userBookingService = new UserBookingService();
        }
        catch (IOException ex){
            System.out.println("There is something wrong!" + ex.getMessage());
            return;
        }

        while(option!=7){
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option){
                case 1:
                    String signUpName;
                    while(true){
                        System.out.println("Enter your username to signup: ");
                        signUpName = scanner.nextLine();
                        if(signUpName.contains(" ")){
                            System.out.println("Username cannot contain spaces!");
                        }
                        else{
                            break;
                        }
                    }
                    System.out.println("Enter your password: ");
                    String signUpPass = scanner.nextLine();
                    User userToSignup = new User(signUpName, signUpPass, UserServiceUtil.hashPassword(signUpPass), new ArrayList<>(), UUID.randomUUID().toString());
                    try{
                        boolean userDuplicate = userBookingService.signUp(userToSignup);
                        if(userDuplicate){
                            System.out.println("Sign up successful!");
                        }
                        else{
                            break;
                        }
                    }catch (IOException ex){
                        System.out.println("Can SignUp User!");
                    }
                    break;
                case 2:
                    System.out.println("Enter the username to login: ");
                    String username;
                    while(true){
                        username = scanner.nextLine();
                        if(username.contains(" ")){
                            System.out.println("Username cannot contain spaces!");
                        }
                        else{
                            break;
                        }
                    }
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();

                    User signInUser = new User(username,password,UserServiceUtil.hashPassword(password),
                            new ArrayList<>(),UUID.randomUUID().toString());

                    try{
                        UserBookingService userBookingTempUser = new UserBookingService(signInUser);
                        if(userBookingTempUser.loginUser()){
                            System.out.println("Login successful! Welcome " + username);
                            userBookingService = userBookingTempUser;
                        }
                        else{
                            System.out.println("Login failed!");
                        }
                    }catch (IOException ex){
                        System.out.println("There is something wrong!");
                    }
                    break;
                case 3:
                    try{
                        userBookingService.fetchBookings();
                        System.out.println("Bookings fetched successfully!");
                    }catch (Exception ex){
                        System.out.println("There is something wrong!");
                    }
                    break;
                case 4:
                    System.out.println("Enter your source destination: ");
                    String source = scanner.nextLine();
                    System.out.println("Enter your destination destination: ");
                    String destination = scanner.nextLine();
//                    List<Train> trains = userBookingService.getTrains(source,destination);
                    break;
            }
        }


    }
}

