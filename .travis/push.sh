#!/bin/sh

setup_git() {
  git config --global user.email "marcoveluscek@gmail.com"
  git config --global user.name "mvelusce"
}

commit_files() {
  git checkout master
  git add .
  git commit --message "Travis build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
  echo "https://mvelusce:$GH_TOKEN@github.com/mvelusce/recipe-book.git"
  git push -f https://mvelusce:$GH_TOKEN@github.com/mvelusce/recipe-book.git master
}

setup_git
commit_files
upload_files
