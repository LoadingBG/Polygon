# Commands to do

---
## Commands
Notes:
- `<name>` is a placeholder for a required option.
- `(name)` is a placeholder for an optional option.

### Run (done)
- Wait for slash commands to support multiline strings.

### Help
```
/help (command)
```
Sends help for commands.
- Maybe split commands into modules if needed?
- Show only available to user commands? (non-admin to not receive help for admin commands for example)
- GUI? (select menu for commands and buttons for pages)

### Admin
```
/admin kick <user> (reason)
/admin ban <user> (reason)
/admin mute <user> (reason)
/admin unmute <user>
```
- Consider other subcommands
- Consider durations

### Settings
```
/settings (level 1) (level 2) (level 3) ...
```
A GUI command with dynamic select menu and buttons for configuring the bot.
- Kinda like a settings menu in a program.

### Leaderboard
```
/leaderboard <comparable>
```
Shows leaderboard for the specified thing.
- Things like XP, invites sent, ...

### Languages
```
/languages add <new lang>
/languages remove <old lang>
```
Manages languages which will be supported in the server.
- Make it work with the help channel system (See `Features > Help channel system`)

---
## Features

### Help channel system
Make a system for help channels per language
- Make three available channels per language with a pool of more locked.
- Make a channel reserved when someone sends a message in it and unlock one from the pool (make 3 unlocked channels again).
- Unreserve a channel with a command or by inactivity.
- Put the unreserved channel into the pool.

---
## Tools

### Database
Set up a database.
- NoSQL (MongoDB) or SQL (SQLite, MySQL, ...)
- Maybe store in a non-db file?