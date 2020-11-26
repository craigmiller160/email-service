# email-service

A micro-service for sending emails from my infrastructure.

## Database Setup

This project makes little to no use of a database, however it does use the `oauth2-utils` library for security, which does use a database for storing refresh tokens. Therefore the DB schema for the `oauth2-utils` library needs to be setup for the `email_service_dev` and `email_service_prod` databases.

## Email Account Setup

A special email account, `craigmiller.emailer@gmail.com` has been setup for this application. A special application password has been created for it. It is essential that this password is not committed in Git. Therefore, it is provided another way.

An environment variable, `CRAIG_EMAILER_PASSWORD`, must be created. This variable will contain the password. This completely separates the password from the application.

For development, this variable should be created on the dev linux environment. For production, it needs to be stored in a Kubernetes secret.

## Running in Dev

Execute the run script in the root of this project: `sh run.sh`.