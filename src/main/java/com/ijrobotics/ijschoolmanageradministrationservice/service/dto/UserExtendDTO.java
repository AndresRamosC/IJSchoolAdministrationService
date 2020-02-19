package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.UserType;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.UserExtend} entity.
 */
public class UserExtendDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String userName;

    private String password;

    private Boolean enabled;

    private UserType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserExtendDTO userExtendDTO = (UserExtendDTO) o;
        if (userExtendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExtendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExtendDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
