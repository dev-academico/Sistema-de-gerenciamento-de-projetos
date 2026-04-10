CREATE TYPE stage_status AS ENUM ('WAITING', 'DOING', 'DONE', 'CANCELED');

CREATE TABLE stage_templates (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    default_order INTEGER NOT NULL CHECK ( default_order > 0 ),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    is_default BOOLEAN NOT NULL DEFAULT TRUE,
    time_to_finish INTERVAL
);

create table stages (
    id serial primary key,
    project_id INTEGER NOT NULL REFERENCES project(id) ON DELETE CASCADE,

    name varchar(100) not null,
    description TEXT,
    status stage_status DEFAULT 'WAITING',
    default_order INTEGER CHECK ( default_order > 0 ),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    last_updated_by INTEGER,

    due_date TIMESTAMP NOT NULL ,
    completed_at TIMESTAMP,
    started_at TIMESTAMP,

    FOREIGN KEY (project_id) references project(id) ON DELETE CASCADE,
    FOREIGN KEY (last_updated_by) REFERENCES users(id) ON DELETE NO ACTION,

    CONSTRAINT check_dates CHECK (completed_at >= started_at)
);

create table stage_coordinators
(
    user_id integer,
    stage_id integer,
    assigned_at timestamp,

    foreign key (stage_id) references stages (id) on delete no action,
    foreign key (user_id) references users (id) on delete no action,

    primary key (user_id, stage_id)
);

CREATE OR REPLACE FUNCTION initialize_project_stages()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO stages (
        project_id,
        name,
        description,
        status,
        due_date,
        created_at
    )
    SELECT
        NEW.id,
        name,
        description,
        'WAITING',
        NEW.created_at + COALESCE(time_to_finish, INTERVAL '8 weeks'),
        CURRENT_TIMESTAMP
    FROM stage_templates
    WHERE is_default = TRUE
    ORDER BY default_order;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Aplicando o gatilho na sua tabela de projetos
CREATE TRIGGER trg_setup_project_stages
    AFTER INSERT ON project
    FOR EACH ROW
EXECUTE FUNCTION initialize_project_stages();

ALTER TABLE project ADD COLUMN due_date TIMESTAMP;

-- create table Issues (
--     id serial primary key,
--     name varchar(100) not null,
--     status varchar(20),
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--
--     comment varchar(255)
-- )

