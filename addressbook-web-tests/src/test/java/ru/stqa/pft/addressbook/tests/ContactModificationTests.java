package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {

        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Валерия", "Евгеньевна",
                    "Решетина", "+7(988)1120310",
                    "flyingscarlett@yandex.ru", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Valeria", "Evgenyevna",
                "Reshetina", "89881120310", "lera.reshetina.96@mail.ru", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();

    }
}
