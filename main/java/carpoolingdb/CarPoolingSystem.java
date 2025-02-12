package ceng.ceng351.carpoolingdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarPoolingSystem implements ICarPoolingSystem {

    private static String url = "jdbc:h2:mem:carpoolingdb;DB_CLOSE_DELAY=-1"; // In-memory database
    private static String user = "";          // H2 default username
    private static String password = "";        // H2 default password

    private Connection connection;

    public void initialize(Connection connection) {
        this.connection = connection;
    }

    //Given: getAllDrivers()
    //Testing 5.16: All Drivers after Updating the Ratings
    @Override
    public Driver[] getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        
        String query = "SELECT PIN, rating FROM Drivers ORDER BY PIN ASC;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int PIN = rs.getInt("PIN");
                double rating = rs.getDouble("rating");

                // Create a Driver object with only PIN and rating
                Driver driver = new Driver(PIN, rating);
                drivers.add(driver);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        
        return drivers.toArray(new Driver[0]); 
    }

    
    //5.1 Task 1 Create tables
    @Override
    public int createTables() {
        int tableCount = 6;

        

        
        String createTable1 =
                "CREATE TABLE Participants (" +
                        "    PIN INT PRIMARY KEY," +
                        "    p_name TEXT," +
                        "    age INT" +
                        ")";

        String createTable2 =
                "CREATE TABLE Passengers (" +
                        "    PIN INT PRIMARY KEY," +
                        "    membership_status TEXT," +
                        "    FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                        ")";

        String createTable3 =
                "CREATE TABLE Drivers (" +
                        "    PIN INT PRIMARY KEY," +
                        "    rating DOUBLE," +
                        "    FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                        ")";

        String createTable4 =
                "CREATE TABLE Cars (" +
                        "    CarID INT PRIMARY KEY," +
                        "    PIN INT," +
                        "    color TEXT," +
                        "    brand TEXT," +
                        "    FOREIGN KEY (PIN) REFERENCES Drivers(PIN)" +
                        ")";

        String createTable5 =
                "CREATE TABLE Trips (" +
                        "    TripID INT PRIMARY KEY," +
                        "    CarID INT," +
                        "    date DATE," +
                        "    departure TEXT," +
                        "    destination TEXT," +
                        "    num_seats_available INT," +
                        "    FOREIGN KEY (CarID) REFERENCES Cars(CarID)" +
                        ")";

        String createTable6 =
                "CREATE TABLE Bookings (" +
                        "    TripID INT," +
                        "    PIN INT," +
                        "    booking_status TEXT," +
                        "    FOREIGN KEY (TripID) REFERENCES Trips(TripID)," +
                        "    FOREIGN KEY (PIN) REFERENCES Passengers(PIN)," +
                        "    PRIMARY KEY (TripID, PIN)" +
                        ")";

        try {
            Statement statement = connection.createStatement();
            statement.addBatch(createTable1);
            statement.addBatch(createTable2);
            statement.addBatch(createTable3);
            statement.addBatch(createTable4);
            statement.addBatch(createTable5);
            statement.addBatch(createTable6);
            statement.executeBatch();
            return tableCount; // Number of tables created
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //5.17 Task 17 Drop tables
    @Override
    public int dropTables() {
        int tableCount = 0;
        
        

        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS Participants ";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS Passengers";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS  Drivers";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS Cars ";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS  Trips";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String query = "DROP TABLE IF EXISTS Bookings";
            statement.executeUpdate(query);
            statement.close();
            tableCount++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableCount;
    }
    
    
    //5.2 Task 2 Insert Participants
    @Override
    public int insertParticipants(Participant[] participants) {
        int rowsInserted = 0;

        

        String query = "INSERT INTO Participants (PIN, p_name, age) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Participant participant : participants) {
                preparedStatement.setInt(1, participant.getPIN());
                preparedStatement.setString(2, participant.getP_name());
                preparedStatement.setInt(3, participant.getAge());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowsInserted;
    }

    
    //5.2 Task 2 Insert Passengers
    @Override
    public int insertPassengers(Passenger[] passengers) {
        int rowsInserted = 0;

        

        String query = "INSERT INTO Passengers (PIN, membership_status) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Passenger passenger : passengers) {
                preparedStatement.setInt(1, passenger.getPIN());
                preparedStatement.setString(2, passenger.getMembership_status());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowsInserted;
    }


    //5.2 Task 2 Insert Drivers
    @Override
    public int insertDrivers(Driver[] drivers) {
        int rowsInserted = 0;

        

        String query = "INSERT INTO Drivers (PIN, rating) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Driver driver : drivers) {
                preparedStatement.setInt(1, driver.getPIN());
                preparedStatement.setDouble(2, driver.getRating());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowsInserted;
    }

    
    //5.2 Task 2 Insert Cars
    @Override
    public int insertCars(Car[] cars) {
        int rowsInserted = 0;
        
        

        String query = "INSERT INTO Cars (CarID, PIN, color, brand) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Car car : cars) {
                preparedStatement.setInt(1, car.getCarID());
                preparedStatement.setInt(2, car.getPIN());
                preparedStatement.setString(3, car.getColor());
                preparedStatement.setString(4, car.getBrand());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsInserted;
    }


    //5.2 Task 2 Insert Trips
    @Override
    public int insertTrips(Trip[] trips) {
        int rowsInserted = 0;

        

        String query = "INSERT INTO Trips (TripID, CarID, date, departure, destination, num_seats_available) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Trip trip : trips) {
                preparedStatement.setInt(1, trip.getTripID());
                preparedStatement.setInt(2, trip.getCarID());
                preparedStatement.setString(3, trip.getDate());
                preparedStatement.setString(4, trip.getDeparture());
                preparedStatement.setString(5, trip.getDestination());
                preparedStatement.setInt(6, trip.getNum_seats_available());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rowsInserted;
    }

    //5.2 Task 2 Insert Bookings
    @Override
    public int insertBookings(Booking[] bookings) {
        int rowsInserted = 0;
        
        

        String query = "INSERT INTO Bookings (TripID, PIN, booking_status) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Booking booking : bookings) {
                preparedStatement.setInt(1, booking.getTripID());
                preparedStatement.setInt(2, booking.getPIN());
                preparedStatement.setString(3, booking.getBooking_status());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();
            for (int count : result) {
                rowsInserted += (count >= 0) ? 1 : 0;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsInserted;
    }

    
    //5.3 Task 3 Find all participants who are recorded as both drivers and passengers
    @Override
    public Participant[] getBothPassengersAndDrivers() {
    	
        
        List<Participant> participantList = new ArrayList<>();
        String query = "SELECT P.PIN, P.p_name, P.age\n" +
                "FROM Participants P, Passengers Pa, Drivers D\n" +
                "WHERE P.PIN=Pa.PIN AND P.PIN=D.PIN\n" +
                "ORDER BY P.PIN ASC";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pId = resultSet.getInt("PIN");
                String pName = resultSet.getString("p_name");
                int pAge = resultSet.getInt("age");

                Participant participant = new Participant(pId, pName, pAge);
                participantList.add(participant);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
    	return participantList.toArray(new Participant[0]);
    }

 
    //5.4 Task 4 Find the PINs, names, ages, and ratings of drivers who do not own any cars
    @Override
    public QueryResult.DriverPINNameAgeRating[] getDriversWithNoCars() {
    	
        
        List<QueryResult.DriverPINNameAgeRating> list = new ArrayList<>();
        String query = "SELECT P.PIN, P.p_name, P.age, D.rating\n" +
                "FROM Participants P, Drivers D\n" +
                "WHERE P.PIN=D.PIN AND NOT EXISTS (\n" +
                "SELECT *\n" +
                "FROM Cars C\n" +
                "WHERE C.PIN=D.PIN)\n" +
                "ORDER BY P.PIN ASC";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pId = resultSet.getInt("PIN");
                String pName = resultSet.getString("p_name");
                int pAge = resultSet.getInt("age");
                double pRating = resultSet.getDouble("rating");

                QueryResult.DriverPINNameAgeRating x = new QueryResult.DriverPINNameAgeRating(pId, pName, pAge, pRating);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	return list.toArray(new QueryResult.DriverPINNameAgeRating[0]);
    }
 
    
    //5.5 Task 5 Delete Drivers who do not own any cars
    @Override
    public int deleteDriversWithNoCars() {
        int rowsDeleted = 0;

        
        String query = "DELETE FROM Drivers\n" +
                "WHERE PIN NOT IN (SELECT PIN FROM Cars)";

        try{
            Statement statement = connection.createStatement();

            rowsDeleted = statement.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return rowsDeleted;  
    }

    
    //5.6 Task 6 Find all cars that are not taken part in any trips
    @Override
    public Car[] getCarsWithNoTrips() {
    	
        
        List<Car> carList = new ArrayList<>();
        String query = "SELECT C.CarID, C.PIN, C.color, C.brand\n" +
                "FROM Cars C\n" +
                "WHERE C.CarID NOT IN (SELECT T.CarID FROM Trips T)\n" +
                "ORDER BY C.CarID ASC";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int carId = resultSet.getInt("CarID");
                int pin = resultSet.getInt("PIN");
                String color = resultSet.getString("color");
                String brand = resultSet.getString("brand");

                Car car = new Car(carId, pin, color, brand);
                carList.add(car);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return carList.toArray(new Car[0]);
    }
    
    
    //5.7 Task 7 Find all passengers who didn't book any trips
    @Override
    public Passenger[] getPassengersWithNoBooks() {
    	
        
        List<Passenger> passengerList = new ArrayList<>();
        String query = "SELECT P.PIN, P.membership_status\n" +
                "FROM Passengers P\n" +
                "WHERE P.PIN NOT IN (SELECT DISTINCT B.PIN FROM Bookings B)\n" +
                "ORDER BY P.PIN ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pin = resultSet.getInt("PIN");
                String status = resultSet.getString("membership_status");

                Passenger passenger = new Passenger(pin, status);
                passengerList.add(passenger);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return passengerList.toArray(new Passenger[0]);
    }


    //5.8 Task 8 Find all trips that depart from the specified city to specified destination city on specific date
    @Override
    public Trip[] getTripsFromToCitiesOnSpecificDate(String departure, String destination, String date) {
        
    	
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT T.TripID, T.CarID, T.date, T.departure, T.destination, T.num_seats_available\n" +
                "FROM Trips T\n" +
                "WHERE T.departure = ? AND T.destination = ? AND T.date = ?\n" +
                "ORDER BY T.TripID ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, departure);
            preparedStatement.setString(2, destination);
            preparedStatement.setString(3, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int tripId = resultSet.getInt("TripID");
                int carId = resultSet.getInt("CarID");
                String d = resultSet.getString("date");
                String depar = resultSet.getString("departure");
                String dest = resultSet.getString("destination");
                int nseats = resultSet.getInt("num_seats_available");

                Trip trip = new Trip(tripId, carId, d, depar, dest, nseats);
                trips.add(trip);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return trips.toArray(new Trip[0]);
    }


    //5.9 Task 9 Find the PINs, names, ages, and membership_status of passengers who have bookings on all trips destined at a particular city
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengersWithBookingsToAllTripsForCity(String city) {
        
    	
        List<QueryResult.PassengerPINNameAgeMembershipStatus> list = new ArrayList<>();
        String query = "SELECT P.PIN, P.p_name, P.age, Pa.membership_status\n" +
                "FROM Participants P, Passengers Pa\n" +
                "WHERE P.PIN=Pa.PIN AND NOT EXISTS\n" +
                    "(SELECT T.TripID FROM Trips T WHERE T.destination = ? \n" +
                    "EXCEPT \n" +
                    "SELECT B.TripID FROM Bookings B WHERE B.PIN = P.PIN)\n" +
                "ORDER BY P.PIN ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pin = resultSet.getInt("PIN");
                String name = resultSet.getString("p_name");
                int age = resultSet.getInt("age");
                String status = resultSet.getString("membership_status");

                QueryResult.PassengerPINNameAgeMembershipStatus x = new QueryResult.PassengerPINNameAgeMembershipStatus(pin, name, age, status);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return list.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }

    
    //5.10 Task 10 For a given driver PIN, find the CarIDs that the driver owns and were booked at most twice.    
    @Override
    public Integer[] getDriverCarsWithAtMost2Bookings(int driverPIN) {
        
    	
        List<Integer> list = new ArrayList<>();

        String query = "SELECT T.CarID FROM Trips T, Cars C, Bookings B\n" +
                "WHERE T.CarID=C.CarID AND T.TripID=B.TripID AND C.PIN = ? \n" +
                "GROUP BY T.CarID\n" +
                "HAVING COUNT(*) < 3";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, driverPIN);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int carId = resultSet.getInt("CarID");

                Integer integer = new Integer(carId);
                list.add(integer);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list.toArray(new Integer[0]);  // Return the list as an array
    }


    //5.11 Task 11 Find the average age of passengers with "Confirmed" bookings (i.e., booking_status is ”Confirmed”) on trips departing from a given city and within a specified date range
    @Override
    public Double getAvgAgeOfPassengersDepartFromCityBetweenTwoDates(String city, String start_date, String end_date) {
        Double averageAge = null;
        
    	
        String query = "SELECT AVG(Par.age) AS AverageAge\n" +
                "FROM Passengers P, Participants Par, Bookings B, Trips T\n" +
                "WHERE P.PIN=Par.PIN AND B.PIN=P.PIN AND B.TripID=T.TripID\n" +
                "AND B.booking_status='Confirmed' AND T.departure = ? AND T.date>=? AND T.date<=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, start_date);
            preparedStatement.setString(3, end_date);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                averageAge=resultSet.getDouble("AverageAge");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return averageAge;
    }


    //5.12 Task 12 Find Passengers in a Given Trip.
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengerInGivenTrip(int TripID) {
        
    	
        List<QueryResult.PassengerPINNameAgeMembershipStatus> list = new ArrayList<>();
        String query = "SELECT P.PIN, Par.p_name, Par.age, P.membership_status\n" +
                "FROM Passengers P, Participants Par, Bookings B\n" +
                "WHERE P.PIN=Par.PIN AND B.PIN=P.PIN AND B.TripID=?\n" +
                "ORDER BY P.PIN ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, TripID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pin = resultSet.getInt("PIN");
                String name = resultSet.getString("p_name");
                int age = resultSet.getInt("age");
                String status = resultSet.getString("membership_status");

                QueryResult.PassengerPINNameAgeMembershipStatus x = new QueryResult.PassengerPINNameAgeMembershipStatus(pin, name, age, status);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return list.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }


    //5.13 Task 13 Find Drivers’ Scores
    @Override
    public QueryResult.DriverScoreRatingNumberOfBookingsPIN[] getDriversScores() {
        
    	
        List<QueryResult.DriverScoreRatingNumberOfBookingsPIN> list = new ArrayList<>();
        String query = "SELECT D.PIN, COUNT(*) AS NumberOfBookings, D.rating, D.rating * COUNT(T.TripID) AS DriverScore\n" +
                "FROM Drivers D, Cars C, Trips T, Bookings B\n" +
                "WHERE D.PIN=C.PIN AND T.CarID=C.CarID AND B.TripID=T.TripID\n" +
                "GROUP BY D.PIN\n" +
                "ORDER BY DriverScore DESC, D.PIN ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int pin = resultSet.getInt("PIN");
                double rating = resultSet.getDouble("rating");
                int number_of_bookings = resultSet.getInt("NumberOfBookings");
                double driver_score = resultSet.getDouble("DriverScore");

                QueryResult.DriverScoreRatingNumberOfBookingsPIN x = new QueryResult.DriverScoreRatingNumberOfBookingsPIN(driver_score, rating, number_of_bookings, pin);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return list.toArray(new QueryResult.DriverScoreRatingNumberOfBookingsPIN[0]);
    }

    
    //5.14 Task 14 Find average ratings of drivers who have trips destined to each city
    @Override
    public QueryResult.CityAndAverageDriverRating[] getDriversAverageRatingsToEachDestinatedCity() {
        
    	
        List<QueryResult.CityAndAverageDriverRating> list = new ArrayList<>();

        String query = "SELECT T.destination, AVG(D.rating) AS average\n" +
                "FROM Trips T, Cars C, Drivers D\n" +
                "WHERE T.CarID=C.CarID AND D.PIN=C.PIN\n" +
                "GROUP BY T.destination";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String city = resultSet.getString("destination");
                Double rating = resultSet.getDouble("average");

                QueryResult.CityAndAverageDriverRating x = new QueryResult.CityAndAverageDriverRating(city, rating);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return list.toArray(new QueryResult.CityAndAverageDriverRating[0]);
    }


    //5.15 Task 15 Find total number of bookings of passengers for each membership status
    @Override
    public QueryResult.MembershipStatusAndTotalBookings[] getTotalBookingsEachMembershipStatus() {
        
    	
        List<QueryResult.MembershipStatusAndTotalBookings> list = new ArrayList<>();
        String query = "SELECT P.membership_status, COUNT(B.TripID) AS total\n" +
                "FROM Passengers P, Bookings B\n" +
                "WHERE B.PIN=P.PIN\n" +
                "GROUP BY P.membership_status\n";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String status = resultSet.getString("membership_status");
                int total_number_of_bookings = resultSet.getInt("total");

                QueryResult.MembershipStatusAndTotalBookings x = new QueryResult.MembershipStatusAndTotalBookings(status, total_number_of_bookings);
                list.add(x);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return list.toArray(new QueryResult.MembershipStatusAndTotalBookings[0]);
    }

    
    //5.16 Task 16 For the drivers' ratings, if rating is smaller than 2.0 or equal to 2.0, update the rating by adding 0.5.
    @Override
    public int updateDriverRatings() {
        int rowsUpdated = 0;
        
    	

        String query = "UPDATE Drivers D\n" +
                "SET D.rating = D.rating + 0.5 \n" +
                "WHERE D.rating <= 2.0";

        try{
            Statement statement = connection.createStatement();

            rowsUpdated = statement.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    	
        return rowsUpdated;
    }
    

}
