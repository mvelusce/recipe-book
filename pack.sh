#!/usr/bin/env bash

rm -f recipe-book.zip

zip recipe-book.zip \
    recipe-book.exe \
    artifacts/web-app/*
