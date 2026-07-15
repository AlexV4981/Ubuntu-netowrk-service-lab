package bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand implements Command {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Im alive!").queue();
    }
}
