package tests;

import manager.DataProviderContacts;
import models.Contact;
import models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddNewContactTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preCondition() {

        if (!app.getHelperUser().isLogged()) {
            app.getHelperUser().login(new User().withEmail("locker@gmail.com").withPassword("Qwerty1234!"));
        }

    }

    @Test(dataProvider = "contactSuccess", dataProviderClass = DataProviderContacts.class,invocationCount = 3)
    public void addContactSuccessAllFields(Contact contact) {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().getScreen("src/test/screenshots/screen-" + i + ".png");
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isContactAddedByName(contact.getName()));
        Assert.assertTrue(app.getHelperContact().isContactAddedByPhone(contact.getPhone()));


    }

    @Test(dataProvider = "contactCSV", dataProviderClass = DataProviderContacts.class)
    public void addContactSuccessAllFieldsCSV(Contact contact) {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().getScreen("src/test/screenshots/screen-" + i + ".png");
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isContactAddedByName(contact.getName()));
        Assert.assertTrue(app.getHelperContact().isContactAddedByPhone(contact.getPhone()));


    }

    @Test(groups = {"smoke","regress","retest"})
    public void addContactSuccessReqFields() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        Contact contact = Contact.builder()
                .name("TonyReq" + i)
                .lastName("Silver")
                .phone("65656565" + i)
                .email("silver" + i + "@gmail.com")
                .address("NY")
                .build();
        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().getScreen("src/test/screenshots/screen-" + i + ".png");
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isContactAddedByName(contact.getName()));
        Assert.assertTrue(app.getHelperContact().isContactAddedByPhone(contact.getPhone()));

    }

    @Test
    public void addNewContactWrongName() {
        Contact contact = Contact.builder()
                .name("")
                .lastName("Silver")
                .phone("656565655555")
                .email("silver@gmail.com")
                .address("NY")
                .description("wrong name")
                .build();
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isAddPageStillDisplayed());
    }

    @Test
    public void addNewContactWrongLastName() {
        Contact contact = Contact.builder()
                .name("Tony")
                .lastName("")
                .phone("65656563333")
                .email("silver@gmail.com")
                .address("NY")
                .description("wrong last name")
                .build();
        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isAddPageStillDisplayed());

    }

    @Test(dataProvider = "contactWrongPhone", dataProviderClass = DataProviderContacts.class)
    public void addNewContactWrongPhone(Contact contact) {
        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isAddPageStillDisplayed());
        Assert.assertTrue(app.getHelperContact().isAlertPresent(" Phone not valid: Phone number must contain only digits! And length min 10, max 15!"));

    }

    @Test
    public void addNewContactWrongEmail() {
        Contact contact = Contact.builder()
                .name("Tony")
                .lastName("Silver")
                .phone("656577776565")
                .email("silvergmail.com")
                .address("NY")
                .description("wrong email")
                .build();
        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isAddPageStillDisplayed());
        Assert.assertTrue(app.getHelperContact().isAlertPresent("Email not valid: "));

    }

    @Test
    public void addNewContactWrongAddress() {
        Contact contact = Contact.builder()
                .name("Tony")
                .lastName("Silver")
                .phone("656562222565")
                .email("silver@gmail.com")
                .address("")
                .description("wrong address")
                .build();
        logger.info("Tests run with data: --->" + contact.toString());
        app.getHelperContact().openContactForm();
        app.getHelperContact().fillContactForm(contact);
        app.getHelperContact().saveContact();
        Assert.assertTrue(app.getHelperContact().isAddPageStillDisplayed());

    }

}
