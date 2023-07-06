CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table users(
  ID uuid PRIMARY KEY default UUID_GENERATE_V4()::UUID,
  NAME VARCHAR(100) not null ,
  EMAIL varchar(100) UNIQUE not null ,
  PASSWORD varchar(250)
)