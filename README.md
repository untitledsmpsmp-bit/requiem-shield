# ✦ Requiem Shield

**Paper 1.21.11 plugin** inspired by Gold Experience Requiem's *Return to Zero*.

The shield manipulates reality rather than simply dealing raw damage.

## Features

### Shield
* Normal vanilla shield with hidden persistent data tag
* Works regardless of display name
* Supports any banner pattern
* Compatible with anvils
* Remains recognized after renaming/modification

### Abilities

#### Soul Split (360s cooldown)
* Enter Spectator Mode for ~8 seconds
* Your body is replaced with an immortal Zombie
* Other players can damage the Zombie
* 50% of damage dealt to Zombie transfers to you upon reunion
* Right-click your Zombie body to return to Survival
* Auto-timeout after 8 seconds if you don't reunite

#### Return to Zero (240s cooldown)
* Marked player's timeline appears slowed
* Movement, eating, mining, attacks all feel slower
* Every significant action has a 50% chance to fail
* Failed actions are cancelled with white/gold rewind particles
* Effect lasts ~5 seconds

### Mark System
* Attack another player while shield is in offhand to mark them (10s duration)
* Only one player can be marked at a time
* Hitting an already-marked player doesn't refresh the mark
* Target receives Glowing effect
* Cinematic effects and chat notifications on mark

## Installation

1. Build the plugin:
   ```bash
   mvn clean package
   ```

2. Move the JAR to your Paper server's `plugins/` folder

3. Restart the server

4. Give yourself a shield (or craft one):
   ```
   /give @s shield
   ```

## Commands

* `/rtzcommandtoggle` - Toggle command mode on/off
* `/rtz1` - Activate Soul Split
* `/rtz2` - Activate Return to Zero

## Keybinds (Requires Client Mod)

* `Shift + F` → Soul Split
* `F` → Return To Zero

## Invalid Conditions

Abilities fail without consuming cooldown if:
* No marked player exists
* Target died
* Target disconnected
* Mark expired
* Different world
* Target is outside 20 blocks

## Design Philosophy

**Requiem Shield should feel like a legendary SMP artifact:**

* Cinematic and powerful but balanced
* White/gold visual identity
* Strong sound design
* Smooth effects with titles for major abilities
* Chat messages for passive effects
* Modular, clean code
* Modern Paper APIs
* Good multiplayer performance
* Proper entity and task cleanup
* Strong edge-case handling

**The central fantasy:**
> **Reality itself has chosen to reject the marked player's actions and existence.**

## License

MIT License - See LICENSE file for details
