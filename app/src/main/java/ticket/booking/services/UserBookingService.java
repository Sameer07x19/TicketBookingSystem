package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private List<User> userList;

    private final ObjectMapper objectMapper;

    private final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadUsers();
        this.user = user;
    }
    public UserBookingService() throws IOException{
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadUsers();
    }
    private void loadUsers() throws IOException{
        userList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream()
                .filter(user1 -> user1.getUsername().equals(user.getUsername())
                && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())).findFirst();
//        System.out.println(foundUser);
        return foundUser.isPresent();
    }

    public boolean signUp(User user) throws IOException{
        try{
            Optional<User> foundUser = userList.stream().filter(user1 -> {
                return user1.getUsername().equals(user.getUsername());
            }).findFirst();

            if (foundUser.isPresent()) {
                // If a user with the same username exists,this will print an error message
                System.out.println("Username already taken!");
                return false;
            }

            userList.add(user);
            saveUserListToFile();
        }catch (Exception ex){
            System.out.println("saving user list to file failed " + ex.getMessage());
            return false;
        }
        return true;
    }

    private void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBookings(){
        user.printTickets();
        System.out.println("Fetching your bookings");
    }

//    public boolean cancelBooking(String ticketId) throws IOException{
//        if (ticketId == null || ticketId.isEmpty()) {
//            System.out.println("Ticket ID cannot be null or empty.");
//            return Boolean.FALSE;
//        }
//        boolean isRemoved =  user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId) );
//        if(isRemoved) {
//            saveUserListToFile();
//            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
//            return true;
//        }else{
//            System.out.println("No ticket found with ID " + ticketId);
//            return false;
//        }
//    }
//
//    public List<Train> getTrains (String source, String destination) throws IOException {
//        try{
//            TrainService trainService = new TrainService();
//            return trainService.searchTrains(source,destination);
//        }catch (Exception ex){
//            System.out.println("There is something wrong!");
//            return null;
//        }
//    }

}
