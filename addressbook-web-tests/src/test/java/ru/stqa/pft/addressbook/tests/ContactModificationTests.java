package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {

        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillNewContactForm(new ContactData("Valeria", "Evgenyevna",
                "Reshetina", "89881120310", "lera.reshetina.96@mail.ru"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();

    }
}
