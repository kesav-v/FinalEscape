#!/bin/bash

javac -d . @sources.txt
jar cvfm FinalEscape.jar Manifest.txt @compiled.txt images/ levels/
