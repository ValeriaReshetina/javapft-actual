package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testNewContactCreation() throws Exception {

        if (!app.getContactHelper().isThereAGroupInContactCreationForm()) {
            app.getNavigationHelper().goToGroupPage();
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
            app.getContactHelper().goToContactCreationPage();
        }
        app.getContactHelper().createContact(new ContactData("Валерия", "Евгеньевна",
                "Решетина", "+7(988)1120310", "flyingscarlett@yandex.ru", "test1"), true);
        app.navigationHelper.goToHomePage(app);
    }
}
