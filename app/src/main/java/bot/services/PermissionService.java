package bot.services;

import net.dv8tion.jda.api.entities.Member;

public class PermissionService {

    private final String allowedRoleId;


    public PermissionService(String allowedRoleId) {
        this.allowedRoleId = allowedRoleId;
    }


    public boolean hasControlRole(Member member) {

        if (member == null) {
            return false;
        }

        return member.getRoles()
                .stream()
                .anyMatch(role -> role.getId().equals(allowedRoleId));
    }
}
