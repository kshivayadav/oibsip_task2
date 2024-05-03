import java.util.*;
import java.sql.*;

public class Task1 {
    private static final int mini = 1000;
    private static final int maxi = 9999;

    public static class userlevel { // =
        private String username;
        private String password;

       Scanner sc = new Scanner(System.in);

        public String getUsername() {
            System.out.println("Enter Username:");
            username = sc.next();
            return username;
        }

        public String getPassword() {
            System.out.println("Enter Password");
            password = sc.next();
            return password;
        }
    }

    public static class reservId {
        private int passengerID;
        private String passengerName;
        private String trainnum;
        private String classtype;
        private String journeyDate;
        private String from;
        private String to;

        Scanner sc = new Scanner(System.in);

        public int getPassengerID() {
            Random random = new Random();
            passengerID = random.nextInt(maxi) + mini;
            return passengerID;
        }

        public String getPassengerName() {
            System.out.println("Enter passenger name");
            passengerName = sc.next();
            return passengerName;
        }

        public String getTrainnum() {
            System.out.println("Enter the Train number");
            trainnum = sc.next();
            return trainnum;
        }

        public String getClasstype() {
            System.out.println("Enter the class type");
            classtype = sc.next();
            return classtype;
        }

        public String getJourneyDate() {
            System.out.println("Enter the Journey date as 'YYYY-MM-DD' format");
            journeyDate = sc.next();
            return journeyDate;
        }

        public String getFrom() {
            System.out.println("Enter the Starting place");
            from = sc.next();
            return from;
        }

        public String getTo() {
            System.out.println("Enter the Destination");
            to = sc.next();
            return to;
        }
    }
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Task1.userlevel u1 = new Task1.userlevel();
            String username = u1.getUsername();
            String password = u1.getPassword();

            String url = "jdbc:mysql://localhost:3306/reservation";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    System.out.println("User Connection Granted.\n");
                    while (true) {
                        String InsertQuery = "insert into reservations values (?, ?, ?, ?, ?, ?, ?)";
                        String DeleteQuery = "DELETE FROM reservations WHERE pnr_number = ?";
                        String ShowQuery = "Select * from reservations";

                        System.out.println("Enter the choice : ");
                        System.out.println("1. Insert Record.\n");
                        System.out.println("2. Delete Record.\n");
                        System.out.println("3. Show All Records.\n");
                        System.out.println("4. Exit.\n");
                        int choice = sc.nextInt();

                        if (choice == 1) {

                            Task1.reservId p1 = new Task1.reservId();
                            int pnr_number = p1.getPassengerID();
                            String passengerName = p1.getPassengerName();
                            String trainNumber = p1.getTrainnum();
                            String classType = p1.getClasstype();
                            String journeyDate = p1.getJourneyDate();
                            String getfrom = p1.getFrom();
                            String getto = p1.getTo();

                            try (PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery)) {
                                preparedStatement.setInt(1, pnr_number);
                                preparedStatement.setString(2, passengerName);
                                preparedStatement.setString(3, trainNumber);
                                preparedStatement.setString(4, classType);
                                preparedStatement.setString(5, journeyDate);
                                preparedStatement.setString(6, getfrom);
                                preparedStatement.setString(7, getto);

                                int rowsAffected = preparedStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Record added successfully.");
                                } else {
                                    System.out.println("No records were added.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }

                        } else if (choice == 2) {
                            System.out.println("Enter the PNR number to delete the record : ");
                            int pnrNumber = sc.nextInt();
                            try (PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery)) {
                                preparedStatement.setInt(1, pnrNumber);
                                int rowsAffected = preparedStatement.executeUpdate();

                                if (rowsAffected > 0) {
                                    System.out.println("Record deleted successfully.");
                                } else {
                                    System.out.println("No records were deleted.");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                        } else if (choice == 3) {
                            try (PreparedStatement preparedStatement = connection.prepareStatement(ShowQuery);
                                 ResultSet resultSet = preparedStatement.executeQuery()) {
                                System.out.println("\nAll records printing.\n");
                                while (resultSet.next()) {
                                    String pnrNumber = resultSet.getString("pnr_number");
                                    String passengerName = resultSet.getString("passenger_name");
                                    String trainNumber = resultSet.getString("train_number");
                                    String classType = resultSet.getString("class_type");
                                    String journeyDate = resultSet.getString("journey_date");
                                    String fromLocation = resultSet.getString("from_location");
                                    String toLocation = resultSet.getString("to_location");

                                    System.out.println("Reservation ID: " + pnrNumber);
                                    System.out.println("Passenger Name: " + passengerName);
                                    System.out.println("Train Number: " + trainNumber);
                                    System.out.println("Class Type: " + classType);
                                    System.out.println("Journey Date: " + journeyDate);
                                    System.out.println("From Location: " + fromLocation);
                                    System.out.println("To Location: " + toLocation);
                                    System.out.println("--------------");
                                }
                            } catch (SQLException e) {
                                System.err.println("SQLException: " + e.getMessage());
                            }
                        } else if (choice == 4) {
                            System.out.println("Exiting the program.\n");
                            break;
                        } else {
                            System.out.println("Invalid Choice Entered.\n");
                        }
                    }

                } catch (SQLException e) {
                    System.err.println("SQLException: " + e.getMessage());
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error loading JDBC driver: " + e.getMessage());
            }

            sc.close();
        }
}