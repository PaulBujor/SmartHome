# SEP4

## Repo rules
- No force pushes, if it doesn't fit, then it doesn't go there
- If you add stuff to .gitignore and it doesn't work, talk with Paul.
- In .gitignore avoid adding generic ignores, such as "*.xml". This can fuck up other projects. If you need it on a project, use "project_folder_name/*.xml"

## Branch Rules
- Every team will have its own branch to work on. No one should work on the "main" branch directly.
- A team can make it's own sub-branches, and work on that should be merged into the team branch first
- When work on a team branch is done, a pull request can be made to merge progress into the main branch
- 2 code review approvals are needed for a pull request to go through. 
  - If it contains connections between 'tiers', approval is needed from the team that's exposing functionality, and the team that's consuming it.
  - If it is only local changes (eg. project structure, class names, UI etc.), approval is needed from 2 people from the same team