package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testNewContactCreation() throws Exception {
        String groupName = "test1";

        if (!app.getGroupHelper().isThereAGroup(groupName)) {
            app.getNavigationHelper().goToGroupPage();
            app.getGroupHelper().createGroup(new GroupData(groupName, null, null));
            app.getContactHelper().goToContactCreationPage();

        }
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().createContact(new ContactData("Валерия", "Евгеньевна",
                "Решетина", "+7(988)1120310", "flyingscarlett@yandex.ru", "test1"), true);
        app.navigationHelper.goToHomePage(app);
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before + 1);
    }
}
