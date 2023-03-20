package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        String groupName = "test1";

        if (!app.group().isThereAGroup(groupName)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(groupName));
            app.goTo().homePage();
        }
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Валерия").withMiddleName("Евгеньевна")
                    .withLastName("Решетина").withAddress("Yessentuki, Oktyabrskaya 337, 357600").
                    withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru").
                    withGroup(groupName), true);
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

        assertThat(contact.getAllEmails(), equalTo(mergeMails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(address(contactInfoFromEditForm)));
    }

    private String mergeMails(ContactData contact) {
        return contact.getAllEmails();
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactInfoTests::phoneCleaned)
                .collect(Collectors.joining("\n"));
    }

    private String address(ContactData contact) {
        return contact.getAddress();
    }

    public static String phoneCleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
