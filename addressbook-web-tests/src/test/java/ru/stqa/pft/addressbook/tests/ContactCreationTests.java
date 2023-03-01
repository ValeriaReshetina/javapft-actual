package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testNewContactCreation() throws Exception {
        app.getContactHelper().initNewContactCreation();
        app.getContactHelper().fillContactForm(
                new ContactData("Валерия", "Евгеньевна", "Решетина",
                        "+7(988)1120310", "flyingscarlett@yandex.ru", "test1"), true);
        app.getContactHelper().submitNewContactCreation();
        app.navigationHelper.goToHomePage(app);
    }
}
