#!/usr/bin/env bash
# Run the same checks as CI: Checkstyle (linter) then unit tests.
# Uses ./mvnw (Maven Wrapper) if present, otherwise mvn. Run from project root.

set -e

# Ensure JAVA_HOME is set so Maven Wrapper finds Java (e.g. Homebrew JDK on macOS)
if [ -z "${JAVA_HOME}" ]; then
  if [ -x "/usr/libexec/java_home" ]; then
    JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null) || true
  fi
  if [ -z "${JAVA_HOME}" ] && [ -d "/opt/homebrew/opt/openjdk@21" ]; then
    JAVA_HOME="/opt/homebrew/opt/openjdk@21"
  fi
  if [ -z "${JAVA_HOME}" ] && [ -d "/opt/homebrew/opt/openjdk" ]; then
    JAVA_HOME="/opt/homebrew/opt/openjdk"
  fi
  if [ -n "${JAVA_HOME}" ]; then
    export JAVA_HOME
  fi
fi

MVN="./mvnw"
if [ ! -x "$MVN" ]; then
  MVN="mvn"
fi

echo "==> Running Checkstyle (linter)..."
$MVN -q checkstyle:check

echo "==> Running unit tests..."
$MVN -q test

echo ""
echo "==> All checks passed (same as CI)."
