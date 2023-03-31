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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class AddingContactToGroupTests extends TestBase {

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
        app.contact().selectContactByFirstName(randomFirstName);
        app.contact().selectGroupByName(randomGroupName);
        app.contact().addContactToSelectedGroup();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> allGroupsData = session.createQuery( "from GroupData").list();

        boolean isCreatedGroupFoundViaDb = false;

        for (GroupData group : allGroupsData) {
            if (group.getName().equals(randomGroupName)){
                isCreatedGroupFoundViaDb = true;
            }
            if (isCreatedGroupFoundViaDb == true) {
                break;
            }
        }
        session.getTransaction().commit();
        session.close();

        assertThat(isCreatedGroupFoundViaDb, equalTo(true));
        verifyGroupListInUI();
        verifyContactListInUI();
    }
}
