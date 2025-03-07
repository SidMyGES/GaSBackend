-- Création de la table des utilisateurs
CREATE TABLE app_user (
                          id VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          likes INT DEFAULT 0
);

-- Création de la table des projets
CREATE TABLE projects (
                          id VARCHAR(255) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

-- Table d'association pour les likes des projets
CREATE TABLE project_likes (
                               project_id VARCHAR(255),
                               user_id VARCHAR(255),
                               PRIMARY KEY (project_id, user_id),
                               FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

-- Table d'association pour les collaborateurs des projets
CREATE TABLE project_collaborators (
                                       project_id VARCHAR(255),
                                       user_id VARCHAR(255),
                                       PRIMARY KEY (project_id, user_id),
                                       FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                       FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

-- Création de la table des compétences
CREATE TABLE skills (
                        id VARCHAR(255) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

-- Table d'association entre projets et compétences utilisées
CREATE TABLE project_skills (
                                project_id VARCHAR(255),
                                skill_id VARCHAR(255),
                                PRIMARY KEY (project_id, skill_id),
                                FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                                FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Table d'association entre utilisateurs et compétences
CREATE TABLE user_skills (
                             user_id VARCHAR(255),
                             skill_id VARCHAR(255),
                             PRIMARY KEY (user_id, skill_id),
                             FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
                             FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Table d'association entre utilisateurs et projets
CREATE TABLE user_projects (
                               user_id VARCHAR(255),
                               project_id VARCHAR(255),
                               PRIMARY KEY (user_id, project_id),
                               FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
                               FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Création de la table des "slices"
CREATE TABLE slices (
                        id VARCHAR(255) PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        project_id VARCHAR(255),
                        likes INT DEFAULT 0,
                        FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- Table d'association entre slices et compétences
CREATE TABLE slice_skills (
                              slice_id VARCHAR(255),
                              skill_id VARCHAR(255),
                              PRIMARY KEY (slice_id, skill_id),
                              FOREIGN KEY (slice_id) REFERENCES slices(id) ON DELETE CASCADE,
                              FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Création de la table des commentaires
CREATE TABLE comments (
                          id VARCHAR(255) PRIMARY KEY,
                          content TEXT NOT NULL,
                          writer_id VARCHAR(255) NOT NULL,
                          parent_id VARCHAR(255),
                          project_id VARCHAR(255),
                          FOREIGN KEY (writer_id) REFERENCES app_user(id) ON DELETE CASCADE,
                          FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE,
                          FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE);

INSERT INTO app_user (id, name, last_name, email, password, likes) VALUES
                                                                       ('user1', 'Alice', 'Dupont', 'alice.dupont@example.com', 'password123', 5),
                                                                       ('user2', 'Bob', 'Martin', 'bob.martin@example.com', 'securepass', 10),
                                                                       ('user3', 'Charlie', 'Lemoine', 'charlie.lemoine@example.com', 'hashedpass', 3);

INSERT INTO projects (id, name, description) VALUES
                                                 ('proj1', 'Application de gestion des tâches', 'Une application pour gérer les tâches quotidiennes.'),
                                                 ('proj2', 'Site e-commerce', 'Une boutique en ligne avec paiement sécurisé.'),
                                                 ('proj3', 'Portfolio en ligne', 'Un portfolio interactif pour les développeurs.');


INSERT INTO project_likes (project_id, user_id) VALUES
                                                    ('proj1', 'user1'),
                                                    ('proj1', 'user2'),
                                                    ('proj2', 'user3'),
                                                    ('proj3', 'user1');


INSERT INTO project_collaborators (project_id, user_id) VALUES
                                                            ('proj1', 'user2'),
                                                            ('proj2', 'user3'),
                                                            ('proj3', 'user1'),
                                                            ('proj3', 'user2');


INSERT INTO skills (id, name) VALUES
                                  ('skill1', 'Java'),
                                  ('skill2', 'Spring Boot'),
                                  ('skill3', 'React'),
                                  ('skill4', 'Python'),
                                  ('skill5', 'Django');


INSERT INTO project_skills (project_id, skill_id) VALUES
                                                      ('proj1', 'skill1'),
                                                      ('proj1', 'skill2'),
                                                      ('proj2', 'skill3'),
                                                      ('proj2', 'skill4'),
                                                      ('proj3', 'skill5');


INSERT INTO user_skills (user_id, skill_id) VALUES
                                                ('user1', 'skill1'),
                                                ('user1', 'skill3'),
                                                ('user2', 'skill2'),
                                                ('user2', 'skill4'),
                                                ('user3', 'skill5');


INSERT INTO user_projects (user_id, project_id) VALUES
                                                    ('user1', 'proj1'),
                                                    ('user2', 'proj2'),
                                                    ('user3', 'proj3');



INSERT INTO slices (id, name, project_id, likes) VALUES
                                                     ('slice1', 'Frontend', 'proj1', 15),
                                                     ('slice2', 'Backend', 'proj1', 10),
                                                     ('slice3', 'Base de données', 'proj2', 8);


INSERT INTO slice_skills (slice_id, skill_id) VALUES
                                                  ('slice1', 'skill3'),
                                                  ('slice2', 'skill1'),
                                                  ('slice3', 'skill4');



INSERT INTO comments (id, content, writer_id, parent_id, project_id) VALUES
                                                                                   ('comment1', 'Super projet !', 'user1', NULL, 'proj1'),
                                                                                   ('comment2', 'J’aime beaucoup l’interface.', 'user2', 'comment1', 'proj1'),
                                                                                   ('comment3', 'Bon choix de technologies.', 'user3', NULL, 'proj2');


