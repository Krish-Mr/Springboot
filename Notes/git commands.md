# Git Commands Cheat Sheet (User Story Format)

## 1. Getting Started

| Command | Description | Example |
|---------|-------------|---------|
| `git clone <remote repo url>` | Clone a remote repository to your local machine. | `git clone https://github.com/user/repo.git` |
| `git remote -v` | Show remote repository URLs. | `git remote -v` |

## 2. Making Changes

| Command | Description | Example |
|---------|-------------|---------|
| `git status` | Show the working directory status. | `git status` |
| `git diff` | Show changes between commits, commit and working tree, etc. | `git diff` |
| `git add .` | Stage all changes in the current directory for the next commit. | `git add .` |
| `git reset HEAD <file>` | Unstage a file. | `git reset HEAD index.html` |
| `git checkout -- <filename>` | Discard changes in a specific file. | `git checkout -- index.html` |
| `git checkout -- .` | Discard changes in all files in the working directory. | `git checkout -- .` |

## 3. Committing and Pushing

| Command | Description | Example |
|---------|-------------|---------|
| `git commit -m "commit message"` | Commit staged changes with a message. | `git commit -m "Initial commit"` |
| `git push` | Push committed changes to the remote repository. | `git push origin main` |
| `git pull origin main` | Fetch and merge changes from the remote repository. | `git pull origin main` |

## 4. Branching and Merging

| Command | Description | Example |
|---------|-------------|---------|
| `git branch` | List, create, or delete branches. | `git branch` |
| `git checkout <branch>` | Switch to another branch. | `git checkout develop` |
| `git merge <branch>` | Merge a branch into the current branch. | `git merge feature-branch` |

## 5. Stashing Work

| Command | Description | Example |
|---------|-------------|---------|
| `git stash` | Temporarily save changes that are not ready to be committed. | `git stash` |
| `git stash pop` | Reapply the changes saved in stash. | `git stash pop` |

## 6. Reviewing History

| Command | Description | Example |
|---------|-------------|---------|
| `git log` | Show commit logs. | `git log` |
| `git log --oneline --graph --all` | Show a compact graphical representation of the commit history. | `git log --oneline --graph --all` |

## 7. Resetting Changes

| Command | Description | Example |
|---------|-------------|---------|
| `git reset --hard` | Discard all changes in the working directory and staging area. | `git reset --hard` |

