-- Clear old demo data
TRUNCATE TABLE tasks RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;

-- USERS

INSERT INTO users (
    created_at,
    email,
    name,
    password,
    role,
    updated_at
) VALUES
      (
          '2026-01-05 10:00:00',
          'admin@taskflow.com',
          'Admin User',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi5kM5G6v8l7M0q6jY5N5GQ9P5X6A8W',
          'ADMIN',
          '2026-06-01 09:00:00'
      ),
      (
          '2026-01-12 09:15:00',
          'john.smith@gmail.com',
          'John Smith',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi5kM5G6v8l7M0q6jY5N5GQ9P5X6A8W',
          'USER',
          '2026-05-20 10:00:00'
      ),
      (
          '2026-02-08 10:00:00',
          'emma.wilson@gmail.com',
          'Emma Wilson',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi5kM5G6v8l7M0q6jY5N5GQ9P5X6A8W',
          'USER',
          '2026-05-29 15:00:00'
      ),
      (
          '2026-03-01 09:00:00',
          'liam.brown@gmail.com',
          'Liam Brown',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi5kM5G6v8l7M0q6jY5N5GQ9P5X6A8W',
          'USER',
          '2026-05-27 11:00:00'
      ),
      (
          '2026-04-05 15:10:00',
          'olivia.jones@gmail.com',
          'Olivia Jones',
          '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi5kM5G6v8l7M0q6jY5N5GQ9P5X6A8W',
          'USER',
          '2026-05-22 16:00:00'
      );

-- TASKS

INSERT INTO tasks (
    created_at,
    description,
    due_date,
    priority,
    status,
    title,
    updated_at,
    user_id
) VALUES

      (
          '2026-04-01 10:00:00',
          'Review project requirements',
          '2026-04-05 18:00:00',
          'HIGH',
          'DONE',
          'Project Review',
          '2026-04-05 17:00:00',
          2
      ),

      (
          '2026-05-20 09:00:00',
          'Fix authentication bug',
          '2026-06-05 18:00:00',
          'HIGH',
          'IN_PROGRESS',
          'Bug Fix',
          '2026-06-01 12:00:00',
          3
      ),

      (
          '2026-05-25 11:00:00',
          'Prepare sprint planning',
          '2026-06-10 18:00:00',
          'MEDIUM',
          'TODO',
          'Sprint Planning',
          '2026-05-25 11:00:00',
          4
      ),

      (
          '2026-05-18 14:00:00',
          'Update dashboard UI',
          '2026-06-08 18:00:00',
          'MEDIUM',
          'IN_PROGRESS',
          'UI Update',
          '2026-06-01 15:00:00',
          5
      ),

      (
          '2026-04-10 09:00:00',
          'Write API documentation',
          '2026-04-20 18:00:00',
          'LOW',
          'DONE',
          'Documentation',
          '2026-04-19 16:00:00',
          2
      );