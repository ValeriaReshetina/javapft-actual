package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

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
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData contact = new ContactData("Валерия", "Евгеньевна",
                "Решетина", "+7(988)1120310", "flyingscarlett@yandex.ru", "test1");
        app.getContactHelper().createContact(contact, true);
        app.navigationHelper.goToHomePage(app);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        int max = 0;
        for (ContactData c : after)
            if (c.getId() > max) {
                max = c.getId();
            }
        contact.setId(max);
        before.add(contact);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}
