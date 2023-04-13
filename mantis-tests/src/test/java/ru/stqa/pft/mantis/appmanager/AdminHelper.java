package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;

public class AdminHelper extends HelperBase {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void goToUsersControlPanel() {
        app.wd.findElement(By.xpath("//a[contains(@href, 'manage_overview')]")).click();
        app.wd.findElement(By.xpath("//a[contains(@href, 'manage_user_page')]")).click();
    }

    public void resetUserPasswordButton(String username) {
        String xpath = "//a[contains(@href, 'manage_user_edit_page') and (text()='USER_NAME')]"
                .replace("USER_NAME", username);
        app.wd.findElement(By.xpath(xpath)).click();
        app.wd.findElement(By.xpath("//input[@value='Сбросить пароль']")).click();
        isElementPresent(By.xpath("//*[contains(text(), 'адресу электронной почты')]"));
    }

    public void authorization() {
        //app.wd.findElement(By.xpath("//a[@href='login_page.php']"));
        type(By.xpath("//input[contains(@placeholder, 'Пользователь')]"), "administrator");
        app.wd.findElement(By.xpath("//input[contains(@value, 'Вход')]")).click();
        type(By.xpath("//input[contains(@placeholder, 'Пароль')]"), "root");
        app.wd.findElement(By.xpath("//input[contains(@value, 'Вход')]")).click();
        //wd.switchTo().alert().accept();
    }

    public void selectUserById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }
}
