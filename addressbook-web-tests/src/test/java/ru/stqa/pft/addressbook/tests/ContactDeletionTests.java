package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;


public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() throws Exception {

        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Валерия", "Евгеньевна",
                    "Решетина", "+7(988)1120310",
                    "flyingscarlett@yandex.ru", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().returnToHomePage();
    }
}
