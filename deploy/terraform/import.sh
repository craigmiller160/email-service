#!/bin/sh

function import {
  terraform \
    import \
    -var="onepassword_token=$ONEPASSWORD_TOKEN"\
    "$1" "$2"
}

function plan {
  terraform plan \
    -var "onepassword_token=$ONEPASSWORD_TOKEN"
}

import "keycloak_openid_client.email_service_dev" "apps-dev/98ddff56-205d-45a9-931d-1afea35b109e"
import "keycloak_openid_client.email_service_prod" "apps-prod/e1b642e4-8fe6-4ba6-a7fc-05eb19fd9c86"

import "keycloak_role.email_service_access_role_dev" "apps-dev/6da86d0d-7442-442a-93c2-e20601f7262f"
import "keycloak_role.email_service_access_role_prod" "apps-prod/81db67ff-02e9-4529-8710-c35c841d7b6e"