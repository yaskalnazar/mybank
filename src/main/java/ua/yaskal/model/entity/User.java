package ua.yaskal.model.entity;

import java.util.List;

/**
 * This entity represents user in system.
 * The class has POJO structure.
 *
 * @author Nazar Yaskal
 */
public class User {
    private long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private List<Account> accounts;
    private Role role;



    public enum Role {
        USER("user"), ADMIN("admin"), GUEST("guest");

        private String name;

        Role(String name) {
            this.name = name;
        }

        public String getStringRole(){
            return name;
        }
    }

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public User(long id, String email, String password, String name, String surname, String patronymic, List<Account> accounts, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.accounts = accounts;
        this.role = role;
    }

    /**
     * This class realize pattern Builder for class {@link User}
     *
     * @author Nazar Yaskal
     * @see User
     */
    public static class UserBuilder {
        private long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private String patronymic;
        private List<Account> accounts;
        private Role role;

        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public UserBuilder setAccounts(List<Account> accounts) {
            this.accounts = accounts;
            return this;
        }

        public UserBuilder setUserRole(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(id,email,password,name,surname,patronymic,accounts,role);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
