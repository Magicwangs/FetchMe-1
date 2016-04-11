#!/bin/bash
(uwsgi mysite.ini 2>&1 | tee media/log.txt)
