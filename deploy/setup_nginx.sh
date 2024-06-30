#!/bin/bash
yes | cp -rf paste_nginx_conf /etc/nginx/sites-enabled/paste_nginx_conf
nginx -t
service nginx reload
