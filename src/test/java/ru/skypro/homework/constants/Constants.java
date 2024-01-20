package ru.skypro.homework.constants;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constants {
    public static final Date TIME_1 = new Date(2024, 01, 15, 11, 11,11);
    public static final Date TIME_2 = new Date(2024, 01, 15, 12, 12,12);
    public static final Date TIME_3 = new Date(2024, 01, 16, 13, 13,13);
    public static final User USER = new User(1, "user@test.com", "User name", "User lastname", "+71112223344", Role.USER, "user_1.jpg", "123");
    public static final User ADMIN = new User(2, "admin@test.com", "Admin name", "Admin lastname", "+79998887766", Role.ADMIN, "user_2.jpg", "321");
    public static final User ANOTHER_USER = new User(3, "anotheruser@test.com", "Another user name", "Another user lastname", "+75556667788", Role.USER, "user_3.jpg", "456");
    public static final Ad AD_1 = new Ad(1, USER, "user_1_ad_1.jpg", 1111, "Test title 1", "Test description 1");
    public static final Ad AD_2 = new Ad(2, USER, "user_1_ad_2.jpg", 2222, "Test title 2", "Test description 2");
    public static final Ad AD_3 = new Ad(3, ADMIN, "user_2_ad_3.jpg", 3333, "Test title 3", "Test description 3");
    public static final Comment COMMENT_1 = new Comment(1, USER, USER.getImage(), USER.getFirstName(), TIME_1.getTime(), "Test comment text 1", AD_1);
    public static final Comment COMMENT_2 = new Comment(2, USER, USER.getImage(), USER.getFirstName(), TIME_2.getTime(), "Test comment text 2", AD_1);
    public static final Comment COMMENT_3 = new Comment(3, ADMIN, ADMIN.getImage(), ADMIN.getFirstName(), TIME_3.getTime(), "Test comment text 3", AD_1);
    public static final Comment COMMENT_1_SAVE = new Comment(null, USER, USER.getImage(), USER.getFirstName(), 111111L, "Test comment text 1", AD_1);
    public static final Comment COMMENT_2_SAVE = new Comment(null, USER, USER.getImage(), USER.getFirstName(), 111111L, "Test comment text 2", AD_1);
    public static final Comment COMMENT_3_SAVE = new Comment(null, ADMIN, ADMIN.getImage(), ADMIN.getFirstName(), 111111L, "Test comment text 3", AD_1);
    public static final List<User> USERS = new ArrayList<>(List.of(USER, ADMIN));
    public static final List<Ad> ADS = new ArrayList<>(List.of(AD_1, AD_2, AD_3));
    public static final List<Ad> ADS_USER = new ArrayList<>(List.of(AD_1, AD_2));
    public static final List<Ad> ADS_ADMIN = new ArrayList<>(List.of(AD_3));
    public static final List<Comment> COMMENTS = new ArrayList<>(List.of(COMMENT_1, COMMENT_2, COMMENT_3));
}
