-- PostgreSQL Database Setup for SpotNShop
-- Run these commands in PostgreSQL to set up the database

-- Create database
CREATE DATABASE spotnshop;

-- Create user
CREATE USER spotnshop_user WITH PASSWORD 'spotnshop_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE spotnshop TO spotnshop_user;

-- Connect to the database and grant schema privileges
\c spotnshop;
GRANT ALL ON SCHEMA public TO spotnshop_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO spotnshop_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO spotnshop_user;