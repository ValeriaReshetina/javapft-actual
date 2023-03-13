package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        String groupName = "test1";
        if (!app.group().isThereAGroup(groupName)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.contact().goToContactCreationPage();

        }
    }

    @Test
    public void testNewContactCreation() throws Exception {
        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData("Валерия", "Евгеньевна",
                "Решетина", "+7(988)1120310", "flyingscarlett@yandex.ru", "test1");
        app.contact().create(contact, true);
        app.navigationHelper.homePage(app);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
