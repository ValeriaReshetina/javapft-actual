package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
                .withLastName("Решетина").withMobile("+7(988)1120310")
                .withEmail("flyingscarlett@yandex.ru").withGroup("test1");
        app.contact().create(contact, true);
        app.navigationHelper.homePage(app);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
