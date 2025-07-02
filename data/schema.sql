DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS users_task CASCADE;
DROP TABLE IF EXISTS task CASCADE;
DROP TABLE IF EXISTS users CASCADE;

DROP TYPE IF EXISTS priority CASCADE;
DROP TYPE IF EXISTS status CASCADE;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
	is_deleted BOOLEAN NOT NULL
);

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    priority VARCHAR(25) NOT NULL,
    status VARCHAR(25) NOT NULL,
    due_date DATE NOT NULL,
    description TEXT NOT NULL,
	is_deleted BOOLEAN NOT NULL

);
ALTER TABLE task ADD CONSTRAINT ck_task_priority CHECK (priority IN ('VERY_HIGH', 'HIGH', 'MEDIUM', 'LOW'));
ALTER TABLE task ADD CONSTRAINT ck_task_status CHECK (status IN ('TO_DO', 'IN_PROGRESS', 'PAUSED', 'DONE'));


CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY,
    content TEXT,
    created_at TIMESTAMP,
    task_id BIGINT REFERENCES task(id),
    users_id BIGINT REFERENCES users(id),
	is_deleted BOOLEAN NOT NULL
);

CREATE TABLE users_task (
    users_id BIGINT REFERENCES users(id),
    task_id BIGINT REFERENCES task(id),
    PRIMARY KEY (users_id, task_id)
);