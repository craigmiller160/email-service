# email-service

A micro-service for sending emails from my infrastructure.

## Database Setup

This project makes little to no use of a database, however it does use the `oauth2-utils` library for security, which does use a database for storing refresh tokens. Therefore the DB schema for the `oauth2-utils` library needs to be setup for the `email_service_dev` and `email_service_prod` databases.

## Running in Dev

Execute the run script in the root of this project: `sh run.sh`.