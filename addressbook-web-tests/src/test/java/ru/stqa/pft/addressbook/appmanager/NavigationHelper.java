package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void goToGroupPage() {
        click(By.linkText("groups"));
    }

    public void goToHomePage(ApplicationManager applicationManager) {
        applicationManager.wd.findElement(By.linkText("home page")).click();
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }
}