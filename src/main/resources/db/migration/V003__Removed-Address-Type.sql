ALTER TABLE clinic_schema.clinics
    ADD COLUMN street   VARCHAR(255) not null,
    ADD COLUMN number   VARCHAR(10) not null, 
    ADD COLUMN city     VARCHAR(100) not null,
    ADD COLUMN state    VARCHAR(50) not null, 
    ADD COLUMN zip_code VARCHAR(20) not null; 

ALTER TABLE user_schema.users
    ADD COLUMN street   VARCHAR(255) not null,
    ADD COLUMN number   VARCHAR(10) not null,
    ADD COLUMN city     VARCHAR(100) not null,
    ADD COLUMN state    VARCHAR(50) not null,
    ADD COLUMN zip_code VARCHAR(20) not null;


UPDATE clinic_schema.clinics
SET street   = (address).street,
    number   = (address).number,
    city     = (address).city,
    state    = (address).state,
    zip_code = (address).zip_code;

UPDATE user_schema.users
SET street   = (address).street,
    number   = (address).number,
    city     = (address).city,
    state    = (address).state,
    zip_code = (address).zip_code;

ALTER TABLE clinic_schema.clinics DROP COLUMN address;
ALTER TABLE user_schema.users DROP COLUMN address;

DROP TYPE IF EXISTS config_schema.address_types CASCADE;