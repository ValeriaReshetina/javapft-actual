package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
                    .withLastName("Решетина").withMobile("+7(988)1120310")
                    .withEmail("flyingscarlett@yandex.ru").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        app.contact().initContactModification(before.size() - 1);
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Valeria")
                .withMiddleName("Evgenyevna").withLastName("Reshetina").withMobile("89881120310")
                .withEmail("lera.reshetina.96@mail.ru");
        app.contact().fillContactForm(contact, false);
        app.contact().submitContactModification();
        app.contact().returnToHomePage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
