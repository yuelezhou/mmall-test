package com.mmall.test.common;


public class Const {
    public static final String CURRENT_USER="currentUser";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public  enum Role{
        RoleCustomer(0),RoleAdmin(1);

        private int admin;

        public int getAdmin() {
            return admin;
        }

        Role(int admin) {
            this.admin = admin;
        }
    }
}
