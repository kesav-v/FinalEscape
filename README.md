# FinalEscape

## Documentation

You can find the API online [here](https://ofekih.github.io/FinalEscape/javadoc/index.html "documentation")!

## Running

### On *nix

```sh
bash build.sh

java -jar FinalEscape.jar
# or
java TestRunner
# or (if you make your own runner)
java -cp [customclassfiles]:FinalEscape.jar [yourrunner]
# e.g. java -cp .:FinalEscape.jar TestRunner
```

### On Windows

```sh
javac -d . @sources.txt

# For jar:
jar cvfm FinalEscape.jar Manifest.txt @compiled.txt images/ levels/
java -jar FinalEscape.jar

# Otherwise
java TestRunner
```

## Modifying

Whenever adding new files, be sure to rerun generatesources.sh (e.g. ./generatesources.sh). For windows, run

```sh
dir /s /B *.java > sources.txt
```

For faster compilation (skip .jar generation), run:

```sh
javac -d . @sources.txt
# and execute with
java TestRunner
```

