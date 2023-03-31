package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletingContactFromGroupTests extends TestBase {

    private SessionFactory sessionFactory;
    private String randomFirstName = "testName" + ThreadLocalRandom.current().nextInt(1, 10000);
    private String randomGroupName = "groupName" + ThreadLocalRandom.current().nextInt(1, 10000);

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact().create(new ContactData().withFirstName(randomFirstName).withMiddleName("Евгеньевна")
                .withLastName("Решетина").withAddress("Ессентуки, Октябрьская 337, 357600").
                withMobile("+7(988)1120310").withEmail("flyingscarlett@yandex.ru"),true);
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(randomGroupName));
        app.goTo().homePage();
        app.contact().selectContactByFirstName(randomFirstName);
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
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Test
    public void deletingContactToGroupTest() {

        app.contact().goToGroupPageAfterAddingContact();
        app.contact().selectContactByFirstName(randomFirstName);

        app.contact().deleteContactFromSelectedGroup();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<ContactData> allContactsData = session.createQuery( "from ContactData").list();

        boolean isDeletedContactFoundViaDb = false;

        for (ContactData contact : allContactsData) {
            if (contact.getFirstName().equals(randomFirstName)){
                isDeletedContactFoundViaDb = true;
            }
            if (isDeletedContactFoundViaDb == true) {
                break;
            }
        }
        session.getTransaction().commit();
        session.close();

        assertThat(isDeletedContactFoundViaDb, equalTo(false));
        verifyGroupListInUI();
        verifyContactListInUI();
    }
}