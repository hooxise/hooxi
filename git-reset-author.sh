
#!/bin/sh

# Credits: http://stackoverflow.com/a/750191

git filter-branch -f --env-filter "
    GIT_AUTHOR_NAME='Swapnil Surve'
    GIT_AUTHOR_EMAIL='fusionswap@gmail.com'
    GIT_COMMITTER_NAME='Swapnil surve'
    GIT_COMMITTER_EMAIL='fusionswap@gmail.com'
  " HEAD
