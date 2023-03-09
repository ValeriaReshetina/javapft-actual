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
        List<ContactData> contactsBefore = app.getContactHelper().getContactList();
        ContactData contact = new ContactData("Валерия", "Евгеньевна",
                "Решетина", "+7(988)1120310", "flyingscarlett@yandex.ru", "test1");
        app.getContactHelper().createContact(contact, true);
        app.navigationHelper.goToHomePage(app);
        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size() + 1);

        int max = 0;
        for (ContactData c : contactsAfter)
            if (c.getId() > max) {
                max = c.getId();
            }

        contact.setId(contactsAfter.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        contactsBefore.add(contact);
        Assert.assertEquals(new HashSet<Object>(contactsBefore), new HashSet<Object>(contactsAfter));
    }
}
