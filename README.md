# Car Pooling Database System

This project implements a car pooling database system using Java and SQL. It provides functionalities for managing users (participants, passengers, and drivers), cars, trips, and bookings.  The system uses an H2 in-memory database for data persistence.

## Features

*   **User Management:**  Handles participants, passengers (with membership status), and drivers (with ratings).
*   **Car and Trip Management:**  Manages car details (CarID, PIN, color, brand) and trip information (TripID, CarID, date, departure, destination, available seats).
*   **Booking System:**  Allows passengers to book trips with a booking status (e.g., Confirmed).
*   **Data Retrieval and Analysis:** Implements various SQL queries to retrieve and analyze data, including:
    *   Finding participants who are both drivers and passengers.
    *   Identifying drivers without cars and passengers without bookings.
    *   Calculating average driver ratings for each destination city.
    *   Finding trips departing from a specific city on a specific date.
    *   And many more (see the code for a complete list).
*   **Data Manipulation:** Includes functionalities for creating and dropping tables, inserting data into tables, and updating driver ratings.
*   **Database Interaction:** Uses JDBC for database connectivity, prepared statements for secure data handling, and batch processing for efficient data insertion.

## Technologies Used

*   Java
*   SQL
*   JDBC
*   H2 Database (in-memory)
