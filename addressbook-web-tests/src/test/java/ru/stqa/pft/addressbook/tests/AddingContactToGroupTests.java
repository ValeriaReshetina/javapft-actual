package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;

public class AddingContactToGroupTests extends TestBase {

    private SessionFactory sessionFactory;
    private String randomContactFirstName = "testName" + ThreadLocalRandom.current().nextInt(1, 10000);
    private String randomGroupName = "groupName" + ThreadLocalRandom.current().nextInt(1, 10000);

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact().create(new ContactData().withFirstName(randomContactFirstName).withMiddleName("Евгеньевна")
                    .withLastName("Решетина").withAddress("Ессентуки, Октябрьская 337, 357600").
                    withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru"),true);
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(randomGroupName));
        app.goTo().homePage();

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Test
    public void addingContactToGroupTest() {
        app.goTo().homePage();
        app.contact().selectContactByFirstName(randomContactFirstName);
        app.contact().selectGroupByName(randomGroupName);
        app.contact().addContactToSelectedGroup();

        verifyGroupListInUI();
        verifyContactListInUI();

        Integer createdContactId = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<ContactData> allContactsDataFromDB = session.createQuery( "from ContactData" ).list();
        for ( ContactData contact : allContactsDataFromDB ) {
            if (contact.getFirstName().equals(randomContactFirstName)){
                createdContactId = contact.getId();
            }
        }
        session.getTransaction().commit();

        Assert.assertNotEquals(createdContactId, null,
                "Can't get createdContactId for contact via DB: " + randomContactFirstName);

        session.beginTransaction();
        List<GroupData> allGroupsDataFromDB = session.createQuery( "from GroupData").list();

        boolean createdGroupFoundViaDB = false;

        for (GroupData group : allGroupsDataFromDB) {

            if (group.getName().equals(randomGroupName)){
                createdGroupFoundViaDB = true;

                Contacts contactsInCurrentGroup = group.getContacts();

                boolean createdContactExistInCreatedGroup = false;

                for(ContactData contactData : contactsInCurrentGroup){

                    if (contactData.getId() == createdContactId){

                        createdContactExistInCreatedGroup = true;

                        Assert.assertEquals(contactData.getFirstName(), randomContactFirstName,
                                "Contact found in group " + randomGroupName + " have wrong firstName");
                    }
                }
                Assert.assertNotEquals(createdContactExistInCreatedGroup, false,
                        "Created contact not found in group: " + randomGroupName);
            }
        }
        session.getTransaction().commit();
        session.close();

        Assert.assertNotEquals(createdGroupFoundViaDB, false,
                "Created group not found via DB by name: " + randomGroupName);
    }
}
