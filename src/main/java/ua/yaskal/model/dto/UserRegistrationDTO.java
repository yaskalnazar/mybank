package ua.yaskal.model.dto;


/**
 * This DTO used for registration user in system
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.controller.command.guest.RegistrationCommand
 */
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String patronymic;

    public UserRegistrationDTO(String email, String password, String name, String surname, String patronymic) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
