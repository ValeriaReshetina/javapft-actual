package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;

public class NewContactCreationTests extends TestBase {

    @Test
    public void testNewContactCreation() throws Exception {
        app.goToNewContactPage();
        app.initNewContactCreation();
        app.fillNewContactForm("Валерия", "Евгеньевна", "Решетина", "+7(988)1120310",
                "flyingscarlett@yandex.ru");
        app.submitNewContactCreation();
        app.returnToHomePage();
    }
}
