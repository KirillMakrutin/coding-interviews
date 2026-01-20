# Preconditions
ONLINE DB service:
https://www.programiz.com/sql/online-compiler/

Script to clear the initial state:

    drop table Shippings;
    drop table Customers;
    drop table Orders;

# 1. Initial script
Script to create simple DB for storing time lap results of racers:

    CREATE TABLE Racer (
        id INT,
        name VARCHAR(100)
    );
    
    CREATE TABLE Lap_Time (
        id INT,
        racer_id INT,
        lap_number INT,
        lap_time NUMERIC(8, 2)
    );

## Task
What recommendations for these scripts you have?

# 2. Find the fastest lap
Run scripts to insert data:

	INSERT INTO Racer (id, name) 
	VALUES (1, 'Jerry'), (2, 'Bugs Bunny'), (3, 'Snoopy'), (4, 'Goofy'), (5, 'Mickey Mouse');

	INSERT INTO Lap_Time 
	VALUES 
		(1, 1, 1, 38.1), 
		(2, 1, 2, 32.1),
		(3, 1, 3, 31.1),
		(4, 2, 1, 37.2),
		(5, 2, 2, 36.2),
		(6, 2, 3, 35.2),
		(7, 3, 1, 39.3),
		(8, 3, 2, 33.3),
		(9, 3, 3, 32.3),
		(10, 4, 1, 38.4),
		(11, 4, 2, 32.4),
		(12, 4, 3, 34.4),
		(13, 5, 1, 35.5),
		(14, 5, 2, 36.5),
		(15, 5, 3, 37.5);

## Task 
Develop a script to find the fastest lap for each racer and their names

# 3. Find duplicates
Run script to add duplicate rows:

    INSERT INTO Lap_Time
    VALUES
        (16, 2, 1, 37.2),
        (17, 2, 2, 36.2),
        (18, 2, 3, 35.2);

## Task
Let's imagine that we have duplicate data due to a bug. 
The bug has been fixed, now it is required to develop a script that will find ids of these duplicate rows. 


# 4. Additional questions
- What would you do if you need to improve performance for some query?
