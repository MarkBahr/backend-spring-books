DROP TABLE books;

-- CREATE BOOKS TABLE
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(12, 2),
    cost NUMERIC(12, 2),

);

-- Add username calumn
ALTER TABLE books
ADD seller_user VARCHAR(30);

UPDATE books
set seller_user = 'user1';


-- COPY books
-- FROM 'C:\Users\Public\data\children_books_short.csv'
-- WITH (FORMAT CSV, HEADER);


INSERT INTO books (title, author, description, price, cost)
VALUES
('Little House on the Prairie', 'Laura Ingalls Wilder', 'Pioneer life on the American frontier.', 12.99, 6.50);

INSERT INTO books (title, author, description, price, cost)
VALUES
('Little Women', 'Louisa May Alcott', 'Four sisters growing up during the Civil War.', 10.99, 5.25);

INSERT INTO books (title, author, description, price, cost)
VALUES
('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', 'Children enter a magical land and fight evil.', 9.99, 4.80);

INSERT INTO books (title, author, description, price, cost)
VALUES
('Winnie the Pooh', 'A.A. Milne', 'Gentle tales of a bear and his friends.', 8.99, 4.50);

INSERT INTO books (title, author, description, price, cost)
VALUES
('The Good Samaritan', 'Nick Butterworth', 'Lesson in kindness and compassion.', 6.99, 3.50);

-- Here's some JSON data to use for requests
    -- Create new book
    {
        "title": "Waiting Is Not Easy",
        "author": "Mo Willems",
        "description": "Elephant and Piggie learn patience.",
        "price": 7.99,
        "cost": 3.90
    }

-- Response Body for GET request at endpoint /api/books/1 should look like this:
    {
        "bookId": 1,
        "title": "Little House on the Prairie",
        "author": "Laura Ingalls Wilder",
        "description": "Pioneer life on the American frontier.",
        "price": 12.99,
        "cost": 6.5
    }

-- for id 2 and 3
    {
        "bookId": 2,
        "title": "Little Women",
        "author": "Louisa May Alcott",
        "description": "Four sisters growing up during the Civil War.",
        "price": 10.99,
        "cost": 5.25
    },
    {
        "title": "Voyage of the Dawn Treader",
        "author": "C.S. Lewis",
        "description": "Lucy & Edmund help Prince Caspian on a sea voyage.",
        "price": 9.99,
        "cost": 4.8,
        "sellerUser": "testuser"
    }

-- Define additional books if needed
books = [
    ("House at Pooh Corner", "A.A. Milne", "More adventures in the Hundred Acre Wood.", 8.99, 4.50),
    ("I Love You the Purplest", "Barbara M. Joosse", "A mother expresses love uniquely to her sons.", 6.99, 3.10),
    ("Waiting Is Not Easy", "Mo Willems", "Elephant and Piggie learn patience.", 7.99, 3.90),
    ("Because I Love You", "Max Lucado", "Parable about freedom and love.", 10.50, 5.00),
    ("Pilgrim's Progress (Adapted for Children)", "John Bunyan", "Allegory of the Christian journey.", 10.00, 5.00),
    ("The Jesus Storybook Bible", "Sally Lloyd-Jones", "Every story whispers His name.", 18.99, 9.50)
    ("The Good Samaritan", "Nick Butterworth", "Lesson in kindness and compassion.", 6.99, 3.50),
]

-- Finidng Constraints
SELECT
    conname AS constraint_name,
    conrelid::regclass AS table_name,
    a.attname AS column_name,
    confrelid::regclass AS referenced_table
FROM pg_constraint
JOIN pg_attribute a ON a.attnum = ANY (conkey) AND a.attrelid = conrelid
WHERE confrelid::regclass::text IN ('books');

-- Finding sequences
SELECT pg_get_serial_sequence('your_table_name', 'your_column_name'); 

-- such as
SELECT pg_get_serial_sequence('books', 'book_id');

-- Find the current value of the sequence
SELECT currval('sequence_name');

-- How to reset the sequence current value
ALTER SEQUENCE sequence_name RESTART WITH new_value;
ALTER SEQUENCE public.books_book_id_seq RESTART WITH 6;