package io.spring.core.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void should_create_user_with_all_fields() {
    User user = new User("test@example.com", "testuser", "password123", "My bio", "http://image.url");

    assertThat(user.getId(), notNullValue());
    assertThat(user.getEmail(), is("test@example.com"));
    assertThat(user.getUsername(), is("testuser"));
    assertThat(user.getPassword(), is("password123"));
    assertThat(user.getBio(), is("My bio"));
    assertThat(user.getImage(), is("http://image.url"));
  }

  @Test
  public void should_create_user_with_empty_bio_and_image() {
    User user = new User("test@example.com", "testuser", "password123", "", "");

    assertThat(user.getBio(), is(""));
    assertThat(user.getImage(), is(""));
  }

  @Test
  public void should_generate_unique_id_for_each_user() {
    User user1 = new User("user1@example.com", "user1", "pass1", "", "");
    User user2 = new User("user2@example.com", "user2", "pass2", "", "");

    assertThat(user1.getId(), not(user2.getId()));
  }

  @Test
  public void should_update_username_only() {
    User user = new User("test@example.com", "oldusername", "password123", "bio", "image");
    String originalEmail = user.getEmail();
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("", "newusername", "", "", "");

    assertThat(user.getUsername(), is("newusername"));
    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_update_password_only() {
    User user = new User("test@example.com", "testuser", "oldpassword", "bio", "image");
    String originalEmail = user.getEmail();
    String originalUsername = user.getUsername();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("", "", "newpassword", "", "");

    assertThat(user.getPassword(), is("newpassword"));
    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_update_email_only() {
    User user = new User("old@example.com", "testuser", "password123", "bio", "image");
    String originalUsername = user.getUsername();
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("new@example.com", "", "", "", "");

    assertThat(user.getEmail(), is("new@example.com"));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_update_bio_only() {
    User user = new User("test@example.com", "testuser", "password123", "old bio", "image");
    String originalEmail = user.getEmail();
    String originalUsername = user.getUsername();
    String originalPassword = user.getPassword();
    String originalImage = user.getImage();

    user.update("", "", "", "new bio", "");

    assertThat(user.getBio(), is("new bio"));
    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_update_image_only() {
    User user = new User("test@example.com", "testuser", "password123", "bio", "old-image.jpg");
    String originalEmail = user.getEmail();
    String originalUsername = user.getUsername();
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();

    user.update("", "", "", "", "new-image.jpg");

    assertThat(user.getImage(), is("new-image.jpg"));
    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
  }

  @Test
  public void should_update_multiple_fields_at_once() {
    User user = new User("old@example.com", "olduser", "oldpass", "old bio", "old-image.jpg");

    user.update("new@example.com", "newuser", "newpass", "new bio", "new-image.jpg");

    assertThat(user.getEmail(), is("new@example.com"));
    assertThat(user.getUsername(), is("newuser"));
    assertThat(user.getPassword(), is("newpass"));
    assertThat(user.getBio(), is("new bio"));
    assertThat(user.getImage(), is("new-image.jpg"));
  }

  @Test
  public void should_not_update_fields_when_null_values_provided() {
    User user = new User("test@example.com", "testuser", "password123", "bio", "image");
    String originalEmail = user.getEmail();
    String originalUsername = user.getUsername();
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update(null, null, null, null, null);

    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_not_update_fields_when_empty_strings_provided() {
    User user = new User("test@example.com", "testuser", "password123", "bio", "image");
    String originalEmail = user.getEmail();
    String originalUsername = user.getUsername();
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("", "", "", "", "");

    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getUsername(), is(originalUsername));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_preserve_id_after_update() {
    User user = new User("test@example.com", "testuser", "password123", "bio", "image");
    String originalId = user.getId();

    user.update("new@example.com", "newuser", "newpass", "new bio", "new-image.jpg");

    assertThat(user.getId(), is(originalId));
  }

  @Test
  public void should_have_equality_based_on_id() {
    User user1 = new User("test@example.com", "testuser", "password123", "bio", "image");
    User user2 = new User("test@example.com", "testuser", "password123", "bio", "image");

    assertThat(user1.equals(user2), is(false));
    assertThat(user1.equals(user1), is(true));
  }

  @Test
  public void should_update_username_and_password_together() {
    User user = new User("test@example.com", "olduser", "oldpass", "bio", "image");
    String originalEmail = user.getEmail();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("", "newuser", "newpass", "", "");

    assertThat(user.getUsername(), is("newuser"));
    assertThat(user.getPassword(), is("newpass"));
    assertThat(user.getEmail(), is(originalEmail));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_update_email_and_username_together() {
    User user = new User("old@example.com", "olduser", "password", "bio", "image");
    String originalPassword = user.getPassword();
    String originalBio = user.getBio();
    String originalImage = user.getImage();

    user.update("new@example.com", "newuser", "", "", "");

    assertThat(user.getEmail(), is("new@example.com"));
    assertThat(user.getUsername(), is("newuser"));
    assertThat(user.getPassword(), is(originalPassword));
    assertThat(user.getBio(), is(originalBio));
    assertThat(user.getImage(), is(originalImage));
  }

  @Test
  public void should_handle_special_characters_in_username() {
    User user = new User("test@example.com", "user_name-123", "password", "bio", "image");

    assertThat(user.getUsername(), is("user_name-123"));

    user.update("", "new_user-456", "", "", "");

    assertThat(user.getUsername(), is("new_user-456"));
  }

  @Test
  public void should_handle_long_bio_text() {
    String longBio = "This is a very long bio text that contains multiple sentences. " +
        "It describes the user in great detail. " +
        "The user has many interests and hobbies. " +
        "They enjoy programming and writing tests.";

    User user = new User("test@example.com", "testuser", "password", "", "image");

    user.update("", "", "", longBio, "");

    assertThat(user.getBio(), is(longBio));
  }

  @Test
  public void should_handle_url_format_image() {
    String imageUrl = "https://example.com/images/profile/user123.jpg?size=large&format=webp";

    User user = new User("test@example.com", "testuser", "password", "bio", "");

    user.update("", "", "", "", imageUrl);

    assertThat(user.getImage(), is(imageUrl));
  }

  @Test
  public void should_allow_sequential_updates() {
    User user = new User("test@example.com", "testuser", "password", "bio", "image");

    user.update("new1@example.com", "", "", "", "");
    assertThat(user.getEmail(), is("new1@example.com"));

    user.update("new2@example.com", "", "", "", "");
    assertThat(user.getEmail(), is("new2@example.com"));

    user.update("", "newuser", "", "", "");
    assertThat(user.getUsername(), is("newuser"));
    assertThat(user.getEmail(), is("new2@example.com"));
  }
}
