# email-service

A micro-service for sending emails from my infrastructure.

## Email Account Setup

A special email account, `craigmiller.emailer@gmail.com` has been setup for this application. A special application password has been created for it. It is essential that this password is not committed in Git. Therefore, it is provided another way.

An environment variable, `CRAIG_EMAILER_PASSWORD`, must be created. This variable will contain the password. This completely separates the password from the application.

For development, this variable should be created on the dev machine. For production, it has been stored in 1Password and the heml deployment will automatically create the necessary secret for it.

## Running in Dev

Execute the run script in the root of this project: `sh run.sh`.

## Adding TLS Cert to Other Project

To add the TLS Cert for this application to another project that needs it, simply copy over the files from the `keys` directory in project resources.

## Terraform Environment Variables

For the Terraform script to run, the following environment variables must be present on the machine.

```
# The operator access token for communicating with 1Password
ONEPASSWORD_TOKEN=XXXXXXX
```