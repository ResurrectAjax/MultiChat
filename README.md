# MultiChat

A multi purpose chat plugin which allows players to send messages if they are in the same chat channel and in the right radius.

For example:
global -> reaches everywhere on the server
local -> only reaches players within a 20 block radius in the same world
whisper -> only reaches players within a 3 block radius in the same world

- **Base Commands**:
  - /multichat help - _Get a list of commands_
  - /multichat channel <channel> - _Switches to a different channel_
  - /multichat profanity - _Toggles the profanity filter_
  - /multichat admin <admincommand> - _Run an admin command_
- **Admin commands**:
  - /multichat admin help - _Get a list of admin commands_
  - /multichat admin reload - _Reloads the config files_
  - /multichat admin create <channel> - _Create a new channel_
  - /multichat admin setradius <channel> <radius> - _Set the radius of a channel_
  - /multichat admin setworld <channel> <world> - _Set the world of a channel to all worlds or the player's own world_
  - /multichat admin setdefault <channel> - _Set the default channel_
