package tests;

import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RemoveContactTests extends TestBase{

    @BeforeMethod(alwaysRun = true)
    public void preCondition(){

        if (!app.getHelperUser().isLogged()){
            app.getHelperUser().login(new User().withEmail("locker@gmail.com").withPassword("Qwerty1234!"));
        }
        app.getHelperContact().providerContacts();
    }


    @Test(groups = {"smoke"})
    public void removeFirstContact(){
        //Assert size of ContactList less by one
        Assert.assertEquals(app.getHelperContact().removeOneContact(),1);

    }

    @Test
    public void removeAllContacts(){
        //"No contacts Here
        app.getHelperContact().removeAllContacts();
        Assert.assertTrue(app.getHelperUser().isNoContactsHereDisplayed());


    }
}
