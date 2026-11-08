INSERT INTO tb_author (name) VALUES ('Jane Austen');
INSERT INTO tb_author (name) VALUES ('J. R. R. Tolkien');
INSERT INTO tb_author (name) VALUES ('Robert C. Martin');
INSERT INTO tb_author (name) VALUES ('Yuval Noah Harari');
INSERT INTO tb_author (name) VALUES ('Stephen Hawking');

INSERT INTO tb_category (name) VALUES ('Romance');
INSERT INTO tb_category (name) VALUES ('Fantasia');
INSERT INTO tb_category (name) VALUES ('Tecnologia');
INSERT INTO tb_category (name) VALUES ('História');
INSERT INTO tb_category (name) VALUES ('Ciência');

INSERT INTO tb_book (title, isbn, publication_year, available_quantity, category_id, author_id, version) VALUES ('Pride and Prejudice', '9780141040349', 2009, 4, 1, 1, 0);
INSERT INTO tb_book (title, isbn, publication_year, available_quantity, category_id, author_id, version) VALUES ('The Hobbit', '9780547928227', 2012, 4, 2, 2, 0);
INSERT INTO tb_book (title, isbn, publication_year, available_quantity, category_id, author_id, version) VALUES ('Clean Code: A Handbook of Agile Software Craftsmanship', '9780132350884', 2008, 5, 3, 3, 0);
INSERT INTO tb_book (title, isbn, publication_year, available_quantity, category_id, author_id, version) VALUES ('Sapiens: A Brief History of Humankind', '9780062316097', 2015, 5, 4, 4, 0);
INSERT INTO tb_book (title, isbn, publication_year, available_quantity, category_id, author_id, version) VALUES ('A Brief History of Time', '9780553380163', 1998, 5, 5, 5, 0);

INSERT INTO tb_user (name, email, phone) VALUES ('Ana Silva', 'ana.silva@example.com', '11987654321');
INSERT INTO tb_user (name, email, phone) VALUES ('Bruno Santos', 'bruno.santos@example.com', '21976543210');
INSERT INTO tb_user (name, email, phone) VALUES ('Carla Oliveira', 'carla.oliveira@example.com', '31965432109');
INSERT INTO tb_user (name, email, phone) VALUES ('Diego Souza', 'diego.souza@example.com', '41954321098');
INSERT INTO tb_user (name, email, phone) VALUES ('Elisa Pereira', 'elisa.pereira@example.com', '51943210987');


INSERT INTO tb_loan (loan_date, return_expected_date, return_date, status, book_id, user_id, version) VALUES (DATEADD('DAY', -5, LOCALTIMESTAMP), DATEADD('DAY', 25, CURRENT_DATE), NULL, 'ACTIVE', 1, 1, 0);
INSERT INTO tb_loan (loan_date, return_expected_date, return_date, status, book_id, user_id, version) VALUES (DATEADD('DAY', -10, LOCALTIMESTAMP), DATEADD('DAY', 20, CURRENT_DATE), NULL, 'ACTIVE', 2, 2, 0);
INSERT INTO tb_loan (loan_date, return_expected_date, return_date, status, book_id, user_id, version) VALUES (DATEADD('DAY', -40, LOCALTIMESTAMP), DATEADD('DAY', -10, CURRENT_DATE), DATEADD('DAY', -15, LOCALTIMESTAMP), 'RETURNED', 3, 3, 0);
INSERT INTO tb_loan (loan_date, return_expected_date, return_date, status, book_id, user_id, version) VALUES (DATEADD('DAY', -35, LOCALTIMESTAMP), DATEADD('DAY', -5, CURRENT_DATE), DATEADD('DAY', -10, LOCALTIMESTAMP), 'RETURNED', 4, 4, 0);
INSERT INTO tb_loan (loan_date, return_expected_date, return_date, status, book_id, user_id, version) VALUES (DATEADD('DAY', -2, LOCALTIMESTAMP), DATEADD('DAY', 28, CURRENT_DATE), NULL, 'CANCELLED', 5, 5, 0);
