package org.itstep.model.entity;

import java.util.List;

public class User {
    private long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private List<Account> accounts;
    private Role userRole;

    public enum Role {
        USER, ADMIN, GUEST
    }

    public User(long id, String email, String password, String name, String surname, String patronymic, List<Account> accounts, Role userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.accounts = accounts;
        this.userRole = userRole;
    }

    public static class UserBuilder {
        private long id;
        private String email;
        private String password;
        private String name;
        private String surname;
        private String patronymic;
        private List<Account> accounts;
        private Role userRole;

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

        public UserBuilder setUserRole(Role userRole) {
            this.userRole = userRole;
            return this;
        }

        public User build() {
            return new User(id,email,password,name,surname,patronymic,accounts,userRole);
        }

        public long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public List<Account> getAccounts() {
            return accounts;
        }

        public Role getUserRole() {
            return userRole;
        }
    }


}
