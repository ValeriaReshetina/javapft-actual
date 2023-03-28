package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class DeletingContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().groups();
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
            app.goTo().homePage();
        }
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
                    .withLastName("Решетина").withAddress("Yessentuki, Oktyabrskaya 337, 357600").
                    withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru").
                    inGroup(groups.iterator().next()), true);
        }
    }

        @Test
        public void deletingContactFromGroupTest() {

            app.goTo().homePage();
            app.contact().selectContact(1);
            app.contact().selectGroupWithContacts();
        }
}