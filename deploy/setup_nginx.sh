#!/bin/bash
yes | cp -rf paste.panteleevya.ru /etc/nginx/sites-available/paste.panteleevya.ru
sudo ln -s /etc/nginx/sites-available/paste.panteleevya.ru /etc/nginx/sites-enabled/
yes | cp -rf minio.panteleevya.ru /etc/nginx/sites-available/minio.panteleevya.ru
sudo ln -s /etc/nginx/sites-available/minio.panteleevya.ru /etc/nginx/sites-enabled/
nginx -t
service nginx reload
