package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {

//        app.goTo().groupPage();
//        Groups groups = app.db().groups();
//        if (app.db().groups().size() == 0) {
//            app.group().create(new GroupData().withName("test " + java.time.LocalTime.now()));
//            app.goTo().homePage();
//        }
//        if (app.contact().list().size() == 0) {
//            app.contact().create(new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
//                    .withLastName("Решетина").withAddress("Ессентуки, Октябрьская 337, 357600").
//                    withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru").
//                    inGroup(groups.iterator().next()), true);
//        }
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test " + java.time.LocalTime.now()));
        app.goTo().homePage();
        app.contact().create(new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
                    .withLastName("Решетина").withAddress("Ессентуки, Октябрьская 337, 357600").
                    withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru").
                    inGroup(groups.iterator().next()), true);
    }

    @Test
    public void addingContactToGroupTest() {

        app.goTo().homePage();
        Contacts before = app.db().contacts();
        app.contact().selectContact(1);
        app.contact().addContactToGroup();
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before));
        verifyContactListInUI();
    }
}
