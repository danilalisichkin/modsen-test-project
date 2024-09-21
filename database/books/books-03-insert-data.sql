\c books;

INSERT INTO public.books (author, description, genre, isbn, title) VALUES
    ('George Orwell', 'A dystopian novel set in a totalitarian society.', 'Dystopian', '9780451524935', '1984'),
    ('Harper Lee', 'A novel about racial injustice in the American South.', 'Fiction', '9780060935467', 'To Kill a Mockingbird'),
    ('J.K. Rowling', 'A young wizard attends a magical school and battles dark forces.', 'Fantasy', '9780747532743', 'Harry Potter and the Philosopher''s Stone'),
    ('J.R.R. Tolkien', 'An epic adventure in a fantastical world.', 'Fantasy', '9780261102385', 'The Lord of the Rings'),
    ('F. Scott Fitzgerald', 'A critique of the American Dream set in the Roaring Twenties.', 'Fiction', '9780743273565', 'The Great Gatsby'),
    ('Jane Austen', 'A classic novel exploring issues of class, marriage, and morality.', 'Romance', '9780141439518', 'Pride and Prejudice'),
    ('Mark Twain', 'A humorous and satirical novel about a young boy''s adventures.', 'Adventure', '9780486280615', 'The Adventures of Huckleberry Finn'),
    ('Leo Tolstoy', 'A historical novel depicting the impact of the Napoleonic Wars on Russian society.', 'Historical Fiction', '9781400079988', 'War and Peace'),
    ('Herman Melville', 'An epic tale of obsession and revenge.', 'Adventure', '9780142437247', 'Moby-Dick'),
    ('J.D. Salinger', 'A novel about teenage angst and alienation.', 'Fiction', '9780316769488', 'The Catcher in the Rye');

SELECT * FROM public.books;