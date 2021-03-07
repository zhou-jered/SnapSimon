!/bin/bash


msg="#"

if [ $# -gt 0 ]
then
  msg=$1
fi

git add .
git commit -m "$msg"
git push origin master