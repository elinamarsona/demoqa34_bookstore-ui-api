package tests;
import api.AccountApiSteps;
import api.BookStoreApiSteps;
import helpers.WithLogin;
import models.BookModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static tests.TestData.isbn;
import static tests.TestData.userName;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class DemoqaBookTests extends TestBase {

    @Test
    @WithLogin
    @DisplayName("Удаление книги из профиля")
    void deleteBookTest() {
        LoginResponseModel auth = step("Авторизация пользователя через API", AccountApiSteps::login);
        String token = auth.getToken();
        String userId = auth.getUserId();

        step("Очистка корзины пользователя через API", () ->
                BookStoreApiSteps.deleteAllBooks(token, userId)
        );

        step("Добавление книги через API", () ->
                BookStoreApiSteps.addBook(token, userId, isbn)
        );

        step("Проверка добавленной книги через API", () -> {
            List<BookModel> books = AccountApiSteps.getUserBooks(token, userId);
            assertThat(books).extracting(BookModel::getIsbn).contains(isbn);
        });

        step("Переход на страницу профиля", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(userName));
        });

        step("Удаление книги через API", () ->
                BookStoreApiSteps.deleteBook(token, userId, isbn)
        );

        step("Проверка удаленной книги через API", () -> {
            List<BookModel> books = AccountApiSteps.getUserBooks(token, userId);
            assertThat(books).extracting(BookModel::getIsbn).doesNotContain(isbn);
        });
    }
}