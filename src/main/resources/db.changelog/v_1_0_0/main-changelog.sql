create table users(
                      user_id bigserial primary key not null,
                      user_name varchar(15) not null,
                      user_email varchar(20) not null,
                      user_role varchar(10) not null,
                      created_at timestamp with time zone not null default current_timestamp,
                      update_at timestamp with time zone,
                      last_activity_at timestamp with time zone,
                      constraint valid_email check (user_email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

create table tasks(
    task_id bigserial primary key not null,
    task_name varchar(200) not null,
    task_description varchar(250) not null,
    task_type varchar(10) not null,
    task_status varchar(10) not null,
    user_id int not null,
    created_at timestamp with time zone not null default current_timestamp,
    update_at timestamp with time zone,
    last_edit_at timestamp with time zone,
    constraint fk_user foreign key(user_id) references users(user_id) on delete cascade
);

create index idx_tasks_user_id on tasks(user_id);
create index idx_tasks_status on tasks(task_status);
create index idx_users_email on users(user_email);