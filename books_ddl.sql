/*
Project: Books Inventory Management with JWT Authentication
Author: Mark Bahr
*/


-- CREATE __ TABLE
-- CREATE SEQUENCE FOR ___ TABLE


/* ---------------------------------------------------
            ROLE Table and Data
-----------------------------------------------------*/
CREATE TABLE roles(
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(20)
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

/* ---------------------------------------------------
            USERS Table and Data
-----------------------------------------------------*/
CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(30) UNIQUE,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(100)
);

INSERT INTO users (username, email, password) VALUES ('johnsmith', 'js@test.com', 'Password1');

/* ---------------------------------------------------
            ENUM ROLE Table and Data
-----------------------------------------------------*/
CREATE TABLE user_roles(
    user_id INTEGER,
    role_id INTEGER,
    -- constraint name follows convention fk_<source_table>_<target_table>_<column>
    CONSTRAINT fk_user_roles_users_user_id FOREIGN KEY (user_id) 
        REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_roles_role_id FOREIGN KEY (role_id)
        REFERENCES roles (role_id) ON DELETE CASCADE
);


/* ---------------------------------------------------
            Books Table and Data
-----------------------------------------------------*/

DROP TABLE books;

-- CREATE BOOKS TABLE
CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    book_name VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(12, 2),
    cost NUMERIC(12, 2)
);

-- COPY data from books csv into Books table
COPY books
FROM 'C:\Users\Public\data\christian_children_books.csv'
WITH (FORMAT CSV, HEADER);


-- Here's some JSON data to use for requests
    {
        "bookId": 1,
        "bookName": "Little House on the Prairie",
        "author": "Laura Ingalls Wilder",
        "description": "Pioneer life on the American frontier.",
        "price": 12.99,
        "cost": 6.5
    },
    {
        "bookId": 2,
        "bookName": "Little Women",
        "author": "Louisa May Alcott",
        "description": "Four sisters growing up during the Civil War.",
        "price": 10.99,
        "cost": 5.25
    },
    {
        "bookId": 3,
        "bookName": "The Lion, the Witch and the Wardrobe",
        "author": "C.S. Lewis",
        "description": "Children enter a magical land and fight evil.",
        "price": 9.99,
        "cost": 4.8
    },


-- List of books
books = [
    ("Little House on the Prairie", "Laura Ingalls Wilder", "Pioneer life on the American frontier.", 12.99, 6.50),
    ("Little Women", "Louisa May Alcott", "Four sisters growing up during the Civil War.", 10.99, 5.25),
    ("The Lion, the Witch and the Wardrobe", "C.S. Lewis", "Children enter a magical land and fight evil.", 9.99, 4.80),
    ("Prince Caspian", "C.S. Lewis", "Return to Narnia to help restore the true king.", 9.99, 4.80),
    ("The Horse and His Boy", "C.S. Lewis", "A boy escapes slavery and finds purpose in Narnia.", 9.99, 4.80),
    ("The Magician's Nephew", "C.S. Lewis", "The beginning of the world of Narnia.", 9.99, 4.80),
    ("The Voyage of the Dawn Treader", "C.S. Lewis", "A sea adventure in the world of Narnia.", 9.99, 4.80),
    ("The Silver Chair", "C.S. Lewis", "A quest to rescue a lost prince in Narnia.", 9.99, 4.80),
    ("The Last Battle", "C.S. Lewis", "Final battle between good and evil in Narnia.", 9.99, 4.80),
    ("Winnie the Pooh", "A.A. Milne", "Gentle tales of a bear and his friends.", 8.99, 4.50),
    ("House at Pooh Corner", "A.A. Milne", "More adventures in the Hundred Acre Wood.", 8.99, 4.50),
    ("I Love You the Purplest", "Barbara M. Joosse", "A mother expresses love uniquely to her sons.", 6.99, 3.10),
    ("Waiting Is Not Easy", "Mo Willems", "Elephant and Piggie learn patience.", 7.99, 3.90),
    ("Because I Love You", "Max Lucado", "Parable about freedom and love.", 10.50, 5.00),
    ("You Are Special", "Max Lucado", "Story about self-worth and acceptance.", 10.50, 5.00),
    ("The Princess and the Kiss", "Jennie Bishop", "A story about purity and true love.", 11.00, 5.30),
    ("The Squire and the Scroll", "Jennie Bishop", "Adventure about virtue and integrity.", 11.00, 5.30),
    ("Pilgrim's Progress (Adapted for Children)", "John Bunyan", "Allegory of the Christian journey.", 10.00, 5.00),
    ("The Cooper Kids Adventure Series", "Frank Peretti", "Christian adventure and mystery series.", 7.99, 4.00),
    ("Tales of the Kingdom", "David & Karen Mains", "Allegorical stories of a great King.", 12.00, 6.00),
    ("Tales of the Resistance", "David & Karen Mains", "More allegorical tales of courage.", 12.00, 6.00),
    ("Tales of the Restoration", "David & Karen Mains", "Victory and healing in the kingdom.", 12.00, 6.00),
    ("The Jesus Storybook Bible", "Sally Lloyd-Jones", "Every story whispers His name.", 18.99, 9.50),
    ("God Gave Us You", "Lisa Tawn Bergren", "Parental love as a gift from God.", 9.50, 4.75),
    ("God Gave Us Heaven", "Lisa Tawn Bergren", "A child learns about heaven.", 9.50, 4.75),
    ("God Gave Us Christmas", "Lisa Tawn Bergren", "Understanding the meaning of Christmas.", 9.50, 4.75),
    ("The Chronicles of Narnia Boxed Set", "C.S. Lewis", "All Narnia books in one set.", 59.99, 30.00),
    ("The Biggest Story", "Kevin DeYoung", "How the snake crusher brings us back to Eden.", 14.99, 7.50),
    ("The Biggest Story ABC", "Kevin DeYoung", "Gospel for little ones, A to Z.", 12.99, 6.50),
    ("The Garden, the Curtain and the Cross", "Carl Laferton", "God's story of redemption for children.", 11.99, 6.00),
    ("Found: Psalm 23", "Sally Lloyd-Jones", "Psalm 23 adapted for young readers.", 8.99, 4.50),
    ("Loved: The Lord's Prayer", "Sally Lloyd-Jones", "The Lord's Prayer for children.", 8.99, 4.50),
    ("A Child’s First Bible", "Kenneth N. Taylor", "Bible stories for young readers.", 15.99, 8.00),
    ("The Beginner's Bible", "Zondervan", "Bible stories for toddlers and preschoolers.", 16.99, 8.50),
    ("Jesus Calling: Bible Storybook", "Sarah Young", "Devotional Bible stories for kids.", 17.99, 9.00),
    ("Noah’s Ark", "Peter Spier", "Beautifully illustrated story of the ark.", 11.99, 6.00),
    ("A is for Adam", "Ken Ham", "Biblical alphabet book.", 9.99, 5.00),
    ("D is for Dinosaur", "Ken Ham", "Creation-based dinosaur alphabet book.", 9.99, 5.00),
    ("In the Beginning", "Steve Turner", "Creation story in rhyme.", 8.99, 4.50),
    ("The Parable of the Lily", "Liz Curtis Higgs", "Story of Easter and grace.", 10.99, 5.50),
    ("The Pine Tree Parable", "Liz Curtis Higgs", "Story of giving at Christmas.", 10.99, 5.50),
    ("The Sunflower Parable", "Liz Curtis Higgs", "Learning to share and bless others.", 10.99, 5.50),
    ("The Pumpkin Patch Parable", "Liz Curtis Higgs", "Faith and harvest imagery.", 10.99, 5.50),
    ("My First Hands-On Bible", "Group Publishing", "Interactive Bible for young children.", 17.99, 9.00),
    ("Stories Jesus Told", "Nick Butterworth", "Parables retold for kids.", 8.99, 4.50),
    ("The Lost Sheep", "Nick Butterworth", "Retelling of Jesus' parable.", 6.99, 3.50),
    ("The Good Samaritan", "Nick Butterworth", "Lesson in kindness and compassion.", 6.99, 3.50),
    ("The Prodigal Son", "Nick Butterworth", "Story of grace and forgiveness.", 6.99, 3.50),
    ("Thoughts to Make Your Heart Sing", "Sally Lloyd-Jones", "Devotional thoughts for children.", 16.99, 8.50),
]