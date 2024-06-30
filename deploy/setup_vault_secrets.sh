#!/bin/bash

mkdir -p secrets
echo $VAULT_ADDR > secrets/vault_addr.txt
echo $VAULT_TOKEN > secrets/vault_token.txt
