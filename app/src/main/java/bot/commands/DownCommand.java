package bot.commands;

import bot.services.MinecraftService;
import bot.services.PermissionService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class DownCommand implements Command {

    private final MinecraftService minecraftService;
    private final PermissionService permissionService;


    public DownCommand(
            MinecraftService minecraftService,
            PermissionService permissionService
    ) {
        this.minecraftService = minecraftService;
        this.permissionService = permissionService;
    }


    @Override
    public String getName() {
        return "down";
    }


    @Override
    public void execute(SlashCommandInteractionEvent event) {

        if (!permissionService.hasControlRole(event.getMember())) {

            event.reply("You do not have permission to control the server.")
                    .setEphemeral(true)
                    .queue();

            return;
        }


        event.reply(
            minecraftService.stopServer(
                event.getUser().getId(),
                event.getUser().getName()
            )
        ).queue();
    }
}
