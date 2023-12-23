CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "language" (
    language_id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    language_name VARCHAR(50) NOT NULL,
    status_record CHAR
);

CREATE TABLE "user" (
    user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(20) UNIQUE NOT NULL,
    language_id UUID REFERENCES "language"(language_id) NOT NULL,
    role VARCHAR NOT NULL,
    status_record CHAR
);

CREATE TABLE "user_profile" (
    user_profile_id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    user_id UUID REFERENCES "user"(user_id),
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    status_record CHAR
);

CREATE TABLE "chat" (
    message_id UUID PRIMARY KEY DEFAULT uuid_generate_v4() NOT NULL,
    message TEXT,
    datetime TIMESTAMP,
    user_id UUID REFERENCES "user"(user_id),
    status_record CHAR
);