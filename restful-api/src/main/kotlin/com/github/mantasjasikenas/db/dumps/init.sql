DO
$$
    DECLARE
        default_user_id uuid;
        admin_id        uuid;
    BEGIN
        default_user_id := 'd290f1ee-6c54-4b01-90e6-d701748f0851';
        admin_id := gen_random_uuid();

        create table users
        (
            id            uuid                                not null
                primary key,
            user_name     varchar(255)                        not null,
            email         varchar(255)                        not null,
            password      varchar(255)                        not null,
            force_relogin boolean   default false             not null,
            created_at    timestamp default CURRENT_TIMESTAMP not null
        );

        alter table users
            owner to postgres;

        create table projects
        (
            id          serial
                primary key,
            name        varchar(255)                        not null,
            description text                                not null,
            created_at  timestamp default CURRENT_TIMESTAMP not null,
            created_by  uuid                                not null
                constraint fk_projects_created_by__id
                    references users
                    on update restrict on delete cascade
        );

        alter table projects
            owner to postgres;

        create table sections
        (
            id         serial
                primary key,
            project_id integer                             not null
                constraint fk_sections_project_id__id
                    references projects
                    on update restrict on delete cascade,
            name       varchar(255)                        not null,
            created_at timestamp default CURRENT_TIMESTAMP not null,
            created_by uuid                                not null
                constraint fk_sections_created_by__id
                    references users
                    on update restrict on delete cascade
        );

        alter table sections
            owner to postgres;

        create table tasks
        (
            id           serial
                primary key,
            name         text                                not null,
            description  text                                not null,
            priority     varchar(10)                         not null,
            is_completed boolean   default false             not null,
            due_date     timestamp,
            created_at   timestamp default CURRENT_TIMESTAMP not null,
            created_by   uuid                                not null
                constraint fk_tasks_created_by__id
                    references users
                    on update restrict on delete cascade,
            section_id   integer                             not null
                constraint fk_tasks_section_id__id
                    references sections
                    on update restrict on delete cascade
        );

        alter table tasks
            owner to postgres;

        create table users_roles
        (
            id      serial
                primary key,
            user_id uuid    not null
                constraint fk_users_roles_user_id__id
                    references users
                    on update restrict on delete cascade,
            role_id integer not null
        );

        alter table users_roles
            owner to postgres;

-- Insert default users
        INSERT INTO users (id, user_name, email, password)
        VALUES (default_user_id, 'mantelis', 'mantelis@mantelis.tech', 'password'),
               (admin_id, 'admin', 'admin@admin.com', 'admin123');

-- Insert default roles
        INSERT INTO public.users_roles (user_id, role_id)
        VALUES (default_user_id, 1),
               (admin_id, 0);


-- -- Insert repository into projects table
        INSERT INTO public.projects (name, description, created_at, created_by)
        VALUES ('Website Redesign', 'Complete overhaul of the company website', CURRENT_TIMESTAMP,
                default_user_id),
               ('Mobile App Development', 'Develop a new mobile application for our services',
                CURRENT_TIMESTAMP, default_user_id),
               ('Marketing Campaign', 'Launch a new marketing campaign for the upcoming product',
                CURRENT_TIMESTAMP, default_user_id),
               ('Data Migration', 'Migrate repository from the old system to the new system',
                CURRENT_TIMESTAMP, default_user_id),
               ('Product Launch', 'Prepare for the launch of the new product line', CURRENT_TIMESTAMP,
                default_user_id);
        --
-- -- Insert repository into sections table
        INSERT INTO public.sections (project_id, name, created_by, created_at)
        VALUES (1, 'Design Phase', default_user_id, CURRENT_TIMESTAMP),
               (1, 'Development Phase', default_user_id, CURRENT_TIMESTAMP),
               (2, 'Requirement Gathering', default_user_id, CURRENT_TIMESTAMP),
               (2, 'Development', default_user_id, CURRENT_TIMESTAMP),
               (3, 'Planning', default_user_id, CURRENT_TIMESTAMP),
               (3, 'Execution', default_user_id, CURRENT_TIMESTAMP),
               (4, 'Data Backup', default_user_id, CURRENT_TIMESTAMP),
               (4, 'Data Transfer', default_user_id, CURRENT_TIMESTAMP),
               (5, 'Pre-launch Activities', default_user_id, CURRENT_TIMESTAMP),
               (5, 'Launch Day', default_user_id, CURRENT_TIMESTAMP);
        --
-- -- Insert repository into tasks table
        INSERT INTO public.tasks (name, description, priority, is_completed, due_date, created_by, section_id,
                                  created_at)
        VALUES ('Create Wireframes', 'Design wireframes for the new website', 'High', false, '2023-09-15 23:59:59',
                default_user_id, 1, CURRENT_TIMESTAMP),
               ('Develop Frontend', 'Implement the frontend design', 'Medium', false, '2023-11-30 23:59:59',
                default_user_id, 2, CURRENT_TIMESTAMP),
               ('Gather Requirements', 'Collect all necessary requirements for the app', 'High', false,
                '2023-09-15 23:59:59', default_user_id, 3, CURRENT_TIMESTAMP),
               ('Develop Backend', 'Implement the backend services', 'High', false, '2024-01-31 23:59:59',
                default_user_id, 4, CURRENT_TIMESTAMP),
               ('Create Marketing Plan', 'Draft a detailed marketing plan', 'Medium', false, '2023-08-31 23:59:59',
                default_user_id, 5, CURRENT_TIMESTAMP),
               ('Launch Campaign', 'Execute the marketing campaign', 'High', false, '2023-11-01 23:59:59',
                default_user_id, 6, CURRENT_TIMESTAMP),
               ('Backup Data', 'Create a backup of all existing repository', 'High', false, '2023-12-15 23:59:59',
                default_user_id, 7, CURRENT_TIMESTAMP),
               ('Transfer Data', 'Transfer repository to the new system', 'High', false, '2024-01-05 23:59:59',
                default_user_id, 8, CURRENT_TIMESTAMP),
               ('Prepare Marketing Materials', 'Create marketing materials for the launch', 'Medium', false,
                '2024-01-15 23:59:59', default_user_id, 9, CURRENT_TIMESTAMP),
               ('Launch Product', 'Officially launch the new product', 'High', false, '2024-02-28 23:59:59',
                default_user_id, 10, CURRENT_TIMESTAMP),
               ('Review Wireframes', 'Get feedback on wireframes from stakeholders', 'Medium', false,
                '2023-09-20 23:59:59', default_user_id, 1, CURRENT_TIMESTAMP),
               ('Test Frontend', 'Perform testing on the frontend implementation', 'High', false, '2023-12-10 23:59:59',
                default_user_id, 2, CURRENT_TIMESTAMP),
               ('Document Requirements', 'Document all gathered requirements', 'Medium', false, '2023-09-20 23:59:59',
                default_user_id, 3, CURRENT_TIMESTAMP),
               ('Test Backend', 'Perform testing on the backend services', 'High', false, '2024-02-15 23:59:59',
                default_user_id, 4, CURRENT_TIMESTAMP),
               ('Finalize Marketing Plan', 'Finalize the marketing plan with the team', 'Medium', false,
                '2023-09-10 23:59:59', default_user_id, 5, CURRENT_TIMESTAMP),
               ('Monitor Campaign', 'Monitor the progress of the marketing campaign', 'High', false,
                '2023-11-10 23:59:59', default_user_id, 6, CURRENT_TIMESTAMP),
               ('Verify Backup', 'Verify the integrity of the backup repository', 'High', false, '2023-12-20 23:59:59',
                default_user_id, 7, CURRENT_TIMESTAMP),
               ('Validate Data Transfer', 'Ensure all repository has been transferred correctly', 'High', false,
                '2024-01-08 23:59:59', default_user_id, 8, CURRENT_TIMESTAMP),
               ('Distribute Marketing Materials', 'Distribute marketing materials to the team', 'Medium', false,
                '2024-01-20 23:59:59', default_user_id, 9, CURRENT_TIMESTAMP),
               ('Post-launch Review', 'Conduct a review after the product launch', 'High', false, '2024-03-01 23:59:59',
                default_user_id, 10, CURRENT_TIMESTAMP);
    END
$$;