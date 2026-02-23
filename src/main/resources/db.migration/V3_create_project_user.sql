create table user_project
(
    project_id INTEGER NOT NULL,
    user_id    INTEGER NOT NULL,
    role_in_project VARCHAR(20),
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (project_id, user_id),

    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

alter table project
add column owner_id integer;


alter table project
    add constraint fk_project_owner
        foreign key (owner_id)
            references users(id)
            on delete restrict;