# HyPlugin — Hytale Plugin Boilerplate

A clean starting point for a Hytale server-side plugin: correct lifecycle wiring, a
per-domain registry-manager layout, a small util layer, and one **working, clearly
labelled example** of each common building block (component, command, event handler,
ECS system) so you can copy a real pattern instead of guessing.

---

## Quick start

```bash
# 1. Rename the boilerplate into your own plugin (fresh clone, before coding):
./init.sh CoolMod com.acme.coolmod

# 2. Build:
./build.sh            # → target/final/CoolMod.jar

# 3. Deploy: copy the jar into your server's mods/ directory.
```

`init.sh` rewrites the plugin name, Java package, main class, and Maven coordinates,
and moves the source directories to match — so you don't hand-edit a dozen places.

Don't want to rename? Just build the boilerplate as-is and start editing.

---

## Building

`build.sh` wraps Maven and auto-detects the Hytale server jar (the compile-time
dependency) near the repo. Override with `HYTALE_SERVER_JAR=/path/to/HytaleServer.jar`
or `-Dhytale.install-dir=...`. Output goes to `target/final/<Name>.jar`.

```bash
./build.sh            # package
./build.sh --clean    # mvn clean first
```

> The pom expects the server jar at `server/HytaleServer.jar` by default; point it
> wherever yours lives via the env var / `-D` flag rather than editing the committed pom.

---

## Project layout

```
src/main/java/<your-package>/
├── HyPlugin.java                     — plugin entry point (setup / start / shutdown)
├── setup/
│   ├── SetupManager.java             — orchestrates startup; fans out to the managers
│   ├── AssetRegistryManager.java     — custom data-driven asset stores (empty; patterned)
│   ├── ComponentRegistryManager.java — ECS components (setup) + systems (start)
│   ├── EventRegistryManager.java     — global event handlers / packet adapters
│   ├── CommandRegistryManager.java   — chat/console commands
│   └── InteractionSetupManager.java  — custom item interactions (empty; patterned)
├── examples/                         — WORKING references — copy then delete
│   ├── ExampleComponent.java         — persistent component (codec + clone + type)
│   ├── ExampleCommand.java           — /hyexample, with an arg + permission
│   ├── ExampleEventHandler.java      — PlayerDisconnect handler
│   └── ExampleSystem.java            — interval ECS system over ExampleComponent
└── util/
    ├── Log.java                      — prefixed, level-aware logging helper
    └── PluginConfig.java             — tiny JSON config loader (server-bundled BSON)

src/main/resources/manifest.json      — plugin manifest (Name / Main / ServerVersion)
build.sh                              — build wrapper
init.sh                               — one-command rename
```

---

## Lifecycle

| Phase | Method | Do here |
|-------|--------|---------|
| `setup()` | `HyPlugin.setup()` → `new SetupManager(...)` | Register components, assets, events, commands, interactions. Runs **before** worlds load. |
| `start()` | `HyPlugin.start()` → `setupManager.initializeRuntime()` | Register **ECS systems**; start runtime services. Runs **after** worlds load. |
| `shutdown()` | `HyPlugin.shutdown()` → `setupManager.shutdown()` | Unsubscribe listeners, stop schedulers, release resources. |

> **Why the split:** components must be registered in `setup()` but systems must be
> registered in `start()`. The boilerplate wires this correctly out of the box.

---

## The examples (delete when done)

Each `examples/` class is fully compiled and registered, so the plugin runs and you can
see the pattern live, then strip what you don't need:

- **`ExampleComponent`** — the four-part component template (default ctor, copy ctor +
  `clone()`, `BuilderCodec`, static `ComponentType`). The thing newcomers get wrong most.
- **`ExampleCommand`** — `/hyexample [times]`: a default arg, a permission node, and the
  player-thread `execute` signature.
- **`ExampleEventHandler`** — a `PlayerDisconnectEvent` consumer (the cleanup hook shape).
- **`ExampleSystem`** — a `DelayedEntitySystem` (1s interval) filtered by `getQuery()`,
  mutating via the `CommandBuffer` contract.

To remove them: delete the `examples/` package and the three registration lines in
`ComponentRegistryManager` (component + system) and `CommandRegistryManager` /
`EventRegistryManager`.

---

## Util layer

- **`Log`** — `Log.info/warn/error/debug(fmt, args...)`, each line prefixed with the
  plugin name. Use `Log.debug` (FINE) for hot-path logs so production stays clean.
- **`PluginConfig`** — `PluginConfig.loadOrCreate(getDataDirectory().resolve("config.json"), defaultJson)`
  then `getString/getInt/getBoolean(key, default)`. Uses the server-bundled BSON parser,
  no extra dependency.

---

## Conventions baked in

- No hard-coded magic values in logic — pull from config or asset JSON.
- Components are pure data; logic lives in systems / handlers.
- All store mutations from systems go through the `CommandBuffer`.
- Keyed codec IDs are Uppercase and globally unique.
- User-facing strings should be localized via `Message.translation(...)` + a `.lang` file
  once you add an asset pack.
