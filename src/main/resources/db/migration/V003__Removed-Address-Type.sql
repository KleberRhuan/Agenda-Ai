BEGIN;

-- Step 1: Add new address columns (nullable initially)
ALTER TABLE clinic_schema.clinics
    ADD COLUMN IF NOT EXISTS street   VARCHAR(255),
    ADD COLUMN IF NOT EXISTS number   VARCHAR(10),
    ADD COLUMN IF NOT EXISTS city     VARCHAR(100),
    ADD COLUMN IF NOT EXISTS state    VARCHAR(50),
    ADD COLUMN IF NOT EXISTS zip_code VARCHAR(20);

ALTER TABLE user_schema.users
    ADD COLUMN IF NOT EXISTS street   VARCHAR(255),
    ADD COLUMN IF NOT EXISTS number   VARCHAR(10),
    ADD COLUMN IF NOT EXISTS city     VARCHAR(100),
    ADD COLUMN IF NOT EXISTS state    VARCHAR(50),
    ADD COLUMN IF NOT EXISTS zip_code VARCHAR(20);

-- Step 2: Migrate data from composite type to new columns
UPDATE clinic_schema.clinics
SET street   = (address).street,
    number   = (address).number,
    city     = (address).city,
    state    = (address).state,
    zip_code = (address).zip_code
WHERE address IS NOT NULL;

UPDATE user_schema.users
SET street   = (address).street,
    number   = (address).number,
    city     = (address).city,
    state    = (address).state,
    zip_code = (address).zip_code
WHERE address IS NOT NULL;

-- Step 3: Set NOT NULL constraints after populating data
ALTER TABLE clinic_schema.clinics
    ALTER COLUMN street SET NOT NULL,
ALTER COLUMN number SET NOT NULL,
    ALTER COLUMN city SET NOT NULL,
    ALTER COLUMN state SET NOT NULL,
    ALTER COLUMN zip_code SET NOT NULL;

ALTER TABLE user_schema.users
    ALTER COLUMN street SET NOT NULL,
ALTER COLUMN number SET NOT NULL,
    ALTER COLUMN city SET NOT NULL,
    ALTER COLUMN state SET NOT NULL,
    ALTER COLUMN zip_code SET NOT NULL;

-- Step 4: Drop old composite column
ALTER TABLE clinic_schema.clinics DROP COLUMN IF EXISTS address;
ALTER TABLE user_schema.users DROP COLUMN IF EXISTS address;

-- Step 5: Drop the composite type if it's no longer needed
DROP TYPE IF EXISTS config_schema.address_types CASCADE;

COMMIT;