create table driver_requests(
  ID uuid PRIMARY KEY default UUID_GENERATE_V4()::UUID,
  FIRST_NAME VARCHAR(100) not null,
  LAST_NAME VARCHAR(100) not null,
  EMAIL VARCHAR(100) UNIQUE not null,
  ADDRESS JSON not null,
  ID_PAPERS JSONB not null,
  ROUTES VARCHAR(100),
  PLATE_NB VARCHAR(100),
  DATE_CREATED TIMESTAMP DEFAULT current_timestamp
);
