#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME

# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

SAVED="`pwd`"
CDPATH=""
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available byte code version to be compatible with different versions of Java
# Also make sure to not disable standard Java layout
DEFAULT_JVM_OPTS="$DEFAULT_JVM_OPTS \"-Dorg.gradle.appname=$APP_BASE_NAME\""

# Find java
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/executable/java" ] ; then
        # JDK 9 support
        JAVACMD="$JAVA_HOME/executable/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase maximum heap size for Gradle build
if [ -n "$GRADLE_OPTS" ] ; then
    eval set -- "$GRADLE_OPTS"
else
    set --
fi

# Collect all arguments for the java command, following the ProcessBuilder convention:
# $JAVACMD $JAVA_OPTS $DEFAULT_JVM_OPTS -classpath $CLASSPATH org.gradle.wrapper.GradleWrapperMain "$@"

# Determine the Java command to use to start the JVM.
if [ -z "$JAVACMD" ] ; then
    if [ -n "$JAVA_HOME" ] ; then
        if [ -x "$JAVA_HOME/executable/java" ] ; then
            # JDK 9 support
            JAVACMD="$JAVA_HOME/executable/java"
        else
            JAVACMD="$JAVA_HOME/bin/java"
        fi
    else
        JAVACMD="java"
    fi
fi

exec "$JAVACMD" "-classpath" "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
