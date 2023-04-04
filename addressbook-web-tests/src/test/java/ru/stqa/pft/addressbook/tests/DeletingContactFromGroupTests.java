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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletingContactFromGroupTests extends TestBase {

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
        app.contact().selectContactByFirstName(randomContactFirstName);
        app.contact().selectGroupByName(randomGroupName);
        app.contact().addContactToSelectedGroup();

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    public void deletingContactFromGroupTest() {
        verifyGroupListInUI();
        verifyContactListInUI();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Integer createdContactId = null;

        List<ContactData> allContactsDataFromDB = session.createQuery("from ContactData" ).list();
        for ( ContactData contact : allContactsDataFromDB ) {
            if (contact.getFirstName().equals(randomContactFirstName)){
                createdContactId = contact.getId();
            }
        }
        session.getTransaction().commit();

        Assert.assertNotEquals(createdContactId, null,
                "CreatedContactId for contact via DB: " + randomContactFirstName + " is null");

        app.contact().goToGroupPageAfterAddingContact();
        app.contact().selectContactByFirstName(randomContactFirstName);

        app.contact().deleteContactFromSelectedGroup();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> allGroupsDataFromDB = session.createQuery( "from GroupData").list();

        boolean createdGroupFoundViaDB = false;

        for (GroupData group : allGroupsDataFromDB) {

            if (group.getName().equals(randomGroupName)){
                createdGroupFoundViaDB = true;

                Contacts contactsInCurrentGroup = group.getContacts();

                for(ContactData contactData : contactsInCurrentGroup){
                    Assert.assertNotEquals(contactData.getId(), createdContactId,
                            "Contact with ID " + contactData.getId() + " found in group " + randomGroupName);

                    Assert.assertNotEquals(contactData.getFirstName(), randomContactFirstName,
                            "Contact with first name " + contactData.getFirstName() + " found in group "
                                    + randomGroupName);
                }
            }
        }
        session.getTransaction().commit();
        session.close();

        Assert.assertNotEquals(createdGroupFoundViaDB, false,
                "Created group not found via DB by name: " + randomGroupName);
    }
}