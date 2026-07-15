package bot.commands;

import bot.services.MinecraftQueryService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StatusCommand implements Command {

    private final MinecraftQueryService minecraftQueryService;

    public StatusCommand(MinecraftQueryService minecraftQueryService) {
        this.minecraftQueryService = minecraftQueryService;
    }


    @Override
    public String getName() {
        return "status";
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String status = minecraftQueryService.getStatus();

        event.reply(status).queue();
    }
}
