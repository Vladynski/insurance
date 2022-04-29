package kp.bahmatov.insurance.domain.dto.user;

import kp.bahmatov.insurance.domain.structure.User;
import lombok.Getter;

@Getter
public class AdminUserDataDto extends SelfUserOutDto {
    private final int id;
    private final boolean isActive;

    public AdminUserDataDto(User user) {
        super(user, -1);
        this.id = user.getId();
        this.isActive = user.isEnabled();
    }
}
