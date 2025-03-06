CREATE TABLE users (
                       id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       likes INT DEFAULT 0
);

CREATE TABLE skills (
                        id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        shape_name VARCHAR(255) NOT NULL
);

CREATE TABLE projects (
                          id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          likes INT DEFAULT 0
);

CREATE TABLE slices (
                        id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

CREATE TABLE comments (
                          id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                          parent_id UUID,
                          writer_id UUID NOT NULL,
                          content TEXT NOT NULL,
                          FOREIGN KEY (writer_id) REFERENCES users(id),
                          FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE
);

CREATE TABLE user_skills (
                             user_id UUID NOT NULL,
                             skill_id UUID NOT NULL,
                             PRIMARY KEY (user_id, skill_id),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (skill_id) REFERENCES skills(id)
);

CREATE TABLE user_projects (
                               user_id UUID NOT NULL,
                               project_id UUID NOT NULL,
                               PRIMARY KEY (user_id, project_id),
                               FOREIGN KEY (user_id) REFERENCES users(id),
                               FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE project_skills (
                                project_id UUID NOT NULL,
                                skill_id UUID NOT NULL,
                                PRIMARY KEY (project_id, skill_id),
                                FOREIGN KEY (project_id) REFERENCES projects(id),
                                FOREIGN KEY (skill_id) REFERENCES skills(id)
);

CREATE TABLE project_slices (
                                project_id UUID NOT NULL,
                                slice_id UUID NOT NULL,
                                PRIMARY KEY (project_id, slice_id),
                                FOREIGN KEY (project_id) REFERENCES projects(id),
                                FOREIGN KEY (slice_id) REFERENCES slices(id)
);

CREATE TABLE slice_skills (
                              slice_id UUID NOT NULL,
                              skill_id UUID NOT NULL,
                              PRIMARY KEY (slice_id, skill_id),
                              FOREIGN KEY (slice_id) REFERENCES slices(id),
                              FOREIGN KEY (skill_id) REFERENCES skills(id)
);

CREATE TABLE project_comments (
                                  project_id UUID NOT NULL,
                                  comment_id UUID NOT NULL,
                                  PRIMARY KEY (project_id, comment_id),
                                  FOREIGN KEY (project_id) REFERENCES projects(id),
                                  FOREIGN KEY (comment_id) REFERENCES comments(id)
);

CREATE TABLE slice_comments (
                                slice_id UUID NOT NULL,
                                comment_id UUID NOT NULL,
                                PRIMARY KEY (slice_id, comment_id),
                                FOREIGN KEY (slice_id) REFERENCES slices(id),
                                FOREIGN KEY (comment_id) REFERENCES comments(id)
);

-- Insert sample users
INSERT INTO users (id, name, last_name, email, password, likes) VALUES
                                                                    (RANDOM_UUID(), 'Alice', 'Doe', 'alice@example.com', 'password123', 10),
                                                                    (RANDOM_UUID(), 'Bob', 'Smith', 'bob@example.com', 'password123', 5);

-- Insert sample skills
INSERT INTO skills (id, name, shape_name) VALUES
                                              (RANDOM_UUID(), 'Java', 'Circle'),
                                              (RANDOM_UUID(), 'Spring Boot', 'Square');

-- Insert sample projects
INSERT INTO projects (id, name, likes) VALUES
                                           (RANDOM_UUID(), 'E-commerce Website', 15),
                                           (RANDOM_UUID(), 'Chat Application', 20);

-- Insert sample slices
INSERT INTO slices (id, name) VALUES
                                  (RANDOM_UUID(), 'Backend Development'),
                                  (RANDOM_UUID(), 'UI/UX Design');

-- Insert sample comments
INSERT INTO comments (id, parent_id, writer_id, content) VALUES
                                                             (RANDOM_UUID(), NULL, (SELECT id FROM users WHERE email='alice@example.com'), 'Great project!'),
                                                             (RANDOM_UUID(), NULL, (SELECT id FROM users WHERE email='bob@example.com'), 'Nice work!');

-- Associate users with skills
INSERT INTO user_skills (user_id, skill_id) VALUES
                                                ((SELECT id FROM users WHERE email='alice@example.com'), (SELECT id FROM skills WHERE name='Java')),
                                                ((SELECT id FROM users WHERE email='bob@example.com'), (SELECT id FROM skills WHERE name='Spring Boot'));

-- Associate users with projects
INSERT INTO user_projects (user_id, project_id) VALUES
                                                    ((SELECT id FROM users WHERE email='alice@example.com'), (SELECT id FROM projects WHERE name='E-commerce Website')),
                                                    ((SELECT id FROM users WHERE email='bob@example.com'), (SELECT id FROM projects WHERE name='Chat Application'));

-- Associate projects with skills
INSERT INTO project_skills (project_id, skill_id) VALUES
                                                      ((SELECT id FROM projects WHERE name='E-commerce Website'), (SELECT id FROM skills WHERE name='Java')),
                                                      ((SELECT id FROM projects WHERE name='Chat Application'), (SELECT id FROM skills WHERE name='Spring Boot'));

-- Associate projects with slices
INSERT INTO project_slices (project_id, slice_id) VALUES
                                                      ((SELECT id FROM projects WHERE name='E-commerce Website'), (SELECT id FROM slices WHERE name='Backend Development')),
                                                      ((SELECT id FROM projects WHERE name='Chat Application'), (SELECT id FROM slices WHERE name='UI/UX Design'));

-- Associate slices with skills
INSERT INTO slice_skills (slice_id, skill_id) VALUES
                                                  ((SELECT id FROM slices WHERE name='Backend Development'), (SELECT id FROM skills WHERE name='Java')),
                                                  ((SELECT id FROM slices WHERE name='UI/UX Design'), (SELECT id FROM skills WHERE name='Spring Boot'));

-- Associate comments with projects
INSERT INTO project_comments (project_id, comment_id) VALUES
                                                          ((SELECT id FROM projects WHERE name='E-commerce Website'), (SELECT id FROM comments WHERE content='Great project!')),
                                                          ((SELECT id FROM projects WHERE name='Chat Application'), (SELECT id FROM comments WHERE content='Nice work!'));
