package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;


public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() throws Exception {
        String groupName = "test1";

        if (!app.getGroupHelper().isThereAGroup(groupName)) {
            app.getNavigationHelper().goToGroupPage();
            app.getGroupHelper().createGroup(new GroupData(groupName, "test2", "test3"));
            app.getNavigationHelper().goToHomePage();
        }

        int before = app.getContactHelper().getContactCount();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Валерия", "Евгеньевна",
                    "Решетина", "+7(988)1120310",
                    "flyingscarlett@yandex.ru", groupName), true);
        }

        app.getContactHelper().selectContact(before - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().returnToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before - 1);
    }
}
