#!/bin/bash

# EvilMusic - Web-Based Music Player
# Copyright (C) 2016 Joe Falascino
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

# A script that will run the integration EvilMusic tests.

gradle configIntTest bootRun &

sleep 20
gradle intTest --rerun-tasks

kill -term $!
