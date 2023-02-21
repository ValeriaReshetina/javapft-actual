package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class NewContactCreationTests extends TestBase {

    @Test
    public void testNewContactCreation() throws Exception {
        goToNewContactPage();
        initNewContactCreation();
        fillNewContactForm("Валерия", "Евгеньевна", "Решетина", "+7(988)1120310",
                "flyingscarlett@yandex.ru");
        submitNewContactCreation();
        returnToHomePage();
    }
}
