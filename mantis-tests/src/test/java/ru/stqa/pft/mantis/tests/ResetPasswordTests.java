package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.AdminHelper;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class ResetPasswordTests extends TestBase {

    @Test
    public void testForChangingPassword() throws MessagingException, IOException {
        AdminHelper adminHelper = new AdminHelper(app);
        long now = System.currentTimeMillis();
        Users users = app.db().users();
        UserData userForPasswordReset = users.iterator().next();
        String user = String.format("user%s", now);
        app.getDriver();
        String email = String.format(userForPasswordReset.getEmail());
        String username = String.format(userForPasswordReset.getUsername());
        String password = String.format(userForPasswordReset.getPassword());
        app.james().createUser(username, password);
        String newPassword = String.format("password%s", now);
        adminHelper.authorization();
        adminHelper.goToUsersControlPanel();
        adminHelper.selectUserById(userForPasswordReset.getId());
        adminHelper.resetUserPasswordButton(user);
        List<MailMessage>mailMessages = app.james().waitForMail(user, password, 60000);
        String confirmationLink = getConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, "password");
        assertTrue(app.newSession().login(user, password));
    }

    public String getConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }
}
