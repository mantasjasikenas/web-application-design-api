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

