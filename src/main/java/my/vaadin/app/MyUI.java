package my.vaadin.app;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MyUI extends UI {
    
    private CustomerService service = CustomerService.getInstance();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        List<Customer> customers = service.findAll();
        Accordion accordion = new Accordion();
        accordion.setHeight(100.0f, Unit.PERCENTAGE);
        accordion.setWidth(30.0f, Unit.PERCENTAGE);
        accordion.setTabCaptionsAsHtml(true);

        Resource res = new ThemeResource("icon32.png");
        for (int i = 0; i < customers.size(); i++) {
            VerticalLayout tab = new VerticalLayout();
            TextArea textArea = new TextArea();
            textArea.setRows(2);
            textArea.setReadOnly(true);
            textArea.setWidth(100.0f, Unit.PERCENTAGE);
            textArea.setHeight(100.0f, Unit.PERCENTAGE);
            textArea.setValue("Birthday: " + customers.get(i).getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + "\nOther info can be here.");
            tab.addComponent(textArea);
            accordion.setCaptionAsHtml(true);
            accordion.addTab(tab, "First Name: " + customers.get(i).getFirstName() +
                    ",    Last Name: " + customers.get(i).getLastName() +
                    "<br>E-mail: " + customers.get(i).getEmail(), res );
        }

        layout.addComponent(accordion);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
