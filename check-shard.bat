@echo off
set PGPASSWORD=postgres

echo --------- SHARD 1 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5432 -U postgres -d shard1 -c "SELECT * FROM users;"

echo --------- SHARD 2 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5433 -U postgres -d shard2 -c "SELECT * FROM users;"

echo --------- SHARD 3 ----------
"F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5434 -U postgres -d shard3 -c "SELECT * FROM users;"


@REM echo --------- Clearing all tables in SHARD 1 ----------
@REM "F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5432 -U postgres -d shard1 -c "TRUNCATE TABLE users;"
@REM
@REM echo --------- Clearing all tables in SHARD 2 ----------
@REM "F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5433 -U postgres -d shard2 -c "TRUNCATE TABLE users;"
@REM
@REM echo --------- Clearing all tables in SHARD 3 ----------
@REM "F:\postgresql-17.4-1-windows-x64-binaries\pgsql\bin\psql.exe" -h localhost -p 5434 -U postgres -d shard3 -c "TRUNCATE TABLE users;"
