@echo off
set PGPASSWORD=postgres

set SQL=CREATE TABLE IF NOT EXISTS users (^
    id UUID PRIMARY KEY,^
    first_name VARCHAR(255) NOT NULL,^
    email VARCHAR(255),^
    phone_number VARCHAR(255) NOT NULL,^
    birth_date DATE NOT NULL,^
    country VARCHAR(255) NOT NULL^
);

echo --------- SHARD 1 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5432 -U postgres -d shard1 -c "%SQL%"

echo --------- SHARD 2 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5433 -U postgres -d shard2 -c "%SQL%"

echo --------- SHARD 3 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5434 -U postgres -d shard3 -c "%SQL%"