# email-service

A micro-service for sending emails from my infrastructure.

## Database Setup

This project makes little to no use of a database, however it does use the `oauth2-utils` library for security, which does use a database for storing refresh tokens. Therefore the DB schema for the `oauth2-utils` library needs to be setup for the `email_service_dev` and `email_service_prod` databases.

## Email Account Setup

A special email account, `craigmiller.emailer@gmail.com` has been setup for this application. A special application password has been created for it. It is essential that this password is not committed in Git. Therefore, it is provided another way.

An environment variable, `CRAIG_EMAILER_PASSWORD`, must be created. This variable will contain the password. This completely separates the password from the application.

For development, this variable should be created on the dev linux environment. For production, it needs to be stored in a Kubernetes secret. Assuming the password has been setup in that environment variable on the dev machine, use this command to create the Kubernetes secret for it:

```
kubectl create secret generic craig-emailer-password --from-literal=CRAIG_EMAILER_PASSWORD=$CRAIG_EMAILER_PASSWORD
```

## Running in Dev

The `sso-oauth2-server` must be running in Dev (locally) or else this app will fail, because it needs to download the JWKSet.

Execute the run script in the root of this project: `sh run.sh`.

## Adding TLS Cert to Other Project

To add the TLS Cert for this application to another project that needs it, simply copy over the files from the `keys` directory in project resources.