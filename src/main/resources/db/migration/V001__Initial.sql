BEGIN;

CREATE SCHEMA IF NOT EXISTS config_schema;
CREATE EXTENSION IF NOT EXISTS citext WITH SCHEMA config_schema;

CREATE TABLE IF NOT EXISTS config_schema.insurances (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT insurances_pk PRIMARY KEY (id),
    CONSTRAINT insurances_name_uq UNIQUE (name)
);

CREATE TYPE config_schema.address_types AS (
    street  VARCHAR(255),
    number  VARCHAR(10),
    city    VARCHAR(100),
    state   VARCHAR(50),
    zip_code VARCHAR(20)
);

CREATE SCHEMA IF NOT EXISTS user_schema;

CREATE TABLE IF NOT EXISTS user_schema.roles (
    id SERIAL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    CONSTRAINT roles_pk PRIMARY KEY (id),
    CONSTRAINT roles_name_uq UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS user_schema.users (
    id BIGSERIAL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email config_schema.citext NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    date_of_birth DATE NOT NULL,
    address config_schema.address_types,
    phone VARCHAR(20),
    insurance_id BIGINT,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_email_uq UNIQUE (email),
    CONSTRAINT users_cpf_chk CHECK (LENGTH(cpf) = 11),
    CONSTRAINT users_insurance_id_fk FOREIGN KEY (insurance_id) REFERENCES config_schema.insurances(id)
);

CREATE TABLE IF NOT EXISTS user_schema.user_roles (
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_roles_user_id_fk FOREIGN KEY (user_id) REFERENCES user_schema.users(id),
    CONSTRAINT user_roles_role_id_fk FOREIGN KEY (role_id) REFERENCES user_schema.roles(id)
);

CREATE SCHEMA IF NOT EXISTS clinic_schema;

CREATE TABLE IF NOT EXISTS clinic_schema.clinics (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL,
    address config_schema.address_types,
    phone VARCHAR(20),
    owner_id BIGINT NOT NULL,
    CONSTRAINT clinics_pk PRIMARY KEY (id),
    CONSTRAINT clinics_owner_id_fk FOREIGN KEY (owner_id) REFERENCES user_schema.users(id)
);

CREATE SCHEMA IF NOT EXISTS appointment_schema;
CREATE TYPE appointment_schema.appointment_status AS ENUM ('SCHEDULED', 'CONFIRMED', 'CANCELED');

CREATE TABLE IF NOT EXISTS appointment_schema.appointments (
    id BIGSERIAL,
    date_time TIMESTAMPTZ NOT NULL,
    description TEXT NOT NULL,
    patient_id BIGINT NOT NULL,
    clinic_id BIGINT NOT NULL,
    CONSTRAINT appointments_pk PRIMARY KEY (id),
    CONSTRAINT appointments_patient_id_fk FOREIGN KEY (patient_id) REFERENCES user_schema.users(id),
    CONSTRAINT appointments_clinic_id_fk FOREIGN KEY (clinic_id) REFERENCES clinic_schema.clinics(id)
);

COMMIT;