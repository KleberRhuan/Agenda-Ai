-- Desabilitar constraints nas tabelas
ALTER TABLE config_schema.insurances DISABLE TRIGGER ALL;
ALTER TABLE user_schema.users DISABLE TRIGGER ALL;
ALTER TABLE clinic_schema.clinics DISABLE TRIGGER ALL;
ALTER TABLE appointment_schema.appointments DISABLE TRIGGER ALL;

-- Apagar dados nas tabelas com FK primeiro
DELETE FROM appointment_schema.appointments;
DELETE FROM clinic_schema.clinics;
DELETE FROM user_schema.users;
DELETE FROM config_schema.insurances;

-- Resetar sequence para o valor inicial
ALTER SEQUENCE config_schema.insurances_id_seq RESTART WITH 1;
ALTER SEQUENCE user_schema.users_id_seq RESTART WITH 1;
ALTER SEQUENCE clinic_schema.clinics_id_seq RESTART WITH 1;
ALTER SEQUENCE appointment_schema.appointments_id_seq RESTART WITH 1;

-- Habilitar novamente as constraints
ALTER TABLE config_schema.insurances ENABLE TRIGGER ALL;
ALTER TABLE user_schema.users ENABLE TRIGGER ALL;
ALTER TABLE clinic_schema.clinics ENABLE TRIGGER ALL;
ALTER TABLE appointment_schema.appointments ENABLE TRIGGER ALL;

INSERT INTO config_schema.insurances (id, name)
VALUES
    (1, 'Unimed'),
    (2, 'Bradesco'),
    (3, 'Qualicorp');

INSERT INTO user_schema.users (id, first_name, last_name, email, cpf, date_of_birth, address, phone, insurance_id, role)
VALUES
    (1, 'Maria', 'Oliveira', 'maria@example.com', '12345678901', '1990-05-10',
     ROW('Rua A', '123', 'São Paulo', 'SP', '01000-000')::config_schema.address_types,
     '11999999999', 1, 'PATIENT'),

    (2, 'João', 'Silva', 'joao@example.com', '12345678902', '1985-09-20',
     ROW('Rua B', '456', 'Rio de Janeiro', 'RJ', '20000-000')::config_schema.address_types,
     '21999999999', 2, 'PROFESSIONAL'),

    (3, 'Ana', 'Souza', 'ana@example.com', '12345678903', '1995-04-15',
     ROW('Rua C', '789', 'Belo Horizonte', 'MG', '30000-000')::config_schema.address_types,
     '31999999999', 3, 'RECEPTIONIST');

INSERT INTO clinic_schema.clinics (id, name, address, phone, owner_id)
VALUES
    (1, 'Clínica Vida',
     ROW('Rua Saúde', '100', 'São Paulo', 'SP', '01000-100')::config_schema.address_types,
     '11988888888', 2);

INSERT INTO appointment_schema.appointments (id, date_time, description, patient_id, clinic_id)
VALUES
    (1, '2025-04-10 10:00:00-03', 'Consulta inicial', 1, 1),
    (2, '2025-04-11 11:00:00-03', 'Consulta de retorno', 1, 1);