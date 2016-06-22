#!/bin/bash

set -e # Casuses this script to exit if any command exist with non-zero status.

printf "\n//////////////////////\n// PRE-COMMIT HOOKS //\n//////////////////////\n"

printf "\n<++ GULP LINT ++>\n"
gulp lint --strict-lint

printf "\n<++ GULP TEST ++>\n"
gulp test

printf "\n<++ GRADLE TEST ++>\n"
gradle test --rerun-tasks
