-- Suppression des tables si elles existent (ordre inverse des dépendances)
DROP TABLE IF EXISTS user_skills;
DROP TABLE IF EXISTS user_projects;
DROP TABLE IF EXISTS project_skills;
DROP TABLE IF EXISTS project_collaborators;
DROP TABLE IF EXISTS slice_skills;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS slices;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS skills;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS projects_liked_by;

-- Création des tables
CREATE TABLE app_user (
                          id VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          likes INT DEFAULT 0
);

CREATE TABLE skills (
                        id VARCHAR(36) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE projects (
                          id VARCHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

CREATE TABLE slices (
                        id VARCHAR(36) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        project_id VARCHAR(36),
                        likes INT DEFAULT 0,
                        FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                          id VARCHAR(36) PRIMARY KEY,
                          parent_id VARCHAR(36),
                          writer_id VARCHAR(36) NOT NULL,
                          content TEXT NOT NULL,
                          project_id VARCHAR(36),
                          FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE SET NULL,
                          FOREIGN KEY (writer_id) REFERENCES app_user(id) ON DELETE CASCADE,
                          FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE user_skills (
                             user_id VARCHAR(36),
                             skill_id VARCHAR(36),
                             PRIMARY KEY (user_id, skill_id),
                             FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
                             FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE TABLE user_projects (
                               user_id VARCHAR(36),
                               project_id VARCHAR(36),
                               PRIMARY KEY (user_id, project_id),
                               FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
                               FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE project_skills (
                                project_id VARCHAR(36),
                                skill_id VARCHAR(36),
                                PRIMARY KEY (project_id, skill_id),
                                FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE TABLE project_collaborators (
                                       project_id VARCHAR(36),
                                       user_id VARCHAR(36),
                                       PRIMARY KEY (project_id, user_id),
                                       FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                       FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE TABLE slice_skills (
                              slice_id VARCHAR(36),
                              skill_id VARCHAR(36),
                              PRIMARY KEY (slice_id, skill_id),
                              FOREIGN KEY (slice_id) REFERENCES slices(id) ON DELETE CASCADE,
                              FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE TABLE projects_liked_by (
                                   project_id VARCHAR(36),
                                   liked_by_id VARCHAR(36),
                                   PRIMARY KEY (project_id, liked_by_id),
                                   FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                   FOREIGN KEY (liked_by_id) REFERENCES app_user(id) ON DELETE CASCADE
);

-- Insertion des données
INSERT INTO app_user (id, name, last_name, email, password, likes)
VALUES
    ('1', 'John', 'Doe', 'john.doe@example.com', 'password123', 10),
    ('2', 'Jane', 'Smith', 'jane.smith@example.com', 'password456', 5),
    ('3', 'Alice', 'Johnson', 'alice.johnson@example.com', 'password789', 8);
INSERT INTO skills (id, name)
VALUES
    ('101', 'Java'),
    ('102', 'Spring Boot'),
    ('103', 'SQL'),
    ('104', 'JavaScript'),
    ('105', 'React');
INSERT INTO projects (id, name, description)
VALUES
    ('201', 'Project Alpha', 'A project to build a new web application.'),
    ('202', 'Project Beta', 'A project to develop a mobile app.'),
    ('203', 'Project Gamma', 'A project to create a data analysis tool.');
INSERT INTO slices (id, name, project_id, likes)
VALUES
    ('301', 'Frontend Development', '201', 3),
    ('302', 'Backend Development', '201', 5),
    ('303', 'UI/UX Design', '202', 2),
    ('304', 'Database Design', '203', 4);
INSERT INTO comments (id, parent_id, writer_id, content, project_id)
VALUES
    ('401', NULL, '1', 'Great project! Looking forward to collaborating.', '201'),
    ('402', NULL, '2', 'I have some questions about the backend architecture.', '201'),
    ('403', '402', '1', 'Sure, feel free to ask!', '201'),
    ('404', NULL, '3', 'This project is amazing!', '202');

INSERT INTO user_skills (user_id, skill_id)
VALUES
    ('1', '101'),
    ('1', '102'),
    ('2', '103'),
    ('2', '104'),
    ('3', '105');
INSERT INTO user_projects (user_id, project_id)
VALUES
    ('1', '201'),
    ('2', '201'),
    ('3', '202'),
    ('1', '203');

INSERT INTO project_skills (project_id, skill_id)
VALUES
    ('201', '101'),
    ('201', '102'),
    ('202', '104'),
    ('203', '103');
INSERT INTO project_collaborators (project_id, user_id)
VALUES
    ('201', '1'),
    ('201', '2'),
    ('202', '3'),
    ('203', '1');
INSERT INTO slice_skills (slice_id, skill_id)
VALUES
    ('301', '104'),
    ('302', '101'),
    ('303', '105'),
    ('304', '103');
INSERT INTO projects_liked_by (project_id, liked_by_id)
VALUES
    ('201', '1'),
    ('201', '2'),
    ('202', '3'),
    ('203', '1');