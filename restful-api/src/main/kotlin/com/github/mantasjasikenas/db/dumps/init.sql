create table public.projects
(
    id          serial
        primary key,
    name        varchar(255)                        not null,
    description text                                not null,
    due_date    timestamp,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    created_by  varchar(255)                        not null
);

alter table public.projects
    owner to postgres;

create table public.sections
(
    id         serial
        primary key,
    project_id integer                             not null
        constraint fk_sections_project_id__id
            references public.projects
            on update restrict on delete cascade,
    name       varchar(255)                        not null,
    due_date   timestamp,
    created_by varchar(255)                        not null,
    created_at timestamp default CURRENT_TIMESTAMP not null
);

alter table public.sections
    owner to postgres;

create table public.tasks
(
    id           serial
        primary key,
    name         text                                not null,
    description  text                                not null,
    priority     varchar(10)                         not null,
    is_completed boolean   default false             not null,
    due_date     timestamp,
    created_by   varchar(255)                        not null,
    section_id   integer                             not null
        constraint fk_tasks_section_id__id
            references public.sections
            on update restrict on delete cascade,
    created_at   timestamp default CURRENT_TIMESTAMP not null
);

alter table public.tasks
    owner to postgres;

-- Insert data into projects table
INSERT INTO public.projects (name, description, created_at, created_by)
VALUES ('Website Redesign', 'Complete overhaul of the company website', CURRENT_TIMESTAMP,
        'jdoe'),
       ('Mobile App Development', 'Develop a new mobile application for our services',
        CURRENT_TIMESTAMP, 'asmith'),
       ('Marketing Campaign', 'Launch a new marketing campaign for the upcoming product',
        CURRENT_TIMESTAMP, 'mjones'),
       ('Data Migration', 'Migrate data from the old system to the new system',
        CURRENT_TIMESTAMP, 'bwhite'),
       ('Product Launch', 'Prepare for the launch of the new product line', CURRENT_TIMESTAMP,
        'kgreen');

-- Insert data into sections table
INSERT INTO public.sections (project_id, name, created_by, created_at)
VALUES (1, 'Design Phase', 'jdoe', CURRENT_TIMESTAMP),
       (1, 'Development Phase', 'jdoe', CURRENT_TIMESTAMP),
       (2, 'Requirement Gathering', 'asmith', CURRENT_TIMESTAMP),
       (2, 'Development', 'asmith', CURRENT_TIMESTAMP),
       (3, 'Planning', 'mjones', CURRENT_TIMESTAMP),
       (3, 'Execution', 'mjones', CURRENT_TIMESTAMP),
       (4, 'Data Backup', 'bwhite', CURRENT_TIMESTAMP),
       (4, 'Data Transfer', 'bwhite', CURRENT_TIMESTAMP),
       (5, 'Pre-launch Activities', 'kgreen', CURRENT_TIMESTAMP),
       (5, 'Launch Day', 'kgreen', CURRENT_TIMESTAMP);

-- Insert data into tasks table
INSERT INTO public.tasks (name, description, priority, is_completed, due_date, created_by, section_id, created_at)
VALUES ('Create Wireframes', 'Design wireframes for the new website', 'High', false, '2023-09-15 23:59:59', 'jdoe', 1,
        CURRENT_TIMESTAMP),
       ('Develop Frontend', 'Implement the frontend design', 'Medium', false, '2023-11-30 23:59:59', 'jdoe', 2,
        CURRENT_TIMESTAMP),
       ('Gather Requirements', 'Collect all necessary requirements for the app', 'High', false, '2023-09-15 23:59:59',
        'asmith', 3, CURRENT_TIMESTAMP),
       ('Develop Backend', 'Implement the backend services', 'High', false, '2024-01-31 23:59:59', 'asmith', 4,
        CURRENT_TIMESTAMP),
       ('Create Marketing Plan', 'Draft a detailed marketing plan', 'Medium', false, '2023-08-31 23:59:59', 'mjones', 5,
        CURRENT_TIMESTAMP),
       ('Launch Campaign', 'Execute the marketing campaign', 'High', false, '2023-11-01 23:59:59', 'mjones', 6,
        CURRENT_TIMESTAMP),
       ('Backup Data', 'Create a backup of all existing data', 'High', false, '2023-12-15 23:59:59', 'bwhite', 7,
        CURRENT_TIMESTAMP),
       ('Transfer Data', 'Transfer data to the new system', 'High', false, '2024-01-05 23:59:59', 'bwhite', 8,
        CURRENT_TIMESTAMP),
       ('Prepare Marketing Materials', 'Create marketing materials for the launch', 'Medium', false,
        '2024-01-15 23:59:59', 'kgreen', 9, CURRENT_TIMESTAMP),
       ('Launch Product', 'Officially launch the new product', 'High', false, '2024-02-28 23:59:59', 'kgreen', 10,
        CURRENT_TIMESTAMP),
       ('Review Wireframes', 'Get feedback on wireframes from stakeholders', 'Medium', false, '2023-09-20 23:59:59',
        'jdoe', 1, CURRENT_TIMESTAMP),
       ('Test Frontend', 'Perform testing on the frontend implementation', 'High', false, '2023-12-10 23:59:59', 'jdoe',
        2, CURRENT_TIMESTAMP),
       ('Document Requirements', 'Document all gathered requirements', 'Medium', false, '2023-09-20 23:59:59', 'asmith',
        3, CURRENT_TIMESTAMP),
       ('Test Backend', 'Perform testing on the backend services', 'High', false, '2024-02-15 23:59:59', 'asmith', 4,
        CURRENT_TIMESTAMP),
       ('Finalize Marketing Plan', 'Finalize the marketing plan with the team', 'Medium', false, '2023-09-10 23:59:59',
        'mjones', 5, CURRENT_TIMESTAMP),
       ('Monitor Campaign', 'Monitor the progress of the marketing campaign', 'High', false, '2023-11-10 23:59:59',
        'mjones', 6, CURRENT_TIMESTAMP),
       ('Verify Backup', 'Verify the integrity of the backup data', 'High', false, '2023-12-20 23:59:59', 'bwhite', 7,
        CURRENT_TIMESTAMP),
       ('Validate Data Transfer', 'Ensure all data has been transferred correctly', 'High', false,
        '2024-01-08 23:59:59', 'bwhite', 8, CURRENT_TIMESTAMP),
       ('Distribute Marketing Materials', 'Distribute marketing materials to the team', 'Medium', false,
        '2024-01-20 23:59:59', 'kgreen', 9, CURRENT_TIMESTAMP),
       ('Post-launch Review', 'Conduct a review after the product launch', 'High', false, '2024-03-01 23:59:59',
        'kgreen', 10, CURRENT_TIMESTAMP);