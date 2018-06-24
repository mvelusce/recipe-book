#!/bin/sh

setup_git() {
  git config --global user.email "marcoveluscek@gmail.com"
  git config --global user.name "mvelusce"
}

commit_website_files() {
  git checkout master
  git add .
  git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
  git push "https://${GH_TOKEN}@github.com:mvelusce/recipe-book.git" master
}

setup_git
commit_website_files
upload_files
