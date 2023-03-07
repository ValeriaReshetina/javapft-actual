package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitNewContactCreation() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("middlename")).click();
        wd.findElement(By.name("middlename")).clear();
        wd.findElement(By.name("middlename")).sendKeys(contactData.getMiddleName());
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("mobile")).click();
        wd.findElement(By.name("mobile")).clear();
        wd.findElement(By.name("mobile")).sendKeys(contactData.getMobile());
        wd.findElement(By.name("email")).click();
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys(contactData.geteMail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initNewContactCreation() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void selectContact() {
        wd.findElement(By.xpath("//input[@name='selected[]']")).click();
    }

    public void deleteSelectedContact() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
        wd.switchTo().alert().accept();
    }

    public void initContactModification() {
        wd.findElement(By.xpath("//img[@alt='Edit']")).click();
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact, boolean creation) {
        initNewContactCreation();
        fillContactForm(contact, creation);
        submitNewContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public boolean isThereAGroupInContactCreationForm() {
        return isElementPresent(By.xpath("//select[@name='new_group']//option[text()='test1']"));
    }

    public boolean goToContactCreationPage() {
        return isElementPresent(By.name("add new"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements) {
            //String firstName = element.findElement();
            //String lastName = element.findElement();
          //  ContactData contact = new ContactData(firstName, null, lastName,
             //       null, null, null);
           // contacts.add(contact);
        }
        return contacts;
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
}
