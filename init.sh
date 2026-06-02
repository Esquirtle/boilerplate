#!/usr/bin/env bash
#
# init.sh — Rename this boilerplate into your own plugin in one command.
#
# Renames the plugin name, Java package, main class, and Maven coordinates
# throughout the project, then renames the source directories to match.
#
# Usage:
#   ./init.sh <PluginName> <package> [groupId]
#
# Examples:
#   ./init.sh CoolMod  com.acme.coolmod
#   ./init.sh CoolMod  com.acme.coolmod  com.acme
#
#   <PluginName>  PascalCase; becomes the manifest Name, pom <name>/<artifactId>,
#                 main class (e.g. CoolMod → CoolMod.java), and log prefix.
#   <package>    full Java package root for the plugin (e.g. com.acme.coolmod).
#   [groupId]    Maven groupId; defaults to <package>.
#
# Run this on a FRESH clone, before writing code. It edits files in place.
#
set -euo pipefail

if [[ $# -lt 2 ]]; then
  sed -n '2,20p' "$0" | sed 's/^# \{0,1\}//'
  exit 2
fi

NEW_NAME="$1"          # e.g. CoolMod
NEW_PKG="$2"           # e.g. com.acme.coolmod
NEW_GROUP="${3:-$NEW_PKG}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

OLD_NAME="HyPlugin"
OLD_PKG="esq.hyplugin"
OLD_PKG_PATH="esq/hyplugin"
NEW_PKG_PATH="${NEW_PKG//.//}"
SRC="src/main/java"

echo "==> Renaming: $OLD_NAME → $NEW_NAME, package $OLD_PKG → $NEW_PKG"

# 1. Rewrite file contents (package decls, imports, class name, identifiers).
#    Longest/most-specific patterns first.
find src -type f \( -name '*.java' -o -name '*.json' -o -name '*.xml' \) -print0 \
  | xargs -0 sed -i \
      -e "s/${OLD_PKG}/${NEW_PKG}/g" \
      -e "s/\b${OLD_NAME}\b/${NEW_NAME}/g"

# 2. pom.xml: groupId + artifactId/name.
sed -i \
  -e "0,/<groupId>${OLD_PKG}<\/groupId>/s//<groupId>${NEW_GROUP}<\/groupId>/" \
  -e "s/<artifactId>${OLD_NAME}<\/artifactId>/<artifactId>${NEW_NAME}<\/artifactId>/" \
  -e "s/<name>${OLD_NAME}<\/name>/<name>${NEW_NAME}<\/name>/" \
  pom.xml

# 3. Move the package directories.
if [[ -d "$SRC/$OLD_PKG_PATH" ]]; then
  mkdir -p "$SRC/$(dirname "$NEW_PKG_PATH")"
  git mv "$SRC/$OLD_PKG_PATH" "$SRC/$NEW_PKG_PATH" 2>/dev/null \
    || mv "$SRC/$OLD_PKG_PATH" "$SRC/$NEW_PKG_PATH"
fi

# 4. Rename the main class file.
MAIN_DIR="$SRC/$NEW_PKG_PATH"
if [[ -f "$MAIN_DIR/${OLD_NAME}.java" ]]; then
  git mv "$MAIN_DIR/${OLD_NAME}.java" "$MAIN_DIR/${NEW_NAME}.java" 2>/dev/null \
    || mv "$MAIN_DIR/${OLD_NAME}.java" "$MAIN_DIR/${NEW_NAME}.java"
fi

# 5. Clean up empty leftover package dirs (e.g. esq/ after moving esq/hyplugin).
find "$SRC" -type d -empty -delete 2>/dev/null || true

echo "==> Done. Review changes with 'git diff', then build with ./build.sh"
echo "    Main class: $MAIN_DIR/${NEW_NAME}.java"
echo "    Consider deleting the examples package once you've copied what you need."
