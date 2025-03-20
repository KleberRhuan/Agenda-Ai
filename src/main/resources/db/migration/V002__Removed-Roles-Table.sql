drop table user_schema.roles cascade;
drop table user_schema.user_roles cascade;

create type config_schema.roles_type as enum ('PATIENT', 'RECEPTIONIST', 'PROFESSIONAL', 'ADMIN');
alter table user_schema.users add column role config_schema.roles_type not null default 'PATIENT';