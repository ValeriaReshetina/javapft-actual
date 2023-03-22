package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        String groupName = "test1";
        if (!app.group().isThereAGroup(groupName)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(groupName));
            app.contact().goToContactCreationPage();

        }
    }

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
        return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testNewContactCreation(ContactData contact) {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        //File photo = new File("src/test/resources/stru.png");
        app.contact().create(contact, true);
        app.navigationHelper.homePage(app);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void negativeTestNewContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Валерия'").withMiddleName("Евгеньевна")
                .withLastName("Решетина").withMobile("+7(988)1120310")
                .withEmail("flyingscarlett@yandex.ru").withGroup("test1");
        app.contact().create(contact, true);
        app.navigationHelper.homePage(app);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}
